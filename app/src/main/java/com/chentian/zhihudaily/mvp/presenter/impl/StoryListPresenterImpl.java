package com.chentian.zhihudaily.mvp.presenter.impl;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.chentian.zhihudaily.common.util.CollectionUtils;
import com.chentian.zhihudaily.common.util.Const;
import com.chentian.zhihudaily.data.datasource.DataSource;
import com.chentian.zhihudaily.data.model.StoryAbstract;
import com.chentian.zhihudaily.data.model.StoryCollection;
import com.chentian.zhihudaily.data.model.ThemeStoryCollection;
import com.chentian.zhihudaily.mvp.presenter.StoryListPresenter;
import com.chentian.zhihudaily.mvp.view.MVPStoryListView;
import com.chentian.zhihudaily.util.ViewUtils;

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
  public void onResume() { }

  @Override
  public void onPause() { }

  @Override
  public void loadTopStories() {
    DataSource.getInstance(storyListView.getContext()).
            getLatestStoryCollection(new Callback<StoryCollection>() {
      @Override
      public void success(StoryCollection storyCollection, Response response) {
        storyListView.showMainStory(storyCollection);
        latestDate = storyCollection.getDate();

        loadMoreStories();

        Log.d(Const.LogTag.API, "Get story collection success, size: " + storyCollection.getStories().size());
      }

      @Override
      public void failure(RetrofitError error) {
        Log.d(Const.LogTag.API, "Get story collection failure: " + error + ", " + error.getUrl());
      }
    });
  }

  @Override
  public void loadMoreStories() {
    if (isLoading || isEnded) {
      return;
    }

    isLoading = true;
    DataSource.getInstance(storyListView.getContext()).
            getBeforeStoryCollection(latestDate, new Callback<StoryCollection>() {
      @Override
      public void success(StoryCollection storyCollection, Response response) {
        storyListView.showMoreStory(storyCollection);

        isLoading = false;
        latestDate = storyCollection.getDate();
        isEnded = CollectionUtils.isEmpty(storyCollection.getStories());

        Log.d(Const.LogTag.API, "Get more story collection success, size: " + storyCollection.getStories().size());
      }

      @Override
      public void failure(RetrofitError error) {
        isLoading = false;
        Log.d(Const.LogTag.API, "Get more story collection failure: " + error + ", " + error.getUrl());
      }
    });
  }

  @Override
  public void loadThemeStories(final long themeId) {
    currentThemeId = themeId;

    DataSource.getInstance(storyListView.getContext()).
            getThemeStoryCollection(themeId, new Callback<ThemeStoryCollection>() {
      @Override
      public void success(ThemeStoryCollection themeStoryCollection, Response response) {
        storyListView.showThemeStory(themeStoryCollection);

        String logInfo = String.format("Get theme story collection success, id: %d, size: %d",
                themeId, themeStoryCollection.getStories().size());
        Log.d(Const.LogTag.API, logInfo);
      }

      @Override
      public void failure(RetrofitError error) {
        Log.d(Const.LogTag.API, "Get theme story collection failure: " + error + ", " + error.getUrl());
      }
    });
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
}
