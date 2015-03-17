package com.chentian.zhihudaily.domain.respository;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.util.Log;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.chentian.zhihudaily.common.util.CollectionUtils;
import com.chentian.zhihudaily.common.util.Const;
import com.chentian.zhihudaily.common.util.ThreadUtils;
import com.chentian.zhihudaily.data.datasource.file.FileDataSource;
import com.chentian.zhihudaily.data.datasource.rest.RestDataSource;
import com.chentian.zhihudaily.data.model.ReadStory;
import com.chentian.zhihudaily.data.model.StoryAbstract;
import com.chentian.zhihudaily.data.model.StoryCollection;
import com.chentian.zhihudaily.data.model.ThemeStoryCollection;
import com.chentian.zhihudaily.domain.bus.BeforeStoryCollectionResponse;
import com.chentian.zhihudaily.domain.bus.LatestStoryCollectionResponse;
import com.chentian.zhihudaily.domain.bus.ThemeBeforeStoryCollectionResponse;
import com.chentian.zhihudaily.domain.bus.ThemeLatestStoryCollectionResponse;
import com.squareup.otto.Bus;

/**
 * Sync {@link StoryCollection} related data
 *
 * @author chentian
 */
public class StorySyncUtil {

  public static void syncLatestStoryCollection(RestDataSource restDataSource, final FileDataSource fileDataSource,
                                               final Bus bus) {
    restDataSource.getLatestStoryCollection(new Callback<StoryCollection>() {
      @Override
      public void success(final StoryCollection storyCollection, Response response) {
        onGetLatestStoryCollectionSuccess(storyCollection, bus);
        fileDataSource.saveLatestStoryCollection(storyCollection);

        String logInfo = String.format("Sync latest story collection success, size: %d, date: %s",
                CollectionUtils.notNull(storyCollection.getStories()).size(), storyCollection.getDate());
        Log.d(Const.LogTag.API, logInfo);
      }

      @Override
      public void failure(RetrofitError error) {
        getLatestStoryCollectionFromFile(fileDataSource, bus);
        Log.d(Const.LogTag.API, "Sync latest story collection failure: " + error + ", " + error.getUrl());
      }
    });
  }

  public static void syncBeforeStoryCollection(final String date, RestDataSource restDataSource,
                                               final FileDataSource fileDataSource, final Bus bus) {
    Log.d(Const.LogTag.API, "Sync before story collection starts: " + date);

    restDataSource.getBeforeStoryCollection(date, new Callback<StoryCollection>() {
      @Override
      public void success(final StoryCollection storyCollection, Response response) {
        onGetBeforeStoryCollectionSuccess(storyCollection, bus);
        fileDataSource.saveLatestStoryCollection(date, storyCollection);

        String logInfo = String.format("Sync before story collection success, size: %d, date: %s",
                CollectionUtils.notNull(storyCollection.getStories()).size(), storyCollection.getDate());
        Log.d(Const.LogTag.API, logInfo);
      }

      @Override
      public void failure(RetrofitError error) {
        getBeforeStoryCollectionFromFile(date, fileDataSource, bus);

        Log.d(Const.LogTag.API, "Sync before story collection failure: " + error + ", " + error.getUrl());
      }
    });
  }

  public static void syncThemeLatestStoryCollection(final long themeId, RestDataSource restDataSource,
                                                    final FileDataSource fileDataSource, final Bus bus) {
    Log.d(Const.LogTag.API, "Sync theme latest story collection starts, id: " + themeId);

    restDataSource.getThemeLatestStoryCollection(themeId, new Callback<ThemeStoryCollection>() {
      @Override
      public void success(final ThemeStoryCollection themeStoryCollection, Response response) {
        onGetThemeLatestStoryCollectionSuccess(themeStoryCollection, bus);
        fileDataSource.saveThemeLatestStoryCollection(themeId, themeStoryCollection);

        String logInfo = String.format("Sync theme latest story collection success, id: %d, size: %d, latest id: %d",
                themeId, CollectionUtils.notNull(themeStoryCollection.getStories()).size(),
                themeStoryCollection.getLatestThemeStoryId());
        Log.d(Const.LogTag.API, logInfo);
      }

      @Override
      public void failure(RetrofitError error) {
        getThemeLatestStoryCollectionFromFile(themeId, fileDataSource, bus);

        Log.d(Const.LogTag.API, "Sync theme latest story collection failure: " + error + ", " + error.getUrl());
      }
    });
  }

