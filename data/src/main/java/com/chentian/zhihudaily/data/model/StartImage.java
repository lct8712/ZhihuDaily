package com.chentian.zhihudaily.data.model;

import lombok.Data;

/**
 * Image of start page, such as:
 *   http://news-at.zhihu.com/api/4/start-image/1080*1776
 *
 * @author chentian
 */
@Data
@SuppressWarnings("unused")
public class StartImage {

  private String text;

  private String img;
}
