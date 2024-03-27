package org.hernan.cussi.lyrics.clientservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AlbumDto {

  private Long id;
  private String title;
  private String coverUrl; // cover
  private String coverUrlSmall; // cover_small
  private String coverUrlMedium; // cover_medium
  private String tracklistUrl; // tracklist

  public AlbumDto(final LinkedHashMap<String, Object> item) {
    id = Long.valueOf(String.valueOf(item.get("id")));
    title = (String) item.get("title");
    coverUrl = (String) item.get("cover");
    coverUrlSmall = (String) item.get("cover_small");
    coverUrlMedium = (String) item.get("cover_medium");
    tracklistUrl = (String) item.get("tracklist");
  }
}
