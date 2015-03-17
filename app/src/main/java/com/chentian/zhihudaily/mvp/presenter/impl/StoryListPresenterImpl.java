package com.chentian.zhihudaily.mvp.presenter.impl;

import com.chentian.zhihudaily.DailyApplication;
import com.chentian.zhihudaily.common.util.CollectionUtils;
import com.chentian.zhihudaily.data.model.StoryCollection;
import com.chentian.zhihudaily.data.model.ThemeStoryCollection;
import com.chentian.zhihudaily.domain.bus.*;
import com.chentian.zhihudaily.mvp.presenter.StoryListPresenter;
import com.chentian.zhihudaily.mvp.view.MVPStoryListView;
import com.squareup.otto.Subscribe;

/**
 * @author chentian
 */
public class StoryListPresenterImpl implements StoryListPresenter {

  private MVPStoryListView storyListView;

  private String latestDate;
  private boolean isMainStoryLoading;
  private boolean isMainStoryEnded;

  private long currentThemeId;
  private long latestThemeStoryId;
  private boolean isThemeStoryLoading;
  private boolean isThemeStoryEnded;

  public StoryListPresenterImpl(MVPStoryListView storyListView) {
    this.storyListView = storyListView;
  }

  @Override
  public void onResume() {
    DailyApplication.getInstance().getUiBus().register(this);
  }

  @Override
  public void onPause() {
    DailyApplication.getInstance().getUiBus().unregister(this);
  }

  @Override
  public void loadLatestStories() {
    storyListView.showLatestStory(new StoryCollection());

    isMainStoryEnded = false;
    DailyApplication.getInstance().getDataRepository().syncLatestStoryCollection();
  }

  @Override
  public void loadBeforeStories() {
    if (isMainStoryLoading || isMainStoryEnded) {
      return;
    }

    isMainStoryLoading = true;
    DailyApplication.getInstance().getDataRepository().syncBeforeStoryCollection(latestDate);
  }

  @Override
  public void loadThemeLatestStories(final long themeId) {
    storyListView.showThemeLatestStory(new ThemeStoryCollection());

    currentThemeId = themeId;
    isThemeStoryEnded = false;
    DailyApplication.getInstance().getDataRepository().syncThemeLatestStoryCollection(themeId);
  }

  @Override
  public void loadThemeBeforeStories() {
    if (isThemeStoryLoading || isThemeStoryEnded || latestThemeStoryId == 0) {
      return;
    }

    isThemeStoryLoading = true;
    DailyApplication.getInstance().getDataRepository().syncThemeBeforeStoryCollection(
            currentThemeId, latestThemeStoryId);
  }

  @Override
  public void onRefreshMainStories() {
    loadLatestStories();
  }

  @Override
  public void onRefreshThemeStories() {
    loadThemeLatestStories(currentThemeId);
  }

  @Subscribe
  public void onLatestStoryCollectionUpdate(LatestStoryCollectionResponse storyCollectionResponse) {
    StoryCollection storyCollection = storyCollectionResponse.getStoryCollection();
    storyListView.showLatestStory(storyCollection);
    latestDate = storyCollection.getDate();

    loadBeforeStories();
  }

  @Subscribe
  public void onBeforeStoryCollectionUpdate(BeforeStoryCollectionResponse storyCollectionResponse) {
    StoryCollection storyCollection = storyCollectionResponse.getStoryCollection();
    storyListView.showBeforeStory(storyCollection);

    isMainStoryLoading = false;
    latestDate = storyCollection.getDate();
    isMainStoryEnded = CollectionUtils.isEmpty(storyCollection.getStories());
  }

  @Subscribe
  public void onThemeLatestStoryCollectionUpdate(ThemeLatestStoryCollectionResponse themeStoryCollectionResponse) {
    ThemeStoryCollection themeStoryCollection = themeStoryCollectionResponse.getThemeStoryCollection();
    storyListView.showThemeLatestStory(themeStoryCollection);
    latestThemeStoryId = themeStoryCollection.getLatestThemeStoryId();

    loadThemeBeforeStories();
  }

  @Subscribe
  public void onThemeBeforeStoryCollectionUpdate(ThemeBeforeStoryCollectionResponse themeStoryCollectionResponse) {
    ThemeStoryCollection themeStoryCollection = themeStoryCollectionResponse.getThemeStoryCollection();
    storyListView.showThemeBeforeStory(themeStoryCollection);

    isThemeStoryLoading = false;
    latestThemeStoryId = themeStoryCollection.getLatestThemeStoryId();
    isThemeStoryEnded = CollectionUtils.isEmpty(themeStoryCollection.getStories()) || (latestThemeStoryId == 0);
  }

  @Subscribe
  public void onDrawerItemSelected(DrawerItemSelectedEvent drawerItemSelectedEvent) {
    switch (drawerItemSelectedEvent.getItemType()) {
      case MainPage:
        storyListView.loadMainPage();
        break;
      case Theme:
        storyListView.loadTheme(drawerItemSelectedEvent.getThemeId());
        break;
      default:
        throw new IllegalArgumentException("drawerItemSelectedEvent item type error.");
    }
  }
}