  public static void syncThemeBeforeStoryCollection(final long themeId, final long storyId,
                                                    RestDataSource restDataSource,
                                                    final FileDataSource fileDataSource, final Bus bus) {
    Log.d(Const.LogTag.API, "Sync theme before story collection starts, id: " + themeId);

    restDataSource.getThemeBeforeStoryCollection(themeId, storyId, new Callback<ThemeStoryCollection>() {
      @Override
      public void success(final ThemeStoryCollection themeStoryCollection, Response response) {
        onGetThemeBeforeStoryCollectionSuccess(themeStoryCollection, bus);
        fileDataSource.saveThemeBeforeStoryCollection(themeId, storyId, themeStoryCollection);

        String logInfo = String.format("Sync theme before story collection success, id: %d, size: %d, latest id: %d",
                themeId, CollectionUtils.notNull(themeStoryCollection.getStories()).size(),
                themeStoryCollection.getLatestThemeStoryId());
        Log.d(Const.LogTag.API, logInfo);
      }

      @Override
      public void failure(RetrofitError error) {
        getThemeBeforeStoryCollectionFromFile(themeId, storyId, fileDataSource, bus);

        Log.d(Const.LogTag.API, "Sync theme before story collection failure: " + error + ", " + error.getUrl());
      }
    });
  }

  private static void onGetLatestStoryCollectionSuccess(final StoryCollection storyCollection, final Bus bus) {
    new AsyncTask<Void, Void, Void>() {
      @Override
      protected Void doInBackground(Void... params) {
        Set<Long> readStoryIds = getReadStoryIds();
        fillStoryAbstractListWithReadInfo(readStoryIds, storyCollection.getStories());
        fillStoryAbstractListWithReadInfo(readStoryIds, storyCollection.getTopStories());

        bus.post(new LatestStoryCollectionResponse(storyCollection));
        return null;
      }
    }.execute();
  }

  private static void getLatestStoryCollectionFromFile(FileDataSource fileDataSource, final Bus bus) {
    Log.d(Const.LogTag.API, "Get latest story collection from file starts.");

    fileDataSource.getLatestStoryCollection(new Callback<StoryCollection>() {
      @Override
      public void success(StoryCollection storyCollection, Response response) {
        onGetLatestStoryCollectionSuccess(storyCollection, bus);

        String logInfo = String.format("Ge latest story collection from file success, size: %d, date: %s",
                CollectionUtils.notNull(storyCollection.getStories()).size(), storyCollection.getDate());
        Log.d(Const.LogTag.API, logInfo);
      }

      @Override
      public void failure(RetrofitError error) {
        Log.d(Const.LogTag.API, "Get latest story collection from file failure.");
      }
    });
  }

  private static void onGetBeforeStoryCollectionSuccess(final StoryCollection storyCollection, final Bus bus) {
    new AsyncTask<Void, Void, Void>() {
      @Override
      protected Void doInBackground(Void... params) {
        Set<Long> readStoryIds = getReadStoryIds();
        fillStoryAbstractListWithReadInfo(readStoryIds, storyCollection.getStories());

        bus.post(new BeforeStoryCollectionResponse(storyCollection));
        return null;
      }
    }.execute();
  }

