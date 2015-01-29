package com.chentian.zhihudaily.zhihudaily.api.model;

/**
 * Image of start page, such as:
 *   http://news-at.zhihu.com/api/4/start-image/1080*1776
 *
 * @author chentian
 */
public class StartImage {

  private String text;

  private String img;

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getImg() {
    return img;
  }

  public void setImg(String img) {
    this.img = img;
  }
}
