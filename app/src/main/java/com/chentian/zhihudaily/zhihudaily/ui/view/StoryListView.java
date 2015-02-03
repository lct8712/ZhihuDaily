package com.chentian.zhihudaily.zhihudaily.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ListView;
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
 * @author chentian
 */
public class StoryListView extends ListView {

  private Context context;
  private SlideTopStory slideTopStory;
  private StoryAdapter adapter;

  private boolean isLoading;
  private boolean isEnded;

  public StoryListView(Context context, AttributeSet attrs) {
    super(context, attrs);

    this.context = context;

    setOnScrollListener(new OnScrollListener() {
      private static final int THRESHOLD = 5;

      @Override
      public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE &&
                view.getLastVisiblePosition() >= view.getCount() - THRESHOLD) {
          loadMoreStories();
        }
      }

      @Override
      public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) { }
    });
  }

  public void setSlideTopStoryView(SlideTopStory slideTopStory) {
    this.slideTopStory = slideTopStory;
    addHeaderView(slideTopStory);
  }

  public void loadTopStories() {
    NewsService newsService = RestClient.getInstance().getNewsService();
    newsService.getLatestStoryCollection(new Callback<StoryCollection>() {
      @Override
      public void success(StoryCollection storyCollection, Response response) {
        adapter = new StoryAdapter(context);
        adapter.setLatestDate(storyCollection.getDate());
        adapter.setStoryList(storyCollection.getStories());
        setAdapter(adapter);

        slideTopStory.setTopStories(storyCollection.getTop_stories());

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
