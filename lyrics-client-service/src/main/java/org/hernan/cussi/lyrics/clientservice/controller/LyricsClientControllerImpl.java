package org.hernan.cussi.lyrics.clientservice.controller;

import org.hernan.cussi.lyrics.clientservice.config.AppConfig;
import org.hernan.cussi.lyrics.clientservice.dto.ClientResponseDto;
import org.hernan.cussi.lyrics.utils.aop.HandleRestRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.LinkedHashMap;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/lyrics")
public class LyricsClientControllerImpl implements LyricsClientController {

  private final RestTemplate restTemplate;

  @Autowired
  public LyricsClientControllerImpl(AppConfig config, RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder.rootUri(config.getBaseUrl()).build();
  }

  @GetMapping(
    value = "/suggest",
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  @HandleRestRequest
  public EntityModel<ClientResponseDto> getSuggest(@RequestParam("term") String suggestTerm, @RequestParam(value = "token", required = false) String token) {
    var res = new ClientResponseDto();
    var url = new StringBuilder("/search?q={term}");

    if (token != null) {
      var decodedToken = new String(Base64.getDecoder().decode(token));
      url.append("&").append(decodedToken.substring(decodedToken.indexOf("index")));
    }
    var data = restTemplate.getForObject(url.toString(), LinkedHashMap.class, suggestTerm);

    res.init(data);

    return EntityModel.of(
      res,
      linkTo(methodOn(LyricsClientControllerImpl.class).getSuggest(suggestTerm, token)).withSelfRel()
    );
  }

}
