package org.hernan.cussi.lyrics.clientservice.dto;

import lombok.EqualsAndHashCode;
import org.hernan.cussi.lyrics.utils.dto.ResponseDto;
import org.hernan.cussi.lyrics.utils.dto.SongDto;

import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
public class ClientResponseDto extends ResponseDto {

  public void init(final LinkedHashMap data) {

    next = initToken((String) data.getOrDefault("next", null));
    prev = initToken((String) data.getOrDefault("prev", null));
    initSongs((ArrayList<LinkedHashMap>) data.getOrDefault("data", List.of()));
  }

  private void initSongs(final ArrayList<LinkedHashMap> value) {
    songs = value.stream()
      .filter(item -> item.getOrDefault("type", "").equals("track"))
      .map(ClientSongDto::new).map(clientSongDto -> (SongDto) clientSongDto).toList();
    artists = songs.stream().map(SongDto::getArtist).collect(Collectors.toSet());
    albums = songs.stream().map(SongDto::getAlbum).collect(Collectors.toSet());
  }

  private String initToken(String value) {
    if (value == null) {
      return null;
    }

    return Base64.getEncoder().encodeToString(value.getBytes());
  }
}
