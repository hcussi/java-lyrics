package org.hernan.cussi.lyrics.clientservice.dto;

import lombok.EqualsAndHashCode;
import org.hernan.cussi.lyrics.utils.dto.AlbumDto;

import java.util.LinkedHashMap;

@EqualsAndHashCode(callSuper = true)
public class ClientAlbumDto extends AlbumDto {

  public ClientAlbumDto(final LinkedHashMap<String, Object> item) {
    id = Long.valueOf(String.valueOf(item.get("id")));
    title = (String) item.get("title");
    coverUrl = (String) item.get("cover");
    coverUrlSmall = (String) item.get("cover_small");
    coverUrlMedium = (String) item.get("cover_medium");
    tracklistUrl = (String) item.get("tracklist");
  }
}
