package org.hernan.cussi.lyrics.apigatewayservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
	properties = {
		"userServiceEndpoint=http://localhost:${wiremock.server.port}",
		"lyricsServiceEndpoint=http://localhost:${wiremock.server.port}"
	}
)
@AutoConfigureWireMock(port = 0)
class LyricsApiGatewayServiceApplicationTests {

	@Autowired
	private WebTestClient webClient;

	@Test
	public void contextLoads() throws Exception {

	}

	@Test
	public void apiUsers() throws Exception {
		var usersJson = "{\"content\":[{\"id\":\"534rdc6l5i\",\"name\":\"test1\",\"email\":\"test1@gmail.com\"},{\"id\":\"534rdc6l5j\",\"name\":\"test2\",\"email\":\"test2@gmail.com\"}],\"pageable\":{\"pageNumber\":0,\"pageSize\":5,\"sort\":{\"empty\":false,\"unsorted\":false,\"sorted\":true},\"offset\":0,\"paged\":true,\"unpaged\":false},\"totalElements\":2,\"totalPages\":1,\"last\":true,\"size\":5,\"number\":0,\"sort\":{\"empty\":false,\"unsorted\":false,\"sorted\":true},\"numberOfElements\":2,\"first\":true,\"empty\":false}";
		//Stubs
		stubFor(
			get(urlEqualTo("/api/v1/users"))
				.willReturn(
					aResponse()
						.withHeader("Content-Type", "application/json")
						.withBody(usersJson)
				)
		);

		webClient
			.get()
			.uri("/api/users")
			.exchange()
			.expectStatus().isOk()
		  .expectBody().json(usersJson);
	}

	@Test
	public void apiUser() throws Exception {
		var userJson = "{\"id\":\"534rdc6l5i\",\"name\":\"test\",\"email\":\"test@gmail.com\"}";
		//Stubs
		stubFor(
			get(urlEqualTo("/api/v1/users/65f0b078ab5d7e2f44eda463"))
				.willReturn(
					aResponse()
						.withHeader("Content-Type", "application/json")
						.withBody(userJson)
				)
		);

		webClient
			.get()
			.uri("/api/users/65f0b078ab5d7e2f44eda463")
			.exchange()
			.expectStatus().isOk()
			.expectBody().json(userJson);
	}

	@Test
	public void apiLyrics() throws Exception {
		stubFor(
			get(urlEqualTo("/api/v1/lyrics"))
				.willReturn(
					aResponse()
						.withStatus(404)
				)
		);

		webClient
			.get()
			.uri("/api/v1/lyrics")
			.exchange()
			.expectStatus().is4xxClientError();
	}

}
