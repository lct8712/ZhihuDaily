package com.chentian.zhihudaily.zhihudaily.api.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Story model displayed in detail page, such as:
 *   http://news-at.zhihu.com/api/4/news/4475182
 *
 * @author chentian
 */
public class StoryDetail {

  public class Recommender {

    private String avatar;

    public String getAvatar() {
      return avatar;
    }

    public void setAvatar(String avatar) {
      this.avatar = avatar;
    }
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

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
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

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getShareUrl() {
    return shareUrl;
  }

  public void setShareUrl(String shareUrl) {
    this.shareUrl = shareUrl;
  }

  public List<String> getJs() {
    return js;
  }

  public void setJs(List<String> js) {
    this.js = js;
  }

  public List<String> getCss() {
    return css;
  }

  public void setCss(List<String> css) {
    this.css = css;
  }

  public List<Recommender> getRecommenders() {
    return recommenders;
  }

  public void setRecommenders(List<Recommender> recommenders) {
    this.recommenders = recommenders;
  }

  public String getGaPrefix() {
    return gaPrefix;
  }

  public void setGaPrefix(String gaPrefix) {
    this.gaPrefix = gaPrefix;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String getThemeName() {
    return themeName;
  }

  public void setThemeName(String themeName) {
    this.themeName = themeName;
  }

  public String getEditorName() {
    return editorName;
  }

  public void setEditorName(String editorName) {
    this.editorName = editorName;
  }

  public int getThemeId() {
    return themeId;
  }

  public void setThemeId(int themeId) {
    this.themeId = themeId;
  }
}
