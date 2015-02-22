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

import com.chentian.zhihudaily.data.model.Theme;
import com.chentian.zhihudaily.zhihudaily.R;
import com.chentian.zhihudaily.ui.adapter.ThemeAdapter;
import com.chentian.zhihudaily.mvp.presenter.NavigationDrawerPresenter;
import com.chentian.zhihudaily.mvp.presenter.impl.NavigationDrawerPresenterImpl;
import com.chentian.zhihudaily.mvp.view.MVPNavigationDrawerView;

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

  private static final String STATE_SELECTED_POSITION = "state_selected_position";

  private int currentSelectPosition = 0;

  private NavigationDrawerPresenter presenter;
  private View drawerContainer;
  private DrawerLayout drawerLayout;
  private ActionBarDrawerToggle drawerToggle;
  private ThemeAdapter themeAdapter;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_left_drawer, container, false);

    themeAdapter = new ThemeAdapter(getActivity());
    themeAdapter.setThemeSubscribedListener(new ThemeAdapter.ThemeSubscribedListener() {
      @Override
      public void onThemeSubscribed(Theme theme) {
        presenter.onThemeSubscribed(theme);
      }
    });

    RecyclerView listViewTheme = (RecyclerView) rootView.findViewById(R.id.list_theme);
    listViewTheme.setLayoutManager(new LinearLayoutManager(getActivity()));
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
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putInt(STATE_SELECTED_POSITION, currentSelectPosition);
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

  public void setUp(View drawerContainer, DrawerLayout drawerLayout, Toolbar toolbar) {
    this.drawerContainer = drawerContainer;
    this.drawerLayout = drawerLayout;

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

  public void closeDrawer() {
    drawerLayout.closeDrawer(drawerContainer);
  }

  public ActionBarDrawerToggle getDrawerToggle() {
    return drawerToggle;
  }

  public void setThemeItemCallback(ThemeAdapter.ThemeItemSelectListener themeItemSelectListener) {
    themeAdapter.setThemeItemSelectListener(themeItemSelectListener);
  }

  private void selectItem(final int position) {
    currentSelectPosition = position;

    // TODO: highlight selected item
  }
}
