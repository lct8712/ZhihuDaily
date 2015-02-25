package com.chentian.zhihudaily.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.chentian.zhihudaily.R;
import com.chentian.zhihudaily.ui.fragment.StoryDetailFragment;
import com.chentian.zhihudaily.common.util.Const;
import com.chentian.zhihudaily.util.ViewUtils;

/**
 * Activity to read story detail content
 *
 * @author chentian
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
              .add(R.id.content_container, fragment)
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
        ViewUtils.finishActivityWithSlideAnim(this);
        break;
      case R.id.action_settings:
        break;
      default:
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onBackPressed() {
    ViewUtils.finishActivityWithSlideAnim(this);
  }
}
