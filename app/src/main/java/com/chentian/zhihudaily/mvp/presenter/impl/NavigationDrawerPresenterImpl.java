package com.chentian.zhihudaily.mvp.presenter.impl;

import java.util.ArrayList;

import com.chentian.zhihudaily.common.provider.BusProvider;
import com.chentian.zhihudaily.data.model.Theme;
import com.chentian.zhihudaily.domain.ThemeRepository;
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

    ThemeRepository.syncThemeCollection(navigationDrawerView.getContext());
  }

  @Override
  public void onPause() {
    BusProvider.getUiBus().unregister(this);
  }

  @Override
  public void onThemeSubscribed(final Theme theme) {
    theme.setSubscribed(true);
    ThemeRepository.saveTheme(theme);
  }

  @Subscribe
  public void onThemeListUpdate(ArrayList<Theme> themes) {
    navigationDrawerView.showThemes(themes);
  }
}
