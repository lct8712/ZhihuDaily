package com.chentian.zhihudaily.mvp.presenter.impl;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;

import com.chentian.zhihudaily.common.provider.BusProvider;
import com.chentian.zhihudaily.common.util.CollectionUtils;
import com.chentian.zhihudaily.data.model.StoryAbstract;
import com.chentian.zhihudaily.data.model.StoryCollection;
import com.chentian.zhihudaily.data.model.ThemeStoryCollection;
import com.chentian.zhihudaily.domain.StoryRepository;
import com.chentian.zhihudaily.domain.bus.*;
import com.chentian.zhihudaily.mvp.presenter.StoryListPresenter;
import com.chentian.zhihudaily.mvp.view.MVPStoryListView;
import com.chentian.zhihudaily.util.ViewUtils;
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
    BusProvider.getUiBus().register(this);
  }

  @Override
  public void onPause() {
    BusProvider.getUiBus().unregister(this);
  }

  @Override
  public void loadLatestStories() {
    isMainStoryEnded = false;
    StoryRepository.syncLatestStoryCollection(storyListView.getContext());
  }

  @Override
  public void loadBeforeStories() {
    if (isMainStoryLoading || isMainStoryEnded) {
      return;
    }

    isMainStoryLoading = true;
    StoryRepository.syncBeforeStoryCollection(storyListView.getContext(), latestDate);
  }

  @Override
  public void loadThemeLatestStories(final long themeId) {
    currentThemeId = themeId;
    isThemeStoryEnded = false;
    StoryRepository.syncThemeLatestStoryCollection(storyListView.getContext(), themeId);
  }

  @Override
  public void loadThemeBeforeStories() {
    if (isThemeStoryLoading || isThemeStoryEnded || latestThemeStoryId == 0) {
      return;
    }

    isThemeStoryLoading = true;
    StoryRepository.syncThemeBeforeStoryCollection(storyListView.getContext(), currentThemeId, latestThemeStoryId);
  }

  @Override
  public void onRefreshMainStories() {
    loadLatestStories();
  }

  @Override
  public void onRefreshThemeStories() {
    loadThemeLatestStories(currentThemeId);
  }

  @Override
  public void onStoryCardItemClick(final View view, final StoryAbstract story) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      int cx = (view.getLeft() + view.getRight()) / 2;
      int cy = (view.getTop() + view.getBottom()) / 2;

      int finalRadius = view.getWidth();

      Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
      anim.start();
      anim.addListener(new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
          super.onAnimationEnd(animation);
          ViewUtils.openDetailActivity(story.getId(), view.getContext());
        }
      });
    } else {
      ViewUtils.openDetailActivity(story.getId(), view.getContext());
    }
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
