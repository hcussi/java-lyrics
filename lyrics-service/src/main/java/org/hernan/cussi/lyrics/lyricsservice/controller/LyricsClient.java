package org.hernan.cussi.lyrics.lyricsservice.controller;

import org.hernan.cussi.lyrics.utils.dto.ResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "lyrics-client-service", fallback = LyricsClientFallback.class)
public interface LyricsClient {

    @GetMapping(
      value = "/api/v1/lyrics/suggest",
      produces = MediaType.APPLICATION_JSON_VALUE
    )
    EntityModel<ResponseDto> getSuggest(@RequestHeader Map<String, Object> headers, @RequestParam("term") String suggest, @RequestParam(value = "token", required = false) String token);

}
