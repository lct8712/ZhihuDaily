package com.chentian.zhihudaily.data.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Collection of theme stories, such as:
 *   http://news-at.zhihu.com/api/4/theme/11
 *
 * @author chentian
 */
@SuppressWarnings("unused")
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

  @SerializedName("image_source")
  private String imageSource;

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

  public String getImageSource() {
    return imageSource;
  }

  public void setImageSource(String imageSource) {
    this.imageSource = imageSource;
  }

  public List<Editor> getEditors() {
    return editors;
  }

  public void setEditors(List<Editor> editors) {
    this.editors = editors;
  }
}
