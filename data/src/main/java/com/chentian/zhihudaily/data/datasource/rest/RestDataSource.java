package com.chentian.zhihudaily.data.datasource.rest;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

import com.chentian.zhihudaily.data.datasource.CacheableDataSource;
import com.chentian.zhihudaily.data.datasource.rest.service.NewsService;
import com.chentian.zhihudaily.data.datasource.rest.service.ThemeService;
import com.chentian.zhihudaily.data.model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Rest API data source
 * Get data from web api with Retrofit http library
 *
 * @author chentian
 */
public class RestDataSource extends CacheableDataSource {

  private NewsService newsService;
  private ThemeService themeService;

  public RestDataSource() {
    this(null);
  }

  public RestDataSource(CacheableDataSource next) {
    super(next);

    newsService = new RestAdapter.Builder()
            .setEndpoint(NewsService.API_PREFIX)
            .build()
            .create(NewsService.class);

    Gson gsonExpose = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    themeService = new RestAdapter.Builder()
            .setEndpoint(NewsService.API_PREFIX)
            .setConverter(new GsonConverter(gsonExpose))
            .build()
            .create(ThemeService.class);
  }

  @Override
  public void getStoryDetail(long id, Callback<StoryDetail> callback) {
    newsService.getStoryDetail(id, callback);
  }

  public void getStartImage(Callback<StartImage> callback) {
    newsService.getStartImage(callback);
  }

  public void getLatestStoryCollection(Callback<StoryCollection> callback) {
    newsService.getLatestStoryCollection(callback);
  }

  public void getBeforeStoryCollection(String date, Callback<StoryCollection> callback) {
    newsService.getBeforeStoryCollection(date, callback);
  }

  public void getThemeLatestStoryCollection(long id, Callback<ThemeStoryCollection> callback) {
    newsService.getThemeLatestStoryCollection(id, callback);
  }

  public void getThemeBeforeStoryCollection(long themeId, long storyId, Callback<ThemeStoryCollection> callback) {
    newsService.getThemeBeforeStoryCollection(themeId, storyId, callback);
  }

  public void getStoryExtra(long id, Callback<StoryDetail> callback) {
    newsService.getStoryExtra(id, callback);
  }

  public void getShortComments(long id, Callback<CommentCollection> callback) {
    newsService.getShortComments(id, callback);
  }

  public void getLongComments(long id, Callback<CommentCollection> callback) {
    newsService.getLongComments(id, callback);
  }

  public void getThemeCollection(Callback<ThemeCollection> callback) {
    themeService.getThemeCollection(callback);
  }
}
