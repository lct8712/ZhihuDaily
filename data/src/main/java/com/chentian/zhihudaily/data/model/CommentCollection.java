package com.chentian.zhihudaily.data.model;

import java.util.List;

/**
 * Collection of story comments, such as:
 *   http://news-at.zhihu.com/api/4/story/4232852/long-comments
 *   http://news-at.zhihu.com/api/4/story/4232852/short-comments
 *
 * @author chentian
 */
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

    public String getAuthor() {
      return author;
    }

    public void setAuthor(String author) {
      this.author = author;
    }

    public String getContent() {
      return content;
    }

    public void setContent(String content) {
      this.content = content;
    }

    public String getAvatar() {
      return avatar;
    }

    public void setAvatar(String avatar) {
      this.avatar = avatar;
    }

    public int getTime() {
      return time;
    }

    public void setTime(int time) {
      this.time = time;
    }

    public long getId() {
      return id;
    }

    public void setId(long id) {
      this.id = id;
    }

    public int getLikes() {
      return likes;
    }

    public void setLikes(int likes) {
      this.likes = likes;
    }
  }

  private List<StoryComment> comments;
}
