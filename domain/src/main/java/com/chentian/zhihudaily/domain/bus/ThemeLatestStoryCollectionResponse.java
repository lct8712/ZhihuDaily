package com.chentian.zhihudaily.domain.bus;

import com.chentian.zhihudaily.data.model.ThemeStoryCollection;

/**
 * Latest theme collection entity transfer on bus
 *
 * @author chentian
 */
@SuppressWarnings("unused")
public class ThemeLatestStoryCollectionResponse extends ThemeStoryCollectionResponse {
  public ThemeLatestStoryCollectionResponse(ThemeStoryCollection themeStoryCollection) {
    super(themeStoryCollection);
  }
}
