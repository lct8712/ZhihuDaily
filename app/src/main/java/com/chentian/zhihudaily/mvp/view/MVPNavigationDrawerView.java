package com.chentian.zhihudaily.mvp.view;

import java.util.List;

import android.view.View;

import com.chentian.zhihudaily.data.model.Theme;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing navigation drawer
 *
 * @author chentian
 */
public interface MVPNavigationDrawerView extends MVPView {

  void showThemes(List<Theme> themes);

  void setToolbarTitle(String title);

  void closeDrawer();

  void highlightListItem(View view);
}
