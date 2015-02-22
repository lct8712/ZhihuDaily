package com.chentian.zhihudaily.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Extra info of a story, such as:
 *  http://news-at.zhihu.com/api/4/story-extra/4475182
 *
 * @author chentian
 */
@SuppressWarnings("unused")
public class StoryExtra {

  private int comments;

  @SerializedName("long_comments")
  private int longComments;

  @SerializedName("short_comments")
  private int shortComments;

  private int popularity;

  public int getComments() {
    return comments;
  }

  public void setComments(int comments) {
    this.comments = comments;
  }

  public int getLongComments() {
    return longComments;
  }

  public void setLongComments(int longComments) {
    this.longComments = longComments;
  }

  public int getShortComments() {
    return shortComments;
  }

  public void setShortComments(int shortComments) {
    this.shortComments = shortComments;
  }

  public int getPopularity() {
    return popularity;
  }

  public void setPopularity(int popularity) {
    this.popularity = popularity;
  }
}
