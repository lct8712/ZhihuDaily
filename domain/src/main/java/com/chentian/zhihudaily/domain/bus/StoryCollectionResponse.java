package com.chentian.zhihudaily.domain.bus;

import com.chentian.zhihudaily.data.model.StoryCollection;

/**
 * Story collection entity transfer on bus
 *
 * @author chentian
 */
public class StoryCollectionResponse {

  private StoryCollection storyCollection;

  public StoryCollectionResponse(StoryCollection storyCollection) {
    this.storyCollection = storyCollection;
  }

  public StoryCollection getStoryCollection() {
    return storyCollection;
  }

  public void setStoryCollection(StoryCollection storyCollection) {
    this.storyCollection = storyCollection;
  }
}
