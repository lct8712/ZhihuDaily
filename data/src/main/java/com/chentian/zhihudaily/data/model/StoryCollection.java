package com.chentian.zhihudaily.data.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Collection of stories, could be latest stories or stories before some date such as:
 *   http://news-at.zhihu.com/api/4/news/latest
 *   http://news.at.zhihu.com/api/4/news/before/20131119
 *
 * @author chentian
 */
@SuppressWarnings("unused")
public class StoryCollection {

  private String date;

  private List<StoryAbstract> stories;

  /**
   * Only for latest stories
   */
  @SerializedName("top_stories")
  private List<StoryAbstract> topStories;

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public List<StoryAbstract> getStories() {
    return stories;
  }

  public void setStories(List<StoryAbstract> stories) {
    this.stories = stories;
  }

  public List<StoryAbstract> getTopStories() {
    return topStories;
  }

  public void setTopStories(List<StoryAbstract> topStories) {
    this.topStories = topStories;
  }
}
