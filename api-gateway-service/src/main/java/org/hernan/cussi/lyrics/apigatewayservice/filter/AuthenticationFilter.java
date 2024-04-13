package org.hernan.cussi.lyrics.apigatewayservice.filter;

import lombok.extern.slf4j.Slf4j;
import org.hernan.cussi.lyrics.apigatewayservice.config.RouterValidator;
import org.hernan.cussi.lyrics.apigatewayservice.service.OAuthService;
import org.hernan.cussi.lyrics.utils.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@Profile("!test")
public class AuthenticationFilter implements GatewayFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String CLIENT_AUTHORIZATION_HEADER = "X-Authorization";
    private final String TOKEN_PREFIX = "Bearer ";
    private final RouterValidator routerValidator;
    private final JwtUtil jwtUtil;
    private final OAuthService oAuthService;

    @Autowired
    public AuthenticationFilter(RouterValidator routerValidator, JwtUtil jwtUtil, OAuthService oAuthService) {
        this.routerValidator = routerValidator;
        this.jwtUtil = jwtUtil;
        this.oAuthService = oAuthService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (routerValidator.isSecured.test(request)) {
            if (this.isAuthMissing(request)) {
                return this.onError(exchange, HttpStatus.UNAUTHORIZED);
            }

            var token = this.getAuthHeader(request);

            try {
                var bearerToken = token;
                if (token.startsWith(TOKEN_PREFIX)) {
                    bearerToken = token.substring(TOKEN_PREFIX.length());
                }
                jwtUtil.validateToken(bearerToken);
            } catch (Exception ex) {
                return this.onError(exchange, HttpStatus.FORBIDDEN);
            }
        }
        this.updateRequest(exchange);
        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty(AUTHORIZATION_HEADER).getFirst();
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey(AUTHORIZATION_HEADER);
    }

    private void updateRequest(ServerWebExchange exchange) {
      try {
          var accessToken = oAuthService.getOAuth2AccessToken();

          exchange.getRequest().mutate()
            .header(AUTHORIZATION_HEADER, STR."\{TOKEN_PREFIX}\{accessToken}")
            .build();
      } catch (Exception e) {
        log.error("Failed to request access token", e);
      }
    }

}
