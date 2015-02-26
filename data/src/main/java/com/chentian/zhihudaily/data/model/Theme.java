package com.chentian.zhihudaily.data.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

/**
* @author chentian
*/
@Data
@EqualsAndHashCode(callSuper = false)
@SuppressWarnings("unused")
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
}
