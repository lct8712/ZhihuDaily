package com.chentian.zhihudaily.zhihudaily.api.model;

import java.util.List;

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

  private String image_source;

  private String title;

  private String share_url;

  private List<String> js;

  private List<String> css;

  private List<Recommender> recommenders;

  private String ga_prefix;

  /**
   * 0 for inside story, 1 for outside story
   */
  private int type;

  /**
   * Only for outside story
   */
  private String theme_name;

  /**
   * Only for outside story
   */
  private String editor_name;

  /**
   * Only for outside story
   */
  private int theme_id;

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

  public String getImage_source() {
    return image_source;
  }

  public void setImage_source(String image_source) {
    this.image_source = image_source;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getShare_url() {
    return share_url;
  }

  public void setShare_url(String share_url) {
    this.share_url = share_url;
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

  public String getGa_prefix() {
    return ga_prefix;
  }

  public void setGa_prefix(String ga_prefix) {
    this.ga_prefix = ga_prefix;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String getTheme_name() {
    return theme_name;
  }

  public void setTheme_name(String theme_name) {
    this.theme_name = theme_name;
  }

  public String getEditor_name() {
    return editor_name;
  }

  public void setEditor_name(String editor_name) {
    this.editor_name = editor_name;
  }

  public int getTheme_id() {
    return theme_id;
  }

  public void setTheme_id(int theme_id) {
    this.theme_id = theme_id;
  }
}
