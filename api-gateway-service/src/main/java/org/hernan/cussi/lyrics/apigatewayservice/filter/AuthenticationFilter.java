package org.hernan.cussi.lyrics.apigatewayservice.filter;

import org.hernan.cussi.lyrics.apigatewayservice.config.RouterValidator;
import org.hernan.cussi.lyrics.utils.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RefreshScope
@Component
@Profile("!test")
public class AuthenticationFilter implements GatewayFilter {

    private final String TOKEN_HEADER = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";


    private final RouterValidator routerValidator;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthenticationFilter(RouterValidator routerValidator, JwtUtil jwtUtil) {
        this.routerValidator = routerValidator;
        this.jwtUtil = jwtUtil;
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
                this.updateRequest(exchange, bearerToken);
            } catch (Exception ex) {
                return this.onError(exchange, HttpStatus.FORBIDDEN);
            }
        }
        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty(TOKEN_HEADER).getFirst();
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey(TOKEN_HEADER);
    }

    private void updateRequest(ServerWebExchange exchange, String token) {
        var email = jwtUtil.extractSubject(token);
        exchange.getRequest().mutate().header("email", email).build();
    }
}
