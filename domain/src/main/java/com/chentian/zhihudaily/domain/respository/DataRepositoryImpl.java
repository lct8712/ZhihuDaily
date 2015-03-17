package com.chentian.zhihudaily.domain.respository;

import java.util.*;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.util.Log;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.chentian.zhihudaily.common.util.CollectionUtils;
import com.chentian.zhihudaily.common.util.Const;
import com.chentian.zhihudaily.common.util.ThreadUtils;
import com.chentian.zhihudaily.data.datasource.CacheableDataSource;
import com.chentian.zhihudaily.data.datasource.file.FileDataSource;
import com.chentian.zhihudaily.data.datasource.rest.RestDataSource;
import com.chentian.zhihudaily.data.model.*;
import com.chentian.zhihudaily.domain.bus.ThemeResponse;
import com.orm.query.Select;
import com.squareup.otto.Bus;

/**
 * @author chentian
 */
public class DataRepositoryImpl implements DataRepository {

  private Bus uiBus;
  private RestDataSource restDataSource;
  private FileDataSource fileDataSource;
  private CacheableDataSource cacheableDataSource;

  public DataRepositoryImpl(Context context, Bus uiBus) {
    this.uiBus = uiBus;

    restDataSource = new RestDataSource();
    fileDataSource = new FileDataSource(context);
    cacheableDataSource = new CacheableDataSource(context);
  }

  @Override
  public void getStoryDetail(long id, Callback<StoryDetail> callback) {
    cacheableDataSource.getStoryDetail(id, callback);
  }

  @Override
  public void getStartImage(Callback<StartImage> callback) {
    restDataSource.getStartImage(callback);
  }

  @Override
  public void getStoryExtra(long id, Callback<StoryDetail> callback) {
    restDataSource.getStoryExtra(id, callback);
  }

  @Override
  public void getShortComments(long id, Callback<CommentCollection> callback) {
    restDataSource.getShortComments(id, callback);
  }

  @Override
  public void getLongComments(long id, Callback<CommentCollection> callback) {
    restDataSource.getLongComments(id, callback);
  }

  @Override
  public void syncLatestStoryCollection() {
    StorySyncUtil.syncLatestStoryCollection(restDataSource, fileDataSource, uiBus);
  }

  @Override
  public void syncBeforeStoryCollection(String date) {
    StorySyncUtil.syncBeforeStoryCollection(date, restDataSource, fileDataSource, uiBus);
  }

  @Override
  public void syncThemeLatestStoryCollection(final long themeId) {
    StorySyncUtil.syncThemeLatestStoryCollection(themeId, restDataSource, fileDataSource, uiBus);
  }

  @Override
  public void syncThemeBeforeStoryCollection(final long themeId, final long storyId) {
    StorySyncUtil.syncThemeBeforeStoryCollection(themeId, storyId, restDataSource, fileDataSource, uiBus);
  }

  @Override
  public void markStoryAsRead(StoryAbstract storyAbstract) {
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

  @Override
  public void syncThemeCollection() {
    // Load from database
    new AsyncTask<Void, Void, Void>() {
      @Override
      protected Void doInBackground(Void... params) {
        uiBus.post(new ThemeResponse(listAllTheme()));
        return null;
      }
    }.execute();

    Log.d(Const.LogTag.API, "Get themes starts.");

    // Load from data source
    restDataSource.getThemeCollection(new Callback<ThemeCollection>() {
      @Override
      public void success(final ThemeCollection themeCollection, Response response) {
        new AsyncTask<Void, Void, Void>() {
          @Override
          protected Void doInBackground(Void... params) {
            updateDatabase(themeCollection);
            uiBus.post(new ThemeResponse(listAllTheme()));

            return null;
          }
        }.execute();

        Log.d(Const.LogTag.API, "Get themes success, size:" +
                CollectionUtils.notNull(themeCollection.getOthers()).size());
      }

      @Override
      public void failure(RetrofitError error) {
        Log.d(Const.LogTag.API, "Get themes failed: " + error);
      }
    });
  }

  @Override
  public void saveTheme(final Theme theme) {
    new AsyncTask<Void, Void, Void>() {
      @Override
      protected Void doInBackground(Void... params) {
        theme.save();
        uiBus.post(new ThemeResponse(listAllTheme()));

        return null;
      }
    }.execute();
  }

  @Override
  public void startOffline() {
    new OfflineHelper().run(restDataSource, uiBus);
  }

  /**
   * Update themes in database
   * With subscribed ids not be overwritten
   */
  private void updateDatabase(ThemeCollection themeCollection) {
    ThreadUtils.checkRunningOnNonUiThread();

    Set<Long> subscribedThemeIds = new HashSet<>();
    for (Theme theme : CollectionUtils.notNull(Theme.listAll(Theme.class))) {
      if (theme.isSubscribed()) {
        subscribedThemeIds.add(theme.getThemeApiId());
      }
    }

    List<Theme> subscribedThemes = CollectionUtils.notNull(themeCollection.getSubscribed());
    List<Theme> otherThemes = new ArrayList<>();
    for (Theme theme : CollectionUtils.notNull(themeCollection.getOthers())) {
      if (subscribedThemeIds.contains(theme.getThemeApiId())) {
        subscribedThemes.add(theme);
      } else {
        otherThemes.add(theme);
      }
    }

    setThemeSubscribed(subscribedThemes, true);
    setThemeSubscribed(otherThemes, false);

    Theme.deleteAll(Theme.class);
    Theme.saveInTx(subscribedThemes);
    Theme.saveInTx(otherThemes);
  }

  private List<Theme> listAllTheme() {
    ThreadUtils.checkRunningOnNonUiThread();

    try {
      return Select.from(Theme.class).orderBy("is_subscribed desc").list();
    } catch (SQLiteException e) {
      return Collections.emptyList();
    }
  }

  private void setThemeSubscribed(List<Theme> themes, boolean isSubscribed) {
    for (Theme theme : themes) {
      theme.setSubscribed(isSubscribed);
    }
  }
}
