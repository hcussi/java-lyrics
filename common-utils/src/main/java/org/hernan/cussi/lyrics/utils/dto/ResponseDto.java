package org.hernan.cussi.lyrics.utils.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class ResponseDto {

  protected List<SongDto> songs;
  protected Set<ArtistDto> artists;
  protected Set<AlbumDto> albums;

  protected String prev;
  protected String next;

}
