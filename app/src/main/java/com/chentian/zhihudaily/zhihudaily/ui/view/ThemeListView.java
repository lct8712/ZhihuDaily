package com.chentian.zhihudaily.zhihudaily.ui.view;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.chentian.zhihudaily.common.util.CollectionUtils;
import com.chentian.zhihudaily.common.util.Const;
import com.chentian.zhihudaily.data.dao.ThemeDao;
import com.chentian.zhihudaily.data.datasource.DataSource;
import com.chentian.zhihudaily.data.model.ThemeCollection;
import com.chentian.zhihudaily.zhihudaily.adapter.ThemeAdapter;

/**
 * List view of themes, shows in left drawer, including both subscribed and un-subscribed themes
 *
 * @author chentian
 */
public class ThemeListView extends RecyclerView {

  private ThemeAdapter adapter;

  public ThemeListView(Context context, AttributeSet attrs) {
    super(context, attrs);

    setLayoutManager(new LinearLayoutManager(context));

    adapter = new ThemeAdapter(context);
    setAdapter(adapter);
  }

  /**
   * Load themes from database, then update them with web api result
   */
  public void loadThemes() {
    loadThemesFromDatabase();

    DataSource.getInstance().getLatestThemeCollection(new Callback<ThemeCollection>() {
      @Override
      public void success(final ThemeCollection themeCollection, Response response) {
        new AsyncTask<Void, Void, Void>() {
          @Override
          protected Void doInBackground(Void... params) {
            ThemeDao.updateDatabase(themeCollection);
            adapter.reloadThemeList();

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

  public void loadThemesFromDatabase() {
    new AsyncTask<Void, Void, Void>() {
      @Override
      protected Void doInBackground(Void... params) {
        adapter.reloadThemeList();

        return null;
      }
    }.execute();
  }
}
