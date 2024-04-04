package org.hernan.cussi.lyrics.utils.dto;

import lombok.Data;

@Data
public class SongDto {

  protected Long id;
  protected String title;
  protected String link;
  protected String previewLink;

  protected ArtistDto artist;
  protected AlbumDto album;

}
