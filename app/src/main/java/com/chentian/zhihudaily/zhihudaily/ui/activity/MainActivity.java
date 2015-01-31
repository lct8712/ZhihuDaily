package com.chentian.zhihudaily.zhihudaily.ui.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.chentian.zhihudaily.zhihudaily.R;
import com.chentian.zhihudaily.zhihudaily.ui.fragment.StoryListFragment;
import com.chentian.zhihudaily.zhihudaily.ui.view.LeftDrawerView;

/**
 * Main activity, showing story list
 */
public class MainActivity extends ActionBarActivity {

  private ActionBarDrawerToggle drawerToggle;
  private DrawerLayout drawerLayout;
  private LeftDrawerView leftDrawerView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    leftDrawerView = (LeftDrawerView) findViewById(R.id.left_drawer_view);

    final ActionBar actionBar = getSupportActionBar();
    drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_drawer,
            R.string.drawer_open, R.string.drawer_close) {
      public void onDrawerClosed(View view) {
        if (actionBar != null) {
          actionBar.setTitle(getTitle());
        }
        invalidateOptionsMenu();
      }

      public void onDrawerOpened(View drawerView) {
        if (actionBar != null) {
          actionBar.setTitle(getTitle());
        }
        invalidateOptionsMenu();
      }
    };
    drawerLayout.setDrawerListener(drawerToggle);
    drawerLayout.closeDrawer(leftDrawerView);

    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setHomeButtonEnabled(true);
    }

    if (savedInstanceState == null) {
      StoryListFragment storyListFragment = new StoryListFragment();
      getSupportFragmentManager().beginTransaction()
              .add(R.id.content_frame, storyListFragment)
              .commit();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_main, menu);
    return super.onCreateOptionsMenu(menu);
  }

  /* Called whenever we call invalidateOptionsMenu() */
  @Override
  public boolean onPrepareOptionsMenu(Menu menu) {
    // If the nav drawer is open, hide action items related to the content view
    boolean drawerOpen = drawerLayout.isDrawerOpen(leftDrawerView);
    menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
    return super.onPrepareOptionsMenu(menu);
  }

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    drawerToggle.syncState();
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

    switch (item.getItemId()) {
      case R.id.action_settings:
        break;
      default:
        break;
    }
    return super.onOptionsItemSelected(item);
  }
}
