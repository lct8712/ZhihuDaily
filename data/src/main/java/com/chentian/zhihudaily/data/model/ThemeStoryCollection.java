package com.chentian.zhihudaily.data.model;

import java.util.List;

import lombok.Data;

import com.google.gson.annotations.SerializedName;

/**
 * Collection of theme stories, such as:
 *   http://news-at.zhihu.com/api/4/theme/11
 *
 * @author chentian
 */
@Data
@SuppressWarnings("unused")
public class ThemeStoryCollection {

  public class Editor {

    private String url;

    private String bio;

    private int id;

    private String avatar;

    private String name;
  }

  private List<StoryAbstract> stories;

  private String description;

  private String background;

  private int color;

  private String name;

  private String image;

  @SerializedName("image_source")
  private String imageSource;

  private List<Editor> editors;
}
