package com.chentian.zhihudaily.zhihudaily.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.chentian.zhihudaily.zhihudaily.R;
import com.chentian.zhihudaily.zhihudaily.ui.fragment.StoryDetailFragment;
import com.chentian.zhihudaily.zhihudaily.util.Const;

/**
 * Activity to read story detail content
 */
public class DetailActivity extends BaseActivity {

  public static final String EXTRA_ID = "detail_activity_extra_id";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (savedInstanceState == null) {
      StoryDetailFragment fragment = new StoryDetailFragment();
      fragment.setToolbar(toolbar);
      fragment.setArguments(getIntent().getExtras());
      getSupportFragmentManager().beginTransaction()
              .add(R.id.content_frame, fragment)
              .commit();
    }

    getSupportActionBar().setTitle(Const.EMPTY_STRING);
    toolbar.getBackground().setAlpha(0);
  }

  @Override
  protected int getLayoutResource() {
    return R.layout.activity_detail;
  }

  @Override
  protected int getActionBarIconResource() {
    return R.drawable.ic_action_back;
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_detail, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        finish();
        break;
      case R.id.action_settings:
        break;
      default:
        break;
    }
    return super.onOptionsItemSelected(item);
  }

}
