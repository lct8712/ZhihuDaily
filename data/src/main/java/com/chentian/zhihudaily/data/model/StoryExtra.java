package com.chentian.zhihudaily.data.model;

import lombok.Data;

import com.google.gson.annotations.SerializedName;

/**
 * Extra info of a story, such as:
 *  http://news-at.zhihu.com/api/4/story-extra/4475182
 *
 * @author chentian
 */
@Data
@SuppressWarnings("unused")
public class StoryExtra {

  private int comments;

  @SerializedName("long_comments")
  private int longComments;

  @SerializedName("short_comments")
  private int shortComments;

  private int popularity;
}
