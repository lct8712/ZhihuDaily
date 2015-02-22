package com.chentian.zhihudaily.data.datasource;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.Path;

import com.chentian.zhihudaily.common.provider.BusProvider;
import com.chentian.zhihudaily.common.util.CollectionUtils;
import com.chentian.zhihudaily.common.util.Const;
import com.chentian.zhihudaily.data.dao.ThemeDao;
import com.chentian.zhihudaily.data.datasource.file.FileDataSource;
import com.chentian.zhihudaily.data.datasource.memory.MemoryDataSource;
import com.chentian.zhihudaily.data.datasource.rest.RestDataSource;
import com.chentian.zhihudaily.data.model.*;

/**
 * Use Chain of Responsibility Pattern to implement a serious of data sources with priority:
 *    MemoryDataSource -> FileDataSource -> RestDataSource
 *
 * Methods starts with "sync" have different behaviors.
 * These methods may return a result more than once, so they post result to BUS instead of using callback.
 *
 * @author chentian
 */
public abstract class DataSource {

  private static DataSource instance;

  protected DataSource next;

  protected DataSource(DataSource next) {
    this.next = next;
  }

  private static DataSource createChain(Context context) {
    RestDataSource restDataSource = new RestDataSource(null);
    FileDataSource fileDataSource = new FileDataSource(restDataSource, context);
    return new MemoryDataSource(fileDataSource);
  }

  public static DataSource getInstance(Context context) {
    if (instance == null) {
      instance = createChain(context);
    }
    return instance;
  }

  public void getThemeCollection(Callback<ThemeCollection> callback) {
    next.getThemeCollection(callback);
  }

  public void getStartImage(Callback<StartImage> callback) {
    next.getStartImage(callback);
  }

  public void getLatestStoryCollection(Callback<StoryCollection> callback) {
    next.getLatestStoryCollection(callback);
  }

  public void getBeforeStoryCollection(@Path("date") String date, Callback<StoryCollection> callback) {
    next.getBeforeStoryCollection(date, callback);
  }

  public void getStoryDetail(@Path("id") long id, Callback<StoryDetail> callback) {
    next.getStoryDetail(id, callback);
  }

  public void getThemeStoryCollection(@Path("id") long id, Callback<ThemeStoryCollection> callback) {
    next.getThemeStoryCollection(id, callback);
  }

  public void getStoryExtra(@Path("id") long id, Callback<StoryDetail> callback) {
    next.getStoryExtra(id, callback);
  }

  public void getShortComments(@Path("id") long id, Callback<CommentCollection> callback) {
    next.getShortComments(id, callback);
  }

  public void getLongComments(@Path("id") long id, Callback<CommentCollection> callback) {
    next.getLongComments(id, callback);
  }

  public void syncThemeCollection() {
    // Load from database
    new AsyncTask<Void, Void, Void>() {
      @Override
      protected Void doInBackground(Void... params) {
        BusProvider.getUiBus().post(ThemeDao.listAll());
        return null;
      }
    }.execute();

    // Load from data source
    getThemeCollection(new Callback<ThemeCollection>() {
      @Override
      public void success(final ThemeCollection themeCollection, Response response) {
        new AsyncTask<Void, Void, Void>() {
          @Override
          protected Void doInBackground(Void... params) {
            ThemeDao.updateDatabase(themeCollection);
            BusProvider.getUiBus().post(ThemeDao.listAll());

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
}
