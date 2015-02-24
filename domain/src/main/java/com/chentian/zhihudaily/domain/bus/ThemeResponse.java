package com.chentian.zhihudaily.domain.bus;

import java.util.List;

import com.chentian.zhihudaily.data.model.Theme;

/**
 * Theme entity transfer on bus
 *
 * @author chentian
 */
public class ThemeResponse {

  private List<Theme> themeList;

  public ThemeResponse(List<Theme> themeList) {
    this.themeList = themeList;
  }

  public List<Theme> getThemeList() {
    return themeList;
  }

  public void setThemeList(List<Theme> themeList) {
    this.themeList = themeList;
  }
}
