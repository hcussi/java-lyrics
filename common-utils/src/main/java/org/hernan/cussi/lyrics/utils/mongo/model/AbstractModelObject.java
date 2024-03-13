package org.hernan.cussi.lyrics.utils.mongo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractModelObject {

  @Id
  private String id;

  @Version
  private Integer version;

  /**
   * The time at which this object was created, in ms since epoch.
   */
  @CreatedDate
  private OffsetDateTime creationDate;

  /**
   * The time at which this object was created, in ms since epoch.
   */
  @LastModifiedDate
  private OffsetDateTime modificationDate;
}
