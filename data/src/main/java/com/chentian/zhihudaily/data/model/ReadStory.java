package com.chentian.zhihudaily.data.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.orm.SugarRecord;

/**
 * Model presents stories has bean read
 *
 * @author chentian
 */
@Data
@EqualsAndHashCode(callSuper = false)
@SuppressWarnings("unused")
public class ReadStory extends SugarRecord<ReadStory> {

  private long storyId;

  public ReadStory() { }

  public ReadStory(long storyId) {
    this.storyId = storyId;
  }
}
