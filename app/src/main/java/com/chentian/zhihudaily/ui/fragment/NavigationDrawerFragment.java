package com.chentian.zhihudaily.ui.fragment;

import java.util.List;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.*;

import com.chentian.zhihudaily.R;
import com.chentian.zhihudaily.data.model.Theme;
import com.chentian.zhihudaily.mvp.presenter.NavigationDrawerPresenter;
import com.chentian.zhihudaily.mvp.presenter.impl.NavigationDrawerPresenterImpl;
import com.chentian.zhihudaily.mvp.view.MVPNavigationDrawerView;
import com.chentian.zhihudaily.ui.adapter.ThemeAdapter;
import com.chentian.zhihudaily.util.ViewUtils;

/**
 * Navigation drawer in the left
 *
 * Following the post:
 *   http://www.myandroidsolutions.com/2014/12/16/android-lollipop-navigation-drawer-animation-support/
 *
 * Guidelines for navigation drawer:
 *   https://developer.android.com/design/patterns/navigation-drawer.html
 *   http://www.google.com/design/spec/patterns/navigation-drawer.html
 *
 * @author chentian
 */
public class NavigationDrawerFragment extends Fragment implements MVPNavigationDrawerView {

  private NavigationDrawerPresenter presenter;

  private View drawerContainer;
  private DrawerLayout drawerLayout;
  private ActionBarDrawerToggle drawerToggle;
  private Toolbar toolbar;
  private RecyclerView listViewTheme;
  private ThemeAdapter themeAdapter;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_left_drawer, container, false);
    listViewTheme = (RecyclerView) rootView.findViewById(R.id.list_theme);

    themeAdapter = new ThemeAdapter(getContext());
    themeAdapter.setThemeSubscribedListener(new ThemeAdapter.ThemeSubscribedListener() {
      @Override
      public void onThemeSubscribed(Theme theme) {
        presenter.onThemeSubscribed(theme);
      }
    });
    themeAdapter.setThemeItemSelectListener(new ThemeAdapter.ThemeItemSelectListener() {
      @Override
      public void onMainPageItemSelect(View view) {
        presenter.onMainPageItemSelect(view);
      }

      @Override
      public void onThemeItemSelected(Theme theme, View view) {
        presenter.onThemeItemSelected(theme, view);
      }
    });

    listViewTheme.setLayoutManager(new LinearLayoutManager(getContext()));
    listViewTheme.setAdapter(themeAdapter);

    presenter = new NavigationDrawerPresenterImpl(this);

    return rootView;
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    drawerToggle.onConfigurationChanged(newConfig);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
  }

  @Override
  public void onResume() {
    super.onResume();
    presenter.onResume();
  }

  @Override
  public void onPause() {
    super.onPause();
    presenter.onPause();
  }

  @Override
  public Context getContext() {
    return getActivity();
  }

  @Override
  public void showThemes(List<Theme> themes) {
    themeAdapter.setThemeList(themes);
  }

  @Override
  public void setToolbarTitle(String title) {
    toolbar.setTitle(title);
  }

  public void setUp(View drawerContainer, DrawerLayout drawerLayout, Toolbar toolbar) {
    this.drawerContainer = drawerContainer;
    this.drawerLayout = drawerLayout;
    this.toolbar = toolbar;

    drawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar,
            R.string.drawer_open, R.string.drawer_close) {
      @Override
      public void onDrawerOpened(View drawerView) {
        super.onDrawerOpened(drawerView);
        if (isAdded()) {
          getActivity().supportInvalidateOptionsMenu();
        }
      }

      @Override
      public void onDrawerClosed(View drawerView) {
        super.onDrawerClosed(drawerView);
        if (isAdded()) {
          getActivity().supportInvalidateOptionsMenu();
        }
      }
    };

    drawerToggle.setDrawerIndicatorEnabled(true);
    drawerLayout.setDrawerListener(drawerToggle);
    drawerLayout.closeDrawer(drawerContainer);
  }

  public void openDrawer() {
    drawerLayout.openDrawer(Gravity.START);
  }

  @Override
  public void closeDrawer() {
    drawerLayout.closeDrawer(drawerContainer);
  }

  @Override
  public void highlightListItem(View view) {
    themeAdapter.setSelectedItemView(view);
    for (int i = 0; i < listViewTheme.getChildCount(); i++) {
      View child = listViewTheme.getChildAt(i);
      boolean isSelected = (child == view);
      ViewUtils.setSelectedBackground(child, isSelected, getContext());
    }
  }

  public ActionBarDrawerToggle getDrawerToggle() {
    return drawerToggle;
  }
}
