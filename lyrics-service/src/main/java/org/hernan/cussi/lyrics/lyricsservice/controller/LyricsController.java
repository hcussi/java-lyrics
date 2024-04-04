package org.hernan.cussi.lyrics.lyricsservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.hernan.cussi.lyrics.utils.dto.ResponseDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Lyrics", description = "Lyrics endpoints")
public interface LyricsController {

    @Operation(
      summary = "Suggest endpoint",
      description = "Returns a list of songs, albums and/or artists")
    @ApiResponses(value = {
      @ApiResponse(
        responseCode = "200",
        description = "successful operation",
        content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)) }
      ),
    })
    EntityModel<ResponseDto> getSuggest(@PathVariable("term") String suggest, @RequestParam(value = "token", required = false) String token);

}
