package com.chentian.zhihudaily.zhihudaily.api;

import retrofit.RestAdapter;

import com.chentian.zhihudaily.zhihudaily.api.service.NewsService;

/**
 * Rest API client using Retrofit
 *
 * @author chentian
 */
public class RestClient {

  private static RestClient instance;

  private static final String API_PREFIX = "http://news.at.zhihu.com/api/4";

  private NewsService newsService;

  private RestClient() {
    RestAdapter appsRestAdapter = new RestAdapter.Builder()
            .setEndpoint(API_PREFIX)
            .build();

    newsService = appsRestAdapter.create(NewsService.class);
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
}
