package com.chentian.zhihudaily.domain.bus;

import com.chentian.zhihudaily.data.model.StoryCollection;

/**
 * Latest story collection entity transfer on bus
 *
 * @author chentian
 */
public class LatestStoryCollectionResponse extends StoryCollectionResponse {
  public LatestStoryCollectionResponse(StoryCollection storyCollection) {
    super(storyCollection);
  }
}
