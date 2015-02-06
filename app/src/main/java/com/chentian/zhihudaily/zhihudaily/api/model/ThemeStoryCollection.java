package com.chentian.zhihudaily.zhihudaily.api.model;

import java.util.List;

/**
 * Collection of theme stories, such as:
 *   http://news-at.zhihu.com/api/4/theme/11
 *
 * @author chentian
 */
public class ThemeStoryCollection {

  public class Editor {

    private String url;

    private String bio;

    private int id;

    private String avatar;

    private String name;

    public String getUrl() {
      return url;
    }

    public void setUrl(String url) {
      this.url = url;
    }

    public String getBio() {
      return bio;
    }

    public void setBio(String bio) {
      this.bio = bio;
    }

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public String getAvatar() {
      return avatar;
    }

    public void setAvatar(String avatar) {
      this.avatar = avatar;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }

  private List<StoryAbstract> stories;

  private String description;

  private String background;

  private int color;

  private String name;

  private String image;

  private String image_source;

  private List<Editor> editors;

  public List<StoryAbstract> getStories() {
    return stories;
  }

  public void setStories(List<StoryAbstract> stories) {
    this.stories = stories;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getBackground() {
    return background;
  }

  public void setBackground(String background) {
    this.background = background;
  }

  public int getColor() {
    return color;
  }

  public void setColor(int color) {
    this.color = color;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getImage_source() {
    return image_source;
  }

  public void setImage_source(String image_source) {
    this.image_source = image_source;
  }

  public List<Editor> getEditors() {
    return editors;
  }

  public void setEditors(List<Editor> editors) {
    this.editors = editors;
  }
}
