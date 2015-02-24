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
import com.chentian.zhihudaily.domain.StoryRepository;
import com.chentian.zhihudaily.domain.bus.BeforeStoryCollectionResponse;
import com.chentian.zhihudaily.domain.bus.LatestStoryCollectionResponse;
import com.chentian.zhihudaily.domain.bus.ThemeStoryCollectionResponse;
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
  private boolean isLoading;
  private boolean isEnded;
  private long currentThemeId;

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
  public void loadTopStories() {
    StoryRepository.syncLatestStoryCollection(storyListView.getContext());
  }

  @Override
  public void loadMoreStories() {
    if (isLoading || isEnded) {
      return;
    }

    isLoading = true;
    StoryRepository.syncBeforeStoryCollection(storyListView.getContext(), latestDate);
  }

  @Override
  public void loadThemeStories(final long themeId) {
    currentThemeId = themeId;
    StoryRepository.syncThemeStoryCollection(storyListView.getContext(), themeId);
  }

  @Override
  public void onRefreshMainStories() {
    loadTopStories();
  }

  @Override
  public void onRefreshThemeStories() {
    loadThemeStories(currentThemeId);
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
    storyListView.showMainStory(storyCollection);
    latestDate = storyCollection.getDate();

    loadMoreStories();
  }

  @Subscribe
  public void onBeforeStoryCollectionUpdate(BeforeStoryCollectionResponse storyCollectionResponse) {
    StoryCollection storyCollection = storyCollectionResponse.getStoryCollection();
    storyListView.showMoreStory(storyCollection);

    isLoading = false;
    latestDate = storyCollection.getDate();
    isEnded = CollectionUtils.isEmpty(storyCollection.getStories());
  }

  @Subscribe
  public void onThemeStoryCollectionUpdate(ThemeStoryCollectionResponse themeStoryCollectionResponse) {
    storyListView.showThemeStory(themeStoryCollectionResponse.getThemeStoryCollection());
  }
}
