package com.chentian.zhihudaily.zhihudaily.api;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

import com.chentian.zhihudaily.zhihudaily.api.service.NewsService;
import com.chentian.zhihudaily.zhihudaily.api.service.ThemeService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Rest API client using Retrofit
 *
 * @author chentian
 */
public class RestClient {

  private static RestClient instance;

  private NewsService newsService;

  private ThemeService themeService;

  private RestClient() {
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

  public static RestClient getInstance() {
    if (instance == null) {
      instance = new RestClient();
    }
    return instance;
  }

  public NewsService getNewsService() {
    return newsService;
  }

  public ThemeService getThemeService() {
    return themeService;
  }
}
