package com.chentian.zhihudaily.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

/**
* @author chentian
*/
public class Theme extends SugarRecord<Theme> {

  @Expose
  @SerializedName("id")
  private long themeApiId;

  @Expose
  private int color;

  @Expose
  private String name;

  @Expose
  private String thumbnail;

  @Expose
  private String description;

  private boolean isSubscribed;

  public long getThemeApiId() {
    return themeApiId;
  }

  public void setThemeApiId(long themeApiId) {
    this.themeApiId = themeApiId;
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

  public boolean isSubscribed() {
    return isSubscribed;
  }

  public void setSubscribed(boolean isSubscribed) {
    this.isSubscribed = isSubscribed;
  }
}
