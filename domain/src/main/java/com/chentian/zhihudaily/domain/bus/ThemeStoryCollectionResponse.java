package com.chentian.zhihudaily.domain.bus;

import lombok.Data;

import com.chentian.zhihudaily.data.model.ThemeStoryCollection;

/**
 * Abstract theme collection entity transfer on bus
 *
 * @author chentian
 */
@Data
@SuppressWarnings("unused")
public abstract class ThemeStoryCollectionResponse {

  private ThemeStoryCollection themeStoryCollection;

  public ThemeStoryCollectionResponse(ThemeStoryCollection themeStoryCollection) {
    this.themeStoryCollection = themeStoryCollection;
  }
}
