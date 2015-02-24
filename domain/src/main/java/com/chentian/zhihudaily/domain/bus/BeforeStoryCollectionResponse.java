package com.chentian.zhihudaily.domain.bus;

import com.chentian.zhihudaily.data.model.StoryCollection;

/**
 * Before story collection entity transfer on bus
 *
 * @author chentian
 */
public class BeforeStoryCollectionResponse extends StoryCollectionResponse {
  public BeforeStoryCollectionResponse(StoryCollection storyCollection) {
    super(storyCollection);
  }
}
