package org.hernan.cussi.lyrics.lyricsservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.hernan.cussi.lyrics.utils.aop.HandleRestRequest;
import org.hernan.cussi.lyrics.utils.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/lyrics")
public class LyricsControllerImpl implements LyricsController {

  private final LyricsClient lyricsClient;

  private final HttpServletRequest request;

  @Autowired
  public LyricsControllerImpl(LyricsClient lyricsClient, HttpServletRequest request) {
    this.lyricsClient = lyricsClient;
    this.request = request;
  }

  @GetMapping(
    value = "/suggest",
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  @HandleRestRequest
  public EntityModel<ResponseDto> getSuggest(
    @RequestParam("term") String suggestTerm,
    @RequestParam(value = "token", required = false) String token
  ) {
    var accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
    Map<String, Object> headers = Map.of(HttpHeaders.AUTHORIZATION, accessToken);

    return EntityModel.of(
      lyricsClient.getSuggest(headers, suggestTerm, token).getContent(),
      linkTo(methodOn(LyricsControllerImpl.class).getSuggest(suggestTerm, token)).withSelfRel()
    );
  }

}
