package com.chentian.zhihudaily.domain.bus;

import java.util.List;

import lombok.Data;

import com.chentian.zhihudaily.data.model.Theme;

/**
 * Theme entity transfer on bus
 *
 * @author chentian
 */
@Data
@SuppressWarnings("unused")
public class ThemeResponse {

  private List<Theme> themeList;

  public ThemeResponse(List<Theme> themeList) {
    this.themeList = themeList;
  }
}
