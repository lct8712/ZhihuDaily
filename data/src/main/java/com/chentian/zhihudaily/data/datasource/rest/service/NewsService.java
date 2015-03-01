package com.chentian.zhihudaily.data.datasource.rest.service;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

import com.chentian.zhihudaily.data.model.*;

/**
 * Zhihu paper api service
 * API doc:
 *   https://github.com/izzyleung/ZhihuDailyPurify/wiki/%E7%9F%A5%E4%B9%8E%E6%97%A5%E6%8A%A5-API-%E5%88%86%E6%9E%90
 *
 * @author chentian
 */
public interface NewsService {

  public static final String API_PREFIX = "http://news-at.zhihu.com/api/4";

  @GET("/start-image/1080*1776")
  void getStartImage(Callback<StartImage> callback);

  @GET("/news/latest")
  void getLatestStoryCollection(Callback<StoryCollection> callback);

  @GET("/news/before/{date}")
  void getBeforeStoryCollection(@Path("date") String date, Callback<StoryCollection> callback);

  @GET("/news/{id}")
  void getStoryDetail(@Path("id") long id, Callback<StoryDetail> callback);

  @GET("/theme/{id}")
  void getThemeLatestStoryCollection(@Path("id") long id, Callback<ThemeStoryCollection> callback);

  @GET("/theme/{themeId}/before/{storyId}")
  void getThemeBeforeStoryCollection(@Path("themeId") long themeId, @Path("storyId") long storyId,
                                     Callback<ThemeStoryCollection> callback);

  @GET("/story-extra/{id}")
  void getStoryExtra(@Path("id") long id, Callback<StoryDetail> callback);

  @GET("/story/{id}/short-comments")
  void getShortComments(@Path("id") long id, Callback<CommentCollection> callback);

  @GET("/story/{id}/long-comments")
  void getLongComments(@Path("id") long id, Callback<CommentCollection> callback);
}
