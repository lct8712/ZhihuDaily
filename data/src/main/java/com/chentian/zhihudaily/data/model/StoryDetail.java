package com.chentian.zhihudaily.data.model;

import java.util.List;

import lombok.Data;

import com.google.gson.annotations.SerializedName;

/**
 * Story model displayed in detail page, such as:
 *   http://news-at.zhihu.com/api/4/news/4475182
 *
 * @author chentian
 */
@Data
@SuppressWarnings("unused")
public class StoryDetail {

  public class Recommender {

    private String avatar;
  }

  private long id;

  private String body;

  private String image;

  @SerializedName("image_source")
  private String imageSource;

  private String title;

  @SerializedName("share_url")
  private String shareUrl;

  private List<String> js;

  private List<String> css;

  private List<Recommender> recommenders;

  @SerializedName("ga_prefix")
  private String gaPrefix;

  /**
   * 0 for inside story, 1 for outside story
   */
  private int type;

  /**
   * Only for outside story
   */
  @SerializedName("theme_name")
  private String themeName;

  /**
   * Only for outside story
   */
  @SerializedName("editor_name")
  private String editorName;

  /**
   * Only for outside story
   */
  @SerializedName("theme_id")
  private int themeId;
}
