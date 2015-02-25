package com.chentian.zhihudaily.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.chentian.zhihudaily.R;
import com.chentian.zhihudaily.ui.adapter.ThemeAdapter;
import com.chentian.zhihudaily.data.model.Theme;
import com.chentian.zhihudaily.ui.fragment.NavigationDrawerFragment;
import com.chentian.zhihudaily.ui.fragment.StoryListFragment;

/**
 * Main activity, showing story list
 *
 * @author chentian
 */
public class MainActivity extends BaseActivity {

  private NavigationDrawerFragment navigationDrawerFragment;
  private StoryListFragment storyListFragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    toolbar.setTitle(getString(R.string.today_story));
    navigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().
            findFragmentById(R.id.navigation_drawer);
    navigationDrawerFragment.setUp(findViewById(R.id.navigation_drawer),
            (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

    navigationDrawerFragment.setThemeItemCallback(new ThemeAdapter.ThemeItemSelectListener() {
      @Override
      public void onMainPageItemSelect() {
        toolbar.setTitle(getString(R.string.today_story));
        storyListFragment.loadMainPage();
        navigationDrawerFragment.closeDrawer();
      }

      @Override
      public void onThemeItemSelected(Theme theme) {
        toolbar.setTitle(theme.getName());
        storyListFragment.loadTheme(theme.getThemeApiId());
        navigationDrawerFragment.closeDrawer();
      }
    });

    if (savedInstanceState == null) {
      storyListFragment = new StoryListFragment();
      storyListFragment.setToolbar(toolbar);
      getSupportFragmentManager().beginTransaction()
              .add(R.id.content_container, storyListFragment)
              .commit();
    }
  }

  @Override
  public View onCreateView(View parent, String name, @NonNull Context context, @NonNull AttributeSet attrs) {
    if (toolbar != null) {
      // TODO: test this on a real 5.0 device
      toolbar.getBackground().setAlpha(0xFF);
    }
    return super.onCreateView(parent, name, context, attrs);
  }

  @Override
  protected int getLayoutResource() {
    return R.layout.activity_main;
  }

  @Override
  protected int getActionBarIconResource() {
    return R.drawable.ic_drawer;
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_main, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    navigationDrawerFragment.getDrawerToggle().syncState();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        navigationDrawerFragment.openDrawer();
        break;
      case R.id.action_settings:
        break;
      default:
        break;
    }
    return super.onOptionsItemSelected(item);
  }
}
