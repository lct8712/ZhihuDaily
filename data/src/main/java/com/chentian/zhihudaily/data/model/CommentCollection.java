package com.chentian.zhihudaily.data.model;

import java.util.List;

import lombok.Data;

/**
 * Collection of story comments, such as:
 *   http://news-at.zhihu.com/api/4/story/4232852/long-comments
 *   http://news-at.zhihu.com/api/4/story/4232852/short-comments
 *
 * @author chentian
 */
@Data
@SuppressWarnings("unused")
public class CommentCollection {

  /**
   * Comment of a story, could be a long-comment of a short-comment
   */
  public class StoryComment {

    private String author;

    private String content;

    private String avatar;

    private int time;

    private long id;

    private int likes;
  }

  private List<StoryComment> comments;
}
