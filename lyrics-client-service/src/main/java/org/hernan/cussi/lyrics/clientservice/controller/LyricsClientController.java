package org.hernan.cussi.lyrics.clientservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.hernan.cussi.lyrics.clientservice.dto.ClientResponseDto;
import org.springframework.hateoas.EntityModel;

@Tag(name = "Lyrics Client", description = "Expose Lyrics endpoints")
public interface LyricsClientController {

  @Operation(
    summary = "Suggest endpoint",
    description = "Returns a list of songs, albums and/or artists")
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "successful operation",
      content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ClientResponseDto.class)) }
    ),
  })
  EntityModel<ClientResponseDto> getSuggest(String suggest, String token);

}
