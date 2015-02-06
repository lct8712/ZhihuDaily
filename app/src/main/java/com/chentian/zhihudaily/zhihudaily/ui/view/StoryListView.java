package com.chentian.zhihudaily.zhihudaily.ui.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.chentian.zhihudaily.zhihudaily.adapter.StoryAdapter;
import com.chentian.zhihudaily.zhihudaily.api.RestClient;
import com.chentian.zhihudaily.zhihudaily.api.model.StoryCollection;
import com.chentian.zhihudaily.zhihudaily.api.service.NewsService;
import com.chentian.zhihudaily.zhihudaily.util.CollectionUtils;
import com.chentian.zhihudaily.zhihudaily.util.Const;

/**
 * List of stories, in main page
 *
 * @author chentian
 */
public class StoryListView extends RecycleViewWithDivider {

  private StoryAdapter adapter;

  private boolean isLoading;
  private boolean isEnded;

  public StoryListView(Context context, AttributeSet attrs) {
    super(context, attrs);

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

  public void loadTopStories() {
    NewsService newsService = RestClient.getInstance().getNewsService();
    newsService.getLatestStoryCollection(new Callback<StoryCollection>() {
      @Override
      public void success(StoryCollection storyCollection, Response response) {
        adapter = new StoryAdapter(context, StoryAdapter.HeaderType.SlideHeader);
        adapter.setLatestDate(storyCollection.getDate());
        adapter.setStoryList(storyCollection.getStories());
        adapter.setTopStories(storyCollection.getTop_stories());
        setAdapter(adapter);

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
    NewsService newsService = RestClient.getInstance().getNewsService();
    newsService.getBeforeStoryCollection(adapter.getLatestDate(), new Callback<StoryCollection>() {
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
