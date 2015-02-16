package com.chentian.zhihudaily.zhihudaily.ui.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.chentian.zhihudaily.zhihudaily.adapter.StoryAdapter;
import com.chentian.zhihudaily.zhihudaily.api.RestClient;
import com.chentian.zhihudaily.zhihudaily.api.model.ThemeStoryCollection;
import com.chentian.zhihudaily.zhihudaily.api.service.NewsService;
import com.chentian.zhihudaily.zhihudaily.util.Const;

/**
 * List of theme stories
 *
 * @author chentian
 */
public class ThemeStoryListView extends RecyclerView {

  protected Context context;
  private StoryAdapter adapter;

  public ThemeStoryListView(Context context, AttributeSet attrs) {
    super(context, attrs);

    this.context = context;

    setLayoutManager(new LinearLayoutManager(context));
  }

  public void loadThemeStories(final long themeId, final Runnable callback) {
    NewsService newsService = RestClient.getInstance().getNewsService();
    newsService.getThemeStoryCollection(themeId, new Callback<ThemeStoryCollection>() {
      @Override
      public void success(ThemeStoryCollection themeStoryCollection, Response response) {
        adapter = new StoryAdapter(context, StoryAdapter.HeaderType.NormalHeader);
        adapter.setStoryList(themeStoryCollection.getStories());
        adapter.setNormalHeaderData(themeStoryCollection.getDescription(), themeStoryCollection.getImage());
        setAdapter(adapter);

        if (callback != null) {
          callback.run();
        }

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
}
