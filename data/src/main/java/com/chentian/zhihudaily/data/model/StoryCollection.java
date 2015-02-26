package com.chentian.zhihudaily.data.model;

import java.util.List;

import lombok.Data;

import com.google.gson.annotations.SerializedName;

/**
 * Collection of stories, could be latest stories or stories before some date such as:
 *   http://news-at.zhihu.com/api/4/news/latest
 *   http://news.at.zhihu.com/api/4/news/before/20131119
 *
 * @author chentian
 */
@Data
@SuppressWarnings("unused")
public class StoryCollection {

  private String date;

  private List<StoryAbstract> stories;

  /**
   * Only for latest stories
   */
  @SerializedName("top_stories")
  private List<StoryAbstract> topStories;
}
