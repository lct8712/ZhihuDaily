package com.chentian.zhihudaily.zhihudaily.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.chentian.zhihudaily.zhihudaily.R;
import com.chentian.zhihudaily.zhihudaily.ui.fragment.StoryListFragment;


public class MainActivity extends ActionBarActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    if (savedInstanceState == null) {
      StoryListFragment storyListFragment = new StoryListFragment();
      getSupportFragmentManager().beginTransaction()
              .add(R.id.container, storyListFragment)
              .commit();
    }
  }

}
