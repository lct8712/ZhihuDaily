package com.chentian.zhihudaily.data.model;

import com.orm.SugarRecord;

/**
 * Model presents stories has bean read
 *
 * @author chentian
 */
@SuppressWarnings("unused")
public class ReadStory extends SugarRecord<ReadStory> {

  private long storyId;

  public ReadStory() { }

  public ReadStory(long storyId) {
    this.storyId = storyId;
  }

  public long getStoryId() {
    return storyId;
  }

  public void setStoryId(long storyId) {
    this.storyId = storyId;
  }
}
