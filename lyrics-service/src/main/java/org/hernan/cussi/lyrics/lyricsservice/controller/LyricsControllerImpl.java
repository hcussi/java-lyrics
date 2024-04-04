package org.hernan.cussi.lyrics.lyricsservice.controller;

import org.hernan.cussi.lyrics.utils.aop.HandleRestRequest;
import org.hernan.cussi.lyrics.utils.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/lyrics")
public class LyricsControllerImpl implements LyricsController {

  private final LyricsClient lyricsClient;

  @Autowired
  public LyricsControllerImpl(LyricsClient lyricsClient) {
    this.lyricsClient = lyricsClient;
  }

  @GetMapping(
    value = "/suggest/{term}",
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  @HandleRestRequest
  public EntityModel<ResponseDto> getSuggest(@PathVariable("term") String suggestTerm, @RequestParam(value = "token", required = false) String token) {
    return EntityModel.of(
      lyricsClient.getSuggest(suggestTerm, token).getContent(),
      linkTo(methodOn(LyricsControllerImpl.class).getSuggest(suggestTerm, token)).withSelfRel()
    );
  }

}
