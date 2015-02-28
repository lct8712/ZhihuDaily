package com.chentian.zhihudaily.mvp.presenter.impl;

import android.view.View;

import com.chentian.zhihudaily.R;
import com.chentian.zhihudaily.common.provider.BusProvider;
import com.chentian.zhihudaily.data.model.Theme;
import com.chentian.zhihudaily.domain.ThemeRepository;
import com.chentian.zhihudaily.domain.bus.DrawerItemSelectedEvent;
import com.chentian.zhihudaily.domain.bus.ThemeResponse;
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

  @Override
  public void onMainPageItemSelect(View view) {
    navigationDrawerView.setToolbarTitle(navigationDrawerView.getContext().getString(R.string.today_story));
    navigationDrawerView.highlightListItem(view);
    navigationDrawerView.closeDrawer();

    BusProvider.getUiBus().post(new DrawerItemSelectedEvent(DrawerItemSelectedEvent.ItemType.MainPage));
  }

  @Override
  public void onThemeItemSelected(Theme theme, View view) {
    navigationDrawerView.setToolbarTitle(theme.getName());
    navigationDrawerView.highlightListItem(view);
    navigationDrawerView.closeDrawer();

    BusProvider.getUiBus().post(
            new DrawerItemSelectedEvent(DrawerItemSelectedEvent.ItemType.Theme, theme.getThemeApiId()));
  }

  @Subscribe
  public void onThemeListUpdate(ThemeResponse themeResponse) {
    navigationDrawerView.showThemes(themeResponse.getThemeList());
  }
}
