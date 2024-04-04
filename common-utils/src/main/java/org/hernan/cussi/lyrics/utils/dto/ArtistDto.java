package org.hernan.cussi.lyrics.utils.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class ArtistDto {

  protected Long id;
  protected String name;
  protected String link;
  protected String pictureUrl;
  protected String pictureUrlSmall;
  protected String pictureUrlMedium;
  protected String tracklistUrl;

}
