package com.chentian.zhihudaily.data.datasource.rest.service;

import retrofit.Callback;
import retrofit.http.GET;

import com.chentian.zhihudaily.data.model.ThemeCollection;

/**
 * Zhihu paper api service for themes
 * The reason to separate this from NewsService is theme use a different gson convert
 *
 * @author chentian
 */
public interface ThemeService {

  @GET("/themes")
  void getThemeCollection(Callback<ThemeCollection> callback);
}
