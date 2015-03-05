package com.chentian.zhihudaily.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.View;

import com.chentian.zhihudaily.R;
import com.chentian.zhihudaily.data.model.ReadStory;
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

    Theme theme = new Theme();
    theme.setName("name");
    theme.save();

    ReadStory readStory = new ReadStory(100L);
    readStory.save();

    toolbar.setTitle(getString(R.string.today_story));
    navigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().
            findFragmentById(R.id.navigation_drawer);
    navigationDrawerFragment.setUp(findViewById(R.id.navigation_drawer),
            (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

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
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    navigationDrawerFragment.getDrawerToggle().syncState();
  }
}