  private static void getBeforeStoryCollectionFromFile(String date, FileDataSource fileDataSource, final Bus bus) {
    Log.d(Const.LogTag.API, "Get before story collection from file starts: " + date);

    fileDataSource.getBeforeStoryCollection(date, new Callback<StoryCollection>() {
      @Override
      public void success(StoryCollection storyCollection, Response response) {
        onGetBeforeStoryCollectionSuccess(storyCollection, bus);

        String logInfo = String.format("Get before story collection from file success, size: %d, date: %s",
                CollectionUtils.notNull(storyCollection.getStories()).size(), storyCollection.getDate());
        Log.d(Const.LogTag.API, logInfo);
      }

      @Override
      public void failure(RetrofitError error) {
        Log.d(Const.LogTag.API, "Get before story collection from file failure.");
      }
    });
  }

  private static void onGetThemeLatestStoryCollectionSuccess(final ThemeStoryCollection themeStoryCollection, Bus bus) {
    new AsyncTask<Void, Void, Void>() {
      @Override
      protected Void doInBackground(Void... params) {
        fillStoryAbstractListWithReadInfo(getReadStoryIds(), themeStoryCollection.getStories());

        return null;
      }
    }.execute();

    bus.post(new ThemeLatestStoryCollectionResponse(themeStoryCollection));
  }

  private static void getThemeLatestStoryCollectionFromFile(final long themeId, FileDataSource fileDataSource,
                                                            final Bus bus) {
    Log.d(Const.LogTag.API, "Get theme latest story collection from file starts, id: " + themeId);

    fileDataSource.getThemeLatestStoryCollection(themeId, new Callback<ThemeStoryCollection>() {
      @Override
      public void success(ThemeStoryCollection themeStoryCollection, Response response) {
        onGetThemeLatestStoryCollectionSuccess(themeStoryCollection, bus);

        String logInfo = String.format("Get theme latest story collection from file success," +
                        "id: %d, size: %d, latest id: %d",
                themeId, CollectionUtils.notNull(themeStoryCollection.getStories()).size(),
                themeStoryCollection.getLatestThemeStoryId());
        Log.d(Const.LogTag.API, logInfo);
      }

      @Override
      public void failure(RetrofitError error) {
        Log.d(Const.LogTag.API, "Get theme latest story collection from file failure.");
      }
    });
  }

  private static void onGetThemeBeforeStoryCollectionSuccess(final ThemeStoryCollection themeStoryCollection, Bus bus) {
    new AsyncTask<Void, Void, Void>() {
      @Override
      protected Void doInBackground(Void... params) {
        fillStoryAbstractListWithReadInfo(getReadStoryIds(), themeStoryCollection.getStories());

        return null;
      }
    }.execute();

    bus.post(new ThemeBeforeStoryCollectionResponse(themeStoryCollection));
  }

  private static void getThemeBeforeStoryCollectionFromFile(final long themeId, long storyId,
                                                            FileDataSource fileDataSource, final Bus bus) {
    Log.d(Const.LogTag.API, "Sync theme before story collection from file starts, id: " + themeId);

    fileDataSource.getThemeBeforeStoryCollection(themeId, storyId, new Callback<ThemeStoryCollection>() {
      @Override
      public void success(ThemeStoryCollection themeStoryCollection, Response response) {
        onGetThemeBeforeStoryCollectionSuccess(themeStoryCollection, bus);

        String logInfo = String.format("Sync theme before story collection from file success," +
                        "id: %d, size: %d, latest id: %d",
                themeId, CollectionUtils.notNull(themeStoryCollection.getStories()).size(),
                themeStoryCollection.getLatestThemeStoryId());
        Log.d(Const.LogTag.API, logInfo);
      }

      @Override
      public void failure(RetrofitError error) {
        Log.d(Const.LogTag.API, "Sync theme before story collection from file failure.");
      }
    });
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

  private static void fillStoryAbstractListWithReadInfo(Set<Long> readStoryIds, List<StoryAbstract> topStories) {
    for (StoryAbstract storyAbstract : topStories) {
      if (readStoryIds.contains(storyAbstract.getId())) {
        storyAbstract.setRead(true);
      }
    }
  }
}
