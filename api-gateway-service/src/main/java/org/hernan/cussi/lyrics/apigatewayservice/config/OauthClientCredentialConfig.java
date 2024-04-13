package org.hernan.cussi.lyrics.apigatewayservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.endpoint.DefaultClientCredentialsTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2ClientCredentialsGrantRequest;
import org.springframework.security.oauth2.client.endpoint.OAuth2ClientCredentialsGrantRequestEntityConverter;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Configuration
@Profile("!test")
public class OauthClientCredentialConfig {

    // Create the Okta client registration
    @Bean
    ClientRegistration oktaClientRegistration(
      @Value("${spring.security.oauth2.client.provider.okta.token-uri}") String tokenUri,
      @Value("${spring.security.oauth2.client.registration.okta.client-id}") String clientId,
      @Value("${spring.security.oauth2.client.registration.okta.client-secret}") String clientSecret,
      @Value("${spring.security.oauth2.client.registration.okta.authorization-grant-type}") String authorizationGrantType
    ) {
        return ClientRegistration
          .withRegistrationId("okta")
          .tokenUri(tokenUri)
          .clientId(clientId)
          .clientSecret(clientSecret)
          .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
          .authorizationGrantType(new AuthorizationGrantType(authorizationGrantType))
          .scope("read:lyrics", "read:users", "write:users")
          .build();
    }

    // Create the client registration repository
    @Bean
    public ClientRegistrationRepository oktaClientRegistrationRepository(ClientRegistration oktaClientRegistration) {
        return new InMemoryClientRegistrationRepository(oktaClientRegistration);
    }

    // Create the authorized client service
    @Bean
    public OAuth2AuthorizedClientService oktaAuthorizedClientService(ClientRegistrationRepository oktaClientRegistrationRepository) {
        return new InMemoryOAuth2AuthorizedClientService(oktaClientRegistrationRepository);
    }

    // Create the authorized client manager and service manager using the
    // beans created and configured above
    @Bean
    public AuthorizedClientServiceOAuth2AuthorizedClientManager oktaAuthorizedClientServiceAndManager (
      ClientRegistrationRepository oktaClientRegistrationRepository,
      OAuth2AuthorizedClientService oktaAuthorizedClientService,
      OAuth2AccessTokenResponseClient<OAuth2ClientCredentialsGrantRequest> authorizationCodeAccessTokenResponseClient) {

        OAuth2AuthorizedClientProvider authorizedClientProvider = OAuth2AuthorizedClientProviderBuilder.builder()
          .clientCredentials()
          .clientCredentials(clientCredentialsGrantBuilder -> clientCredentialsGrantBuilder.accessTokenResponseClient(authorizationCodeAccessTokenResponseClient))
          .build();

        AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientManager =
          new AuthorizedClientServiceOAuth2AuthorizedClientManager(
            oktaClientRegistrationRepository, oktaAuthorizedClientService);
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

        return authorizedClientManager;
    }

    @Bean
    public OAuth2AccessTokenResponseClient<OAuth2ClientCredentialsGrantRequest> authorizationCodeAccessTokenResponseClient() {
        var requestEntityConverter = new OAuth2ClientCredentialsGrantRequestEntityConverter();
        requestEntityConverter.addParametersConverter(parametersConverter());

        var accessTokenResponseClient = new DefaultClientCredentialsTokenResponseClient();
        accessTokenResponseClient.setRequestEntityConverter(requestEntityConverter);

        return accessTokenResponseClient;
    }

    private static Converter<OAuth2ClientCredentialsGrantRequest, MultiValueMap<String, String>> parametersConverter() {
        return (_) -> {
            MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
            // OAuth0 application required audience param, this is the API application name related to the app
            parameters.set("audience", "java-lyrics-api");

            return parameters;
        };
    }
}
