package com.chentian.zhihudaily.data.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

/**
 * Collection of themes, a theme is a list of stories with same subject:
 *   http://news-at.zhihu.com/api/4/themes
 *
 * @author chentian
 */
@SuppressWarnings("unused")
public class ThemeCollection {

  public ThemeCollection() {
    subscribed = new ArrayList<>();
    others = new ArrayList<>();
  }

  @Expose
  private int limit;

  @Expose
  private List<Theme> subscribed;

  @Expose
  private List<Theme> others;

  public int getLimit() {
    return limit;
  }

  public void setLimit(int limit) {
    this.limit = limit;
  }

  public List<Theme> getSubscribed() {
    return subscribed;
  }

  public void setSubscribed(List<Theme> subscribed) {
    this.subscribed = subscribed;
  }

  public List<Theme> getOthers() {
    return others;
  }

  public void setOthers(List<Theme> others) {
    this.others = others;
  }
}
