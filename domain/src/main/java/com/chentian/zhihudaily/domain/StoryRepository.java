package com.chentian.zhihudaily.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.util.Log;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.chentian.zhihudaily.common.provider.BusProvider;
import com.chentian.zhihudaily.common.util.CollectionUtils;
import com.chentian.zhihudaily.common.util.Const;
import com.chentian.zhihudaily.common.util.ThreadUtils;
import com.chentian.zhihudaily.data.datasource.DataSource;
import com.chentian.zhihudaily.data.model.ReadStory;
import com.chentian.zhihudaily.data.model.StoryAbstract;
import com.chentian.zhihudaily.data.model.StoryCollection;
import com.chentian.zhihudaily.data.model.ThemeStoryCollection;
import com.chentian.zhihudaily.domain.bus.BeforeStoryCollectionResponse;
import com.chentian.zhihudaily.domain.bus.LatestStoryCollectionResponse;
import com.chentian.zhihudaily.domain.bus.ThemeStoryCollectionResponse;

/**
 * Represents a repository for reading and writing {@link StoryAbstract} related data.
 *
 * @author chentian
 */
public class StoryRepository {

  /**
   * Get latest StoryCollection from data source, then fill the read info from database
   */
  public static void syncLatestStoryCollection(Context context) {
    Log.d(Const.LogTag.API, "Get latest story collection starts.");

    DataSource.getInstance(context).getLatestStoryCollection(new Callback<StoryCollection>() {
      @Override
      public void success(final StoryCollection storyCollection, Response response) {
        new AsyncTask<Void, Void, Void>() {
          @Override
          protected Void doInBackground(Void... params) {
            Set<Long> readStoryIds = getReadStoryIds();
            fillStoryAbstractListWithReadInfo(readStoryIds, storyCollection.getStories());
            fillStoryAbstractListWithReadInfo(readStoryIds, storyCollection.getTopStories());

            BusProvider.getUiBus().post(new LatestStoryCollectionResponse(storyCollection));
            return null;
          }
        }.execute();

        Log.d(Const.LogTag.API, "Get latest story collection success, size: " + storyCollection.getStories().size());
      }

      @Override
      public void failure(RetrofitError error) {
        Log.d(Const.LogTag.API, "Get latest story collection failure: " + error + ", " + error.getUrl());
      }
    });
  }

  /**
   * Get before StoryCollection from data source, then fill the read info from database
   */
  public static void syncBeforeStoryCollection(Context context, String date) {
    Log.d(Const.LogTag.API, "Get more story collection starts.");

    DataSource.getInstance(context).getBeforeStoryCollection(date, new Callback<StoryCollection>() {
      @Override
      public void success(final StoryCollection storyCollection, Response response) {
        new AsyncTask<Void, Void, Void>() {
          @Override
          protected Void doInBackground(Void... params) {
            Set<Long> readStoryIds = getReadStoryIds();
            fillStoryAbstractListWithReadInfo(readStoryIds, storyCollection.getStories());

            BusProvider.getUiBus().post(new BeforeStoryCollectionResponse(storyCollection));
            return null;
          }
        }.execute();

        Log.d(Const.LogTag.API, "Get more story collection success, size: " + storyCollection.getStories().size());
      }

      @Override
      public void failure(RetrofitError error) {
        Log.d(Const.LogTag.API, "Get more story collection failure: " + error + ", " + error.getUrl());
      }
    });
  }

  public static void syncThemeStoryCollection(Context context, final long themeId) {
    Log.d(Const.LogTag.API, "Get theme story collection starts, id: " + themeId);

    DataSource.getInstance(context).getThemeStoryCollection(themeId, new Callback<ThemeStoryCollection>() {
      @Override
      public void success(final ThemeStoryCollection themeStoryCollection, Response response) {
        new AsyncTask<Void, Void, Void>() {
          @Override
          protected Void doInBackground(Void... params) {
            fillStoryAbstractListWithReadInfo(getReadStoryIds(), themeStoryCollection.getStories());

            return null;
          }
        }.execute();

        BusProvider.getUiBus().post(new ThemeStoryCollectionResponse(themeStoryCollection));
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

  public static void markAsRead(StoryAbstract storyAbstract) {
    storyAbstract.setRead(true);
    final ReadStory readStory = new ReadStory(storyAbstract.getId());
    new AsyncTask<Void, Void, Void>() {
      @Override
      protected Void doInBackground(Void... params) {
        readStory.save();
        return null;
      }
    }.execute();
  }

  private static void fillStoryAbstractListWithReadInfo(Set<Long> readStoryIds, List<StoryAbstract> topStories) {
    for (StoryAbstract storyAbstract : topStories) {
      if (readStoryIds.contains(storyAbstract.getId())) {
        storyAbstract.setRead(true);
      }
    }
  }

  private static Set<Long> getReadStoryIds() {
    ThreadUtils.checkRunningOnNonUiThread();

    try {
      Set<Long> result = new HashSet<>();
      for (ReadStory readStory : CollectionUtils.notNull(ReadStory.listAll(ReadStory.class))) {
        result.add(readStory.getStoryId());
      }
      return result;
    } catch (SQLiteException e) {
      return Collections.emptySet();
    }
  }
}
