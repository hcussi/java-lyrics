package org.hernan.cussi.lyrics.clientservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {

  private List<SongDto> songs;
  private Set<ArtistDto> artists;
  private Set<AlbumDto> albums;

  private String prev;
  private String next; // http://api.deezer.com/search?limit=15&q=back%20black&index=15

  public void init(final LinkedHashMap data) {

    next = initToken((String) data.getOrDefault("next", null));
    prev = initToken((String) data.getOrDefault("prev", null));
    initSongs((ArrayList<LinkedHashMap>) data.getOrDefault("data", List.of()));
  }

  private void initSongs(final ArrayList<LinkedHashMap> value) {
    songs = value.stream()
      .filter(item -> item.getOrDefault("type", "").equals("track"))
      .map(SongDto::new).toList();
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
