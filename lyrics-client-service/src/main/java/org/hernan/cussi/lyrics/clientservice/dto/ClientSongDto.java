package org.hernan.cussi.lyrics.clientservice.dto;

import lombok.EqualsAndHashCode;
import org.hernan.cussi.lyrics.utils.dto.SongDto;

import java.util.LinkedHashMap;

@EqualsAndHashCode(callSuper = true)
public class ClientSongDto extends SongDto {

  public ClientSongDto(final LinkedHashMap<String, Object> item) {
    id = Long.valueOf(String.valueOf(item.get("id")));
    title = (String) item.get("title");
    link = (String) item.get("link");
    previewLink = (String) item.get("preview");
    artist = new ClientArtistDto((LinkedHashMap<String, Object>) item.get("artist"));
    album = new ClientAlbumDto((LinkedHashMap<String, Object>) item.get("album"));
  }
}
