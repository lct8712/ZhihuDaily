package com.chentian.zhihudaily.zhihudaily.api.model;

import java.util.List;

/**
 * Collection of themes, a theme is a list of stories with same subject, such as:
 *   http://news-at.zhihu.com/api/4/themes
 *
 * @author chentian
 */
public class ThemeCollection {

  public class Theme {

    private long id;

    private int color;

    private String name;

    private String thumbnail;

    private String description;

    public long getId() {
      return id;
    }

    public void setId(long id) {
      this.id = id;
    }

    public int getColor() {
      return color;
    }

    public void setColor(int color) {
      this.color = color;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getThumbnail() {
      return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
      this.thumbnail = thumbnail;
    }

    public String getDescription() {
      return description;
    }

    public void setDescription(String description) {
      this.description = description;
    }
  }

  private int limit;

  private List<Theme> subscribed;

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
