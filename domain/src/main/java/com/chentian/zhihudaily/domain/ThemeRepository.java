package com.chentian.zhihudaily.domain;

import java.util.*;

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
import com.chentian.zhihudaily.data.datasource.DataSource;
import com.chentian.zhihudaily.data.model.Theme;
import com.chentian.zhihudaily.data.model.ThemeCollection;
import com.chentian.zhihudaily.common.util.ThreadUtils;
import com.chentian.zhihudaily.domain.bus.ThemeResponse;
import com.orm.query.Select;

/**
 * Represents a repository for reading and writing {@link Theme} related data.
 *
 * Methods starts with "sync" may return a result more than once,
 * so they post result to BUS instead of using callback.
 *
 * @author chentian
 */
public class ThemeRepository {

  /**
   * Update themes in database
   * With subscribed ids not be overwritten
   */
  public static void updateDatabase(ThemeCollection themeCollection) {
    ThreadUtils.checkRunningOnNonUiThread();

    Set<Long> subscribedThemeIds = new HashSet<>();
    for (Theme theme : CollectionUtils.notNull(Theme.listAll(Theme.class))) {
      if (theme.isSubscribed()) {
        subscribedThemeIds.add(theme.getThemeApiId());
      }
    }

    List<Theme> subscribedThemes = themeCollection.getSubscribed();
    List<Theme> otherThemes = new ArrayList<>();
    for (Theme theme : themeCollection.getOthers()) {
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

  public static List<Theme> listAll() {
    ThreadUtils.checkRunningOnNonUiThread();

    try {
      return Select.from(Theme.class).orderBy("is_subscribed desc").list();
    } catch (SQLiteException e) {
      return Collections.emptyList();
    }
  }

  public static void saveTheme(final Theme theme) {
    new AsyncTask<Void, Void, Void>() {
      @Override
      protected Void doInBackground(Void... params) {
        theme.save();
        BusProvider.getUiBus().post(new ThemeResponse(listAll()));

        return null;
      }
    }.execute();
  }

  public static void syncThemeCollection(Context context) {
    // Load from database
    new AsyncTask<Void, Void, Void>() {
      @Override
      protected Void doInBackground(Void... params) {
        BusProvider.getUiBus().post(new ThemeResponse(listAll()));
        return null;
      }
    }.execute();

    // Load from data source
    DataSource.getInstance(context).
            getThemeCollection(new Callback<ThemeCollection>() {
      @Override
      public void success(final ThemeCollection themeCollection, Response response) {
        new AsyncTask<Void, Void, Void>() {
          @Override
          protected Void doInBackground(Void... params) {
            updateDatabase(themeCollection);
            BusProvider.getUiBus().post(new ThemeResponse(listAll()));

            return null;
          }
        }.execute();

        Log.d(Const.LogTag.API, "Load themes success, size:" +
                CollectionUtils.notNull(themeCollection.getOthers()).size());
      }

      @Override
      public void failure(RetrofitError error) {
        Log.d(Const.LogTag.API, "Load themes failed: " + error);
      }
    });
  }

  private static void setThemeSubscribed(List<Theme> themes, boolean isSubscribed) {
    for (Theme theme : themes) {
      theme.setSubscribed(isSubscribed);
    }
  }
}
