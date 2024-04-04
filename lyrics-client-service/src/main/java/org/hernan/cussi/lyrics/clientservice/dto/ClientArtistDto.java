package org.hernan.cussi.lyrics.clientservice.dto;

import lombok.EqualsAndHashCode;
import org.hernan.cussi.lyrics.utils.dto.ArtistDto;

import java.util.LinkedHashMap;

@EqualsAndHashCode(callSuper = true)
public class ClientArtistDto extends ArtistDto {

  public ClientArtistDto(final LinkedHashMap<String, Object> item) {
    id = Long.valueOf(String.valueOf(item.get("id")));
    name = (String) item.get("name");
    link = (String) item.get("link");
    pictureUrl = (String) item.get("picture");
    pictureUrlSmall = (String) item.get("picture_small");
    pictureUrlMedium = (String) item.get("picture_medium");
    tracklistUrl = (String) item.get("tracklist");
  }
}
