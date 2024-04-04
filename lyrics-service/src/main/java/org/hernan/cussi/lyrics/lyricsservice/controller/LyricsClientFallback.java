package org.hernan.cussi.lyrics.lyricsservice.controller;

import org.hernan.cussi.lyrics.utils.dto.ResponseDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class LyricsClientFallback implements LyricsClient {

    @Override
    public EntityModel<ResponseDto> getSuggest(final String suggest, final String token) {
        var res = new ResponseDto();

        return EntityModel.of(
          res,
          linkTo(methodOn(LyricsControllerImpl.class).getSuggest(suggest, token)).withSelfRel()
        );
    }
}
