package com.chentian.zhihudaily.data.model;

import java.util.List;

import lombok.Data;

import com.google.gson.annotations.Expose;

/**
 * Collection of themes, a theme is a list of stories with same subject:
 *   http://news-at.zhihu.com/api/4/themes
 *
 * @author chentian
 */
@Data
@SuppressWarnings("unused")
public class ThemeCollection {

  @Expose
  private int limit;

  @Expose
  private List<Theme> subscribed;

  @Expose
  private List<Theme> others;
}
