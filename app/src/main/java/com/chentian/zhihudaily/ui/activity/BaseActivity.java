package com.chentian.zhihudaily.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.chentian.zhihudaily.zhihudaily.R;

/**
 * Activity with toolbar
 *
 * @author chentian
 */
public abstract class BaseActivity extends ActionBarActivity {

  protected Toolbar toolbar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayoutResource());

    toolbar = (Toolbar) findViewById(R.id.toolbar_main);
    setSupportActionBar(toolbar);
    getSupportActionBar().setHomeButtonEnabled(true);
    toolbar.setNavigationIcon(getActionBarIconResource());
  }

  protected abstract int getLayoutResource();

  protected abstract int getActionBarIconResource();
}
