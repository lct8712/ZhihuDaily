package com.chentian.zhihudaily.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.chentian.zhihudaily.DailyApplication;
import com.chentian.zhihudaily.R;
import com.chentian.zhihudaily.common.provider.UiModeProvider;
import com.chentian.zhihudaily.util.ViewUtils;

/**
 * Activity with toolbar
 *
 * @author chentian
 */
public abstract class BaseActivity extends ActionBarActivity {

  @InjectView(R.id.toolbar_main) Toolbar toolbar;

  protected abstract int getLayoutResource();

  protected abstract int getActionBarIconResource();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ViewUtils.setTheme(this);
    setContentView(getLayoutResource());

    ButterKnife.inject(this);

    setSupportActionBar(toolbar);
    getSupportActionBar().setHomeButtonEnabled(true);
    toolbar.setNavigationIcon(getActionBarIconResource());
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_detail, menu);

    // Modify ui mode menu item title
    MenuItem menuItemUiMode = menu.findItem(R.id.action_change_mode);
    UiModeProvider.UiMode uiMode = DailyApplication.getInstance().getUiModeProvider().getCurrentMode(this);
    int resId = (uiMode == UiModeProvider.UiMode.DayMode) ? R.string.action_night_mode : R.string.action_day_mode;
    menuItemUiMode.setTitle(getString(resId));

    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        ViewUtils.finishActivityWithSlideAnim(this);
        break;
      case R.id.action_change_mode:
        changeUiMode();
        break;
      case R.id.action_settings:
        setTheme(R.style.AppTheme_Dark);
        break;
      default:
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  private void changeUiMode() {
    DailyApplication.getInstance().getUiModeProvider().changeUiMode(this);
    invalidateOptionsMenu();

    ViewUtils.setTheme(this);

    finish();
    Intent intent = new Intent(this, this.getClass());
    startActivity(intent);
  }
}
