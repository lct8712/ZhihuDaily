package com.chentian.zhihudaily.zhihudaily.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.chentian.zhihudaily.zhihudaily.R;
import com.chentian.zhihudaily.zhihudaily.ui.fragment.StoryDetailFragment;

/**
 * Activity to read story detail content
 */
public class DetailActivity extends ActionBarActivity {

  public static final String EXTRA_ID = "detail_activity_extra_id";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);

    if (savedInstanceState == null) {
      StoryDetailFragment fragment = new StoryDetailFragment();
      fragment.setArguments(getIntent().getExtras());
      getSupportFragmentManager().beginTransaction()
              .add(R.id.container, fragment)
              .commit();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_detail, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

}
