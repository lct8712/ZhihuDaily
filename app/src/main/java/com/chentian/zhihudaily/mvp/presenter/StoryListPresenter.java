package com.chentian.zhihudaily.mvp.presenter;

/**
 * @author chentian
 */
public interface StoryListPresenter extends MVPPresenter {

  void loadLatestStories();

  void loadBeforeStories();

  void loadThemeLatestStories(long themeId);

  void loadThemeBeforeStories();

  void onRefreshMainStories();

  void onRefreshThemeStories();

}
