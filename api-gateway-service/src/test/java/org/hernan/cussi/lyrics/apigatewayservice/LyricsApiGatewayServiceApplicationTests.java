package org.hernan.cussi.lyrics.apigatewayservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
	properties = {
		"userServiceEndpoint=http://localhost:${wiremock.server.port}",
		"lyricsServiceEndpoint=http://localhost:${wiremock.server.port}",
		"authenticationServiceEndpoint=http://localhost:${wiremock.server.port}"
	}
)
@AutoConfigureWireMock(port = 0)
@ActiveProfiles("test")
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
		var lyricsJson = "{\"songs\":[{\"id\":92720046,\"title\":\"Back In Black\",\"link\":\"https://www.deezer.com/track/92720046\",\"previewLink\":\"https://cdns-preview-c.dzcdn.net/stream/c-cfc0e5baab3f7ce7758e259482bd8681-5.mp3\",\"artist\":{\"id\":115,\"name\":\"AC/DC\",\"link\":\"https://www.deezer.com/artist/115\",\"pictureUrl\":\"https://api.deezer.com/artist/115/image\",\"pictureUrlSmall\":\"https://e-cdns-images.dzcdn.net/images/artist/ad61d6e0fa724d880db979c9ac8cc5e3/56x56-000000-80-0-0.jpg\",\"pictureUrlMedium\":\"https://e-cdns-images.dzcdn.net/images/artist/ad61d6e0fa724d880db979c9ac8cc5e3/250x250-000000-80-0-0.jpg\",\"tracklistUrl\":\"https://api.deezer.com/artist/115/top?limit=50\"},\"album\":{\"id\":9410100,\"title\":\"Back In Black\",\"coverUrl\":\"https://api.deezer.com/album/9410100/image\",\"coverUrlSmall\":\"https://e-cdns-images.dzcdn.net/images/cover/41041b14873956eff0459c8ea2c296a8/56x56-000000-80-0-0.jpg\",\"coverUrlMedium\":\"https://e-cdns-images.dzcdn.net/images/cover/41041b14873956eff0459c8ea2c296a8/250x250-000000-80-0-0.jpg\",\"tracklistUrl\":\"https://api.deezer.com/album/9410100/tracks\"}}],\"artists\":[{\"id\":115,\"name\":\"AC/DC\",\"link\":\"https://www.deezer.com/artist/115\",\"pictureUrl\":\"https://api.deezer.com/artist/115/image\",\"pictureUrlSmall\":\"https://e-cdns-images.dzcdn.net/images/artist/ad61d6e0fa724d880db979c9ac8cc5e3/56x56-000000-80-0-0.jpg\",\"pictureUrlMedium\":\"https://e-cdns-images.dzcdn.net/images/artist/ad61d6e0fa724d880db979c9ac8cc5e3/250x250-000000-80-0-0.jpg\",\"tracklistUrl\":\"https://api.deezer.com/artist/115/top?limit=50\"}],\"albums\":[{\"id\":9410156,\"title\":\"Iron Man 2\",\"coverUrl\":\"https://api.deezer.com/album/9410156/image\",\"coverUrlSmall\":\"https://e-cdns-images.dzcdn.net/images/cover/f5cb12b6f789aeec02b245100c8f1bcc/56x56-000000-80-0-0.jpg\",\"coverUrlMedium\":\"https://e-cdns-images.dzcdn.net/images/cover/f5cb12b6f789aeec02b245100c8f1bcc/250x250-000000-80-0-0.jpg\",\"tracklistUrl\":\"https://api.deezer.com/album/9410156/tracks\"}],\"prev\":null,\"next\":\"aHR0cHM6Ly9hcGkuZGVlemVyLmNvbS9zZWFyY2g/cT1iYWNrJTIwaW4lMjBibGFjayZpbmRleD0yNQ==\",\"_links\":{\"self\":{\"href\":\"http://192.168.68.101:8083/api/v1/lyrics/suggest/back%20in%20black{?token}\",\"templated\":true}}}";

		stubFor(
			get(urlEqualTo("/api/v1/lyrics/suggest?term=back%20in%20black"))
				.willReturn(
					aResponse()
						.withHeader("Content-Type", "application/json")
						.withBody(lyricsJson)
				)
		);

		webClient
			.get()
			.uri("/api/lyrics/suggest?term=back in black")
			.exchange()
			.expectStatus().isOk()
			.expectBody().json(lyricsJson);
	}

}
