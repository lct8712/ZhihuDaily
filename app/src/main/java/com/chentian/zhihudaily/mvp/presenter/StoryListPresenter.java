package com.chentian.zhihudaily.mvp.presenter;

import android.view.View;

import com.chentian.zhihudaily.data.model.StoryAbstract;

/**
 * @author chentian
 */
public interface StoryListPresenter extends MVPPresenter {

  void loadTopStories();

  void loadMoreStories();

  void loadThemeStories(long themeId);

  void onRefreshMainStories();

  void onRefreshThemeStories();

  void onStoryCardItemClick(View view, StoryAbstract story);

}
