package org.hernan.cussi.lyrics.utils.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class AlbumDto {

  protected Long id;
  protected String title;
  protected String coverUrl;
  protected String coverUrlSmall;
  protected String coverUrlMedium;
  protected String tracklistUrl;

}
