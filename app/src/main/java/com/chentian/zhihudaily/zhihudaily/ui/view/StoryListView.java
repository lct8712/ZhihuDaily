package com.chentian.zhihudaily.zhihudaily.ui.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.chentian.zhihudaily.common.util.CollectionUtils;
import com.chentian.zhihudaily.common.util.Const;
import com.chentian.zhihudaily.data.datasource.DataSource;
import com.chentian.zhihudaily.data.model.StoryCollection;
import com.chentian.zhihudaily.zhihudaily.adapter.StoryAdapter;

/**
 * List of stories, in main page
 *
 * @author chentian
 */
public class StoryListView extends RecyclerView {

  protected Context context;
  private StoryAdapter adapter;

  private boolean isLoading;
  private boolean isEnded;

  public StoryListView(Context context, AttributeSet attrs) {
    super(context, attrs);

    this.context = context;

    final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
    setLayoutManager(layoutManager);

    setOnScrollListener(new OnScrollListener() {
      private static final int THRESHOLD = 5;

      @Override
      public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        int itemCount = layoutManager.getItemCount();
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        if (newState == SCROLL_STATE_IDLE && lastVisibleItemPosition >= itemCount - THRESHOLD) {
          loadMoreStories();
        }
      }
    });
  }

  public void loadTopStories(final Runnable callback) {
    DataSource.getInstance().getLatestStoryCollection(new Callback<StoryCollection>() {
      @Override
      public void success(StoryCollection storyCollection, Response response) {
        adapter = new StoryAdapter(context, StoryAdapter.HeaderType.SlideHeader);
        adapter.setLatestDate(storyCollection.getDate());
        adapter.setStoryList(storyCollection.getStories());
        adapter.setTopStories(storyCollection.getTop_stories());
        setAdapter(adapter);

        if (callback != null) {
          callback.run();
        }

        loadMoreStories();

        Log.d(Const.LogTag.API, "Get story collection success, size: " + storyCollection.getStories().size());
      }

      @Override
      public void failure(RetrofitError error) {
        Log.d(Const.LogTag.API, "Get story collection failure: " + error + ", " + error.getUrl());
      }
    });
  }

  private void loadMoreStories() {
    if (isLoading || isEnded) {
      return;
    }

    isLoading = true;
    DataSource.getInstance().getBeforeStoryCollection(adapter.getLatestDate(), new Callback<StoryCollection>() {
      @Override
      public void success(StoryCollection storyCollection, Response response) {
        isLoading = false;
        adapter.setLatestDate(storyCollection.getDate());
        adapter.appendStoryList(storyCollection.getStories());
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
}
