package org.hernan.cussi.lyrics.clientservice.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class ArtistDto {

  private Long id;
  private String name;
  private String link;
  private String pictureUrl; // picture
  private String pictureUrlSmall; // picture_small
  private String pictureUrlMedium; // picture_medium
  private String tracklistUrl; // tracklist

  public ArtistDto(final LinkedHashMap<String, Object> item) {
    id = Long.valueOf(String.valueOf(item.get("id")));
    name = (String) item.get("name");
    link = (String) item.get("link");
    pictureUrl = (String) item.get("picture");
    pictureUrlSmall = (String) item.get("picture_small");
    pictureUrlMedium = (String) item.get("picture_medium");
    tracklistUrl = (String) item.get("tracklist");
  }
}
