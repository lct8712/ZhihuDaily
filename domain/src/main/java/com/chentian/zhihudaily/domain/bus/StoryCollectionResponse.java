package com.chentian.zhihudaily.domain.bus;

import lombok.Data;

import com.chentian.zhihudaily.data.model.StoryCollection;

/**
 * Abstract story collection entity transfer on bus
 *
 * @author chentian
 */
@Data
@SuppressWarnings("unused")
public abstract class StoryCollectionResponse {

  private StoryCollection storyCollection;

  public StoryCollectionResponse(StoryCollection storyCollection) {
    this.storyCollection = storyCollection;
  }
}
