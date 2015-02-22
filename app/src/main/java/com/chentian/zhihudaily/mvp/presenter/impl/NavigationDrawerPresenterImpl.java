package com.chentian.zhihudaily.mvp.presenter.impl;

import java.util.ArrayList;

import android.os.AsyncTask;

import com.chentian.zhihudaily.common.provider.BusProvider;
import com.chentian.zhihudaily.data.dao.ThemeDao;
import com.chentian.zhihudaily.data.datasource.DataSource;
import com.chentian.zhihudaily.data.model.Theme;
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

    DataSource.getInstance(navigationDrawerView.getContext()).syncThemeCollection();
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

}
