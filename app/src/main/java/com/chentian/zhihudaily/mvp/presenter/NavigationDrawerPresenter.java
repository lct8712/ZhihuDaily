package com.chentian.zhihudaily.mvp.presenter;

import android.view.View;

import com.chentian.zhihudaily.data.model.Theme;

/**
 * @author chentian
 */
public interface NavigationDrawerPresenter extends MVPPresenter {

  void onThemeSubscribed(Theme theme);

  void onMainPageItemSelect(View view);

  void onThemeItemSelected(Theme theme, View view);

}
