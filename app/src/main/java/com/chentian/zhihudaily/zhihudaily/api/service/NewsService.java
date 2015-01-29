package com.chentian.zhihudaily.zhihudaily.api.service;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

import com.chentian.zhihudaily.zhihudaily.api.model.*;

/**
 * Zhihu paper api service
 * API doc:
 *   https://github.com/izzyleung/ZhihuDailyPurify/wiki/%E7%9F%A5%E4%B9%8E%E6%97%A5%E6%8A%A5-API-%E5%88%86%E6%9E%90
 *
 * @author chentian
 */
public interface NewsService {

  @GET("/start-image/1080*1776")
  void getStartImage(Callback<StartImage> callback);

  @GET("/news/latest")
  void getLatestStoryCollection(Callback<StoryCollection> callback);

  @GET("/news/before/{date}")
  void getBeforeStoryCollection(@Path("date") String date, Callback<StoryCollection> callback);

  @GET("/news/{id}")
  void getStoryDetail(@Path("id") int id, Callback<StoryDetail> callback);

  @GET("/story-extra/{id}")
  void getStoryExtra(@Path("id") int id, Callback<StoryDetail> callback);

  @GET("/story/{id}/short-comments")
  void getShortComments(@Path("id") int id, Callback<CommentCollection> callback);

  @GET("/story/{id}/long-comments")
  void getLongComments(@Path("id") int id, Callback<CommentCollection> callback);

  @GET("/themes")
  void getLatestThemeCollection(Callback<ThemeCollection> callback);
}