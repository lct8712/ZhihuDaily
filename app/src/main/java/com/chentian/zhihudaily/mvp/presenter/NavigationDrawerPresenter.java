package com.chentian.zhihudaily.mvp.presenter;

import com.chentian.zhihudaily.data.model.Theme;

/**
 * @author chentian
 */
public interface NavigationDrawerPresenter extends MVPPresenter {

  void onThemeSubscribed(Theme theme);

}
