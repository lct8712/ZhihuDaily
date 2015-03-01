package com.chentian.zhihudaily.mvp.presenter;

import android.view.View;

import com.chentian.zhihudaily.data.model.StoryAbstract;

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

  void onStoryCardItemClick(View view, StoryAbstract story);

}
