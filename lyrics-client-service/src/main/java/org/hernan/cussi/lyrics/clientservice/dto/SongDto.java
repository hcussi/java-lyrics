package org.hernan.cussi.lyrics.clientservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;

@Data
@NoArgsConstructor
public class SongDto {

  private Long id;
  private String title;
  private String link;
  private String previewLink; // preview

  private ArtistDto artist;
  private AlbumDto album;

  public SongDto(final LinkedHashMap<String, Object> item) {
    id = Long.valueOf(String.valueOf(item.get("id")));
    title = (String) item.get("title");
    link = (String) item.get("link");
    previewLink = (String) item.get("preview");
    artist = new ArtistDto((LinkedHashMap<String, Object>) item.get("artist"));
    album = new AlbumDto((LinkedHashMap<String, Object>) item.get("album"));
  }
}
