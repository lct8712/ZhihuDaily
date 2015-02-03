package com.chentian.zhihudaily.zhihudaily.ui.view;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ListView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.chentian.zhihudaily.zhihudaily.adapter.ThemeAdapter;
import com.chentian.zhihudaily.zhihudaily.api.RestClient;
import com.chentian.zhihudaily.zhihudaily.api.model.ThemeCollection;
import com.chentian.zhihudaily.zhihudaily.dao.ThemeDao;
import com.chentian.zhihudaily.zhihudaily.util.CollectionUtils;
import com.chentian.zhihudaily.zhihudaily.util.Const;

/**
 * @author chentian
 */
public class ThemeListView extends ListView {

  private ThemeAdapter adapter;

  public ThemeListView(Context context, AttributeSet attrs) {
    super(context, attrs);

    adapter = new ThemeAdapter(context);
    setAdapter(adapter);
  }

  /**
   * Load themes from database, then update them with web api result
   */
  public void loadThemes() {
    loadThemesFromDatabase();

    RestClient.getInstance().getThemeService().getLatestThemeCollection(new Callback<ThemeCollection>() {
      @Override
      public void success(final ThemeCollection themeCollection, Response response) {
        new AsyncTask<Void, Void, Void>() {
          @Override
          protected Void doInBackground(Void... params) {
            ThemeDao.updateDatabase(themeCollection);
            adapter.setThemeList();

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
        adapter.setThemeList();

        return null;
      }
    }.execute();
  }
}
