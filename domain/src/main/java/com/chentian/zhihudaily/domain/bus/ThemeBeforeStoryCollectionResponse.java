package com.chentian.zhihudaily.domain.bus;

import com.chentian.zhihudaily.data.model.ThemeStoryCollection;

/**
 * Before theme collection entity transfer on bus
 *
 * @author chentian
 */
@SuppressWarnings("unused")
public class ThemeBeforeStoryCollectionResponse extends ThemeStoryCollectionResponse {
  public ThemeBeforeStoryCollectionResponse(ThemeStoryCollection themeStoryCollection) {
    super(themeStoryCollection);
  }
}
