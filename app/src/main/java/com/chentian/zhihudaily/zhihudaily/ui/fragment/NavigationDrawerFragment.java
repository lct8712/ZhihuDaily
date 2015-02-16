package com.chentian.zhihudaily.zhihudaily.ui.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.*;

import com.chentian.zhihudaily.zhihudaily.R;
import com.chentian.zhihudaily.zhihudaily.adapter.ThemeAdapter;
import com.chentian.zhihudaily.zhihudaily.ui.view.ThemeListView;

/**
 * Following the post:
 *   http://www.myandroidsolutions.com/2014/12/16/android-lollipop-navigation-drawer-animation-support/
 * Guidelines for navigation drawer:
 *   https://developer.android.com/design/patterns/navigation-drawer.html
 *   http://www.google.com/design/spec/patterns/navigation-drawer.html
 *
 * @author chentian
 */
public class NavigationDrawerFragment extends Fragment {

  private static final String STATE_SELECTED_POSITION = "state_selected_position";

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
    super.onSaveInstanceState(outState);
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

  public void closeDrawer() {
    drawerLayout.closeDrawer(drawerContainer);
  }

  public ActionBarDrawerToggle getDrawerToggle() {
    return drawerToggle;
  }

  public void setThemeItemCallback(ThemeAdapter.ThemeItemCallback themeItemCallback) {
    ThemeAdapter adapter = (ThemeAdapter) listViewTheme.getAdapter();
    adapter.setThemeItemCallback(themeItemCallback);
  }

  private void selectItem(final int position) {
    currentSelectPosition = position;

    if (listViewTheme != null) {
//      listViewTheme.setSelection(position);
//      listViewTheme.setItemChecked(position, true);
//      if (navigationDrawerCallback != null) {
//        navigationDrawerCallback.onThemeItemSelected(position, listViewTheme.getTheme(position));
//      }
    }
    if (drawerLayout != null) {
//      drawerLayout.closeDrawer(drawerContainer);
    }
  }
}
