package org.hernan.cussi.lyrics.apigatewayservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
@Profile("!test")
public class OAuthService {
    private static final String ACCESS_TOKEN = "api-gateway:access_token";
    private static final Long FIVE_MINUTES = (long) (5 * 60);

    // Inject the OAuth authorized client service and authorized client manager
    // from the OAuthClientConfiguration class
    private final AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientManager;

    private final ReactiveRedisTemplate<String, String> redisTemplate;

    // Build an OAuth2 request for the Okta provider
    private final OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest.withClientRegistrationId("okta")
      .principal("API Gateway")
      .build();

    @Autowired
    public OAuthService(
      AuthorizedClientServiceOAuth2AuthorizedClientManager oktaAuthorizedClientServiceAndManager,
      ReactiveRedisTemplate<String, String> reactiveStringRedisTemplate
    ) {
        this.authorizedClientManager = oktaAuthorizedClientServiceAndManager;
        this.redisTemplate = reactiveStringRedisTemplate;
    }

    private OAuth2AccessToken getNewOAuth2AccessToken() {
        // Perform the actual authorization request using the authorized client service and authorized client
        // manager. This is where the JWT is retrieved from the Okta servers.
        OAuth2AuthorizedClient authorizedClient = this.authorizedClientManager.authorize(authorizeRequest);

        // Get the token from the authorized client object
        return Objects.requireNonNull(authorizedClient).getAccessToken();
    }

    public String getOAuth2AccessToken() throws ExecutionException, InterruptedException {
        if (redisTemplate.hasKey(ACCESS_TOKEN).toFuture().get()) {
            return redisTemplate.opsForValue().get(ACCESS_TOKEN).toFuture().get();
        }
        var accessToken = getNewOAuth2AccessToken();
        Duration duration = Duration.between(Instant.now(), accessToken.getExpiresAt()).minusMinutes(FIVE_MINUTES);
        redisTemplate.opsForValue().set(ACCESS_TOKEN, accessToken.getTokenValue(), duration).toFuture().get();
        return accessToken.getTokenValue();
    }
}
