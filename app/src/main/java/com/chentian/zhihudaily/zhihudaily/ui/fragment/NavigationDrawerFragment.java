package com.chentian.zhihudaily.zhihudaily.ui.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.AdapterView;

import com.chentian.zhihudaily.zhihudaily.R;
import com.chentian.zhihudaily.zhihudaily.api.model.Theme;
import com.chentian.zhihudaily.zhihudaily.ui.view.ThemeListView;

/**
 * Following the article:
 *   http://www.myandroidsolutions.com/2014/12/16/android-lollipop-navigation-drawer-animation-support/
 * Guidelines for navigation drawer:
 *   https://developer.android.com/design/patterns/navigation-drawer.html
 *   http://www.google.com/design/spec/patterns/navigation-drawer.html
 *
 * @author chentian
 */
public class NavigationDrawerFragment extends Fragment {

  public static interface NavigationDrawerCallback {
    void onItemSelected(int position, Theme theme);
  }

  private static final String STATE_SELECTED_POSITION = "state_selected_position";

  private NavigationDrawerCallback navigationDrawerCallback;
  private int currentSelectPosition = 0;

  private View drawerContainer;
  private DrawerLayout drawerLayout;
  private ActionBarDrawerToggle drawerToggle;
  private ThemeListView listViewTheme;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_left_drawer, container, false);

    listViewTheme = (ThemeListView) rootView.findViewById(R.id.list_theme);
    listViewTheme.loadThemes();

    listViewTheme.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectItem(position);
      }
    });

    return rootView;
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    drawerToggle.onConfigurationChanged(newConfig);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (drawerToggle.onOptionsItemSelected(item)) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
//    super.onSaveInstanceState(outState);
    outState.putInt(STATE_SELECTED_POSITION, currentSelectPosition);
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

  public ActionBarDrawerToggle getDrawerToggle() {
    return drawerToggle;
  }

  public void setNavigationDrawerCallback(NavigationDrawerCallback navigationDrawerCallback) {
    this.navigationDrawerCallback = navigationDrawerCallback;
  }

  private void selectItem(int position) {
    currentSelectPosition = position;

    if (listViewTheme != null) {
      listViewTheme.setItemChecked(position, true);
      if (navigationDrawerCallback != null) {
        navigationDrawerCallback.onItemSelected(position, listViewTheme.getTheme(position));
      }
    }
    if (drawerLayout != null) {
      drawerLayout.closeDrawer(drawerContainer);
    }
  }
}
