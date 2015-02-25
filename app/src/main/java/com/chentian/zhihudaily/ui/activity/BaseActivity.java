package com.chentian.zhihudaily.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.chentian.zhihudaily.R;

/**
 * Activity with toolbar
 *
 * @author chentian
 */
public abstract class BaseActivity extends ActionBarActivity {

  @InjectView(R.id.toolbar_main) Toolbar toolbar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayoutResource());

    ButterKnife.inject(this);

    setSupportActionBar(toolbar);
    getSupportActionBar().setHomeButtonEnabled(true);
    toolbar.setNavigationIcon(getActionBarIconResource());
  }

  protected abstract int getLayoutResource();

  protected abstract int getActionBarIconResource();
}
