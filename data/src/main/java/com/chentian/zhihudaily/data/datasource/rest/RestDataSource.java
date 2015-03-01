package com.chentian.zhihudaily.data.datasource.rest;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

import com.chentian.zhihudaily.data.datasource.DataSource;
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
public class RestDataSource extends DataSource {

  private NewsService newsService;

  private ThemeService themeService;

  public RestDataSource(DataSource next) {
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
  public void getStartImage(Callback<StartImage> callback) {
    newsService.getStartImage(callback);
  }

  @Override
  public void getLatestStoryCollection(Callback<StoryCollection> callback) {
    newsService.getLatestStoryCollection(callback);
  }

  @Override
  public void getBeforeStoryCollection(String date, Callback<StoryCollection> callback) {
    newsService.getBeforeStoryCollection(date, callback);
  }

  @Override
  public void getStoryDetail(long id, Callback<StoryDetail> callback) {
    newsService.getStoryDetail(id, callback);
  }

  @Override
  public void getThemeLatestStoryCollection(long id, Callback<ThemeStoryCollection> callback) {
    newsService.getThemeLatestStoryCollection(id, callback);
  }

  @Override
  public void getThemeBeforeStoryCollection(long themeId, long storyId, Callback<ThemeStoryCollection> callback) {
    newsService.getThemeBeforeStoryCollection(themeId, storyId, callback);
  }

  @Override
  public void getStoryExtra(long id, Callback<StoryDetail> callback) {
    newsService.getStoryExtra(id, callback);
  }

  @Override
  public void getShortComments(long id, Callback<CommentCollection> callback) {
    newsService.getShortComments(id, callback);
  }

  @Override
  public void getLongComments(long id, Callback<CommentCollection> callback) {
    newsService.getLongComments(id, callback);
  }

  @Override
  public void getThemeCollection(Callback<ThemeCollection> callback) {
    themeService.getThemeCollection(callback);
  }
}
