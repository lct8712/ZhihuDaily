package com.chentian.zhihudaily.domain.bus;

import com.chentian.zhihudaily.data.model.ThemeStoryCollection;

/**
 * Theme collection entity transfer on bus
 *
 * @author chentian
 */
public class ThemeStoryCollectionResponse {

  private ThemeStoryCollection themeStoryCollection;

  public ThemeStoryCollectionResponse(ThemeStoryCollection themeStoryCollection) {
    this.themeStoryCollection = themeStoryCollection;
  }

  public ThemeStoryCollection getThemeStoryCollection() {
    return themeStoryCollection;
  }

  public void setThemeStoryCollection(ThemeStoryCollection themeStoryCollection) {
    this.themeStoryCollection = themeStoryCollection;
  }
}
