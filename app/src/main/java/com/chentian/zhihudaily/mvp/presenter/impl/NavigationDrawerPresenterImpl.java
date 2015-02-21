package com.chentian.zhihudaily.mvp.presenter.impl;

import java.util.ArrayList;

import android.os.AsyncTask;
import android.util.Log;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.chentian.zhihudaily.common.provider.BusProvider;
import com.chentian.zhihudaily.common.util.CollectionUtils;
import com.chentian.zhihudaily.common.util.Const;
import com.chentian.zhihudaily.data.dao.ThemeDao;
import com.chentian.zhihudaily.data.datasource.DataSource;
import com.chentian.zhihudaily.data.model.Theme;
import com.chentian.zhihudaily.data.model.ThemeCollection;
import com.chentian.zhihudaily.mvp.presenter.NavigationDrawerPresenter;
import com.chentian.zhihudaily.mvp.view.MVPNavigationDrawerView;
import com.squareup.otto.Subscribe;

/**
 * @author chentian
 */
public class NavigationDrawerPresenterImpl implements NavigationDrawerPresenter {

  private MVPNavigationDrawerView navigationDrawerView;

  public NavigationDrawerPresenterImpl(MVPNavigationDrawerView navigationDrawerView) {
    this.navigationDrawerView = navigationDrawerView;
  }

  @Override
  public void onResume() {
    BusProvider.getUiBus().register(this);

    loadThemes();
  }

  @Override
  public void onPause() {
    BusProvider.getUiBus().unregister(this);
  }

  @Override
  public void onThemeSubscribed(final Theme theme) {
    new AsyncTask<Void, Void, Void>() {
      @Override
      protected Void doInBackground(Void... params) {
        theme.setSubscribed(true);
        theme.save();
        BusProvider.getUiBus().post(ThemeDao.listAll());

        return null;
      }
    }.execute();
  }

  @Subscribe
  public void onThemeListUpdate(ArrayList<Theme> themes) {
    navigationDrawerView.showThemes(themes);
  }

  /**
   * Load themes from database, then update them with web api result
   */
  private void loadThemes() {
    // Load from database
    // TODO: move this to data layer
    new AsyncTask<Void, Void, Void>() {
      @Override
      protected Void doInBackground(Void... params) {
        BusProvider.getUiBus().post(ThemeDao.listAll());
        return null;
      }
    }.execute();

    // Load from data source
    DataSource.getInstance().getLatestThemeCollection(new Callback<ThemeCollection>() {
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
