package com.chentian.zhihudaily.zhihudaily.api.model;

/**
 * Extra info of a story, such as:
 *  http://news-at.zhihu.com/api/4/story-extra/4475182
 *
 * @author chentian
 */
public class StoryExtra {

  private int comments;

  private int long_comments;

  private int short_comments;

  private int popularity;

  public int getComments() {
    return comments;
  }

  public void setComments(int comments) {
    this.comments = comments;
  }

  public int getLong_comments() {
    return long_comments;
  }

  public void setLong_comments(int long_comments) {
    this.long_comments = long_comments;
  }

  public int getShort_comments() {
    return short_comments;
  }

  public void setShort_comments(int short_comments) {
    this.short_comments = short_comments;
  }

  public int getPopularity() {
    return popularity;
  }

  public void setPopularity(int popularity) {
    this.popularity = popularity;
  }
}
