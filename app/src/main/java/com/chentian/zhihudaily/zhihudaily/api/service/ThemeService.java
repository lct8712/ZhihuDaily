package com.chentian.zhihudaily.zhihudaily.api.service;

import retrofit.Callback;
import retrofit.http.GET;

import com.chentian.zhihudaily.zhihudaily.api.model.ThemeCollection;

/**
 *
 * Zhihu paper api service for themes
 * The reason to separate this from NewsService is theme use a different gson convert
 *
 * @author chentian
 */
public interface ThemeService {

  @GET("/themes")
  void getLatestThemeCollection(Callback<ThemeCollection> callback);
}
