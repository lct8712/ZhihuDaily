package com.chentian.zhihudaily.common.provider;

import android.content.Context;
import android.content.SharedPreferences;

import com.chentian.zhihudaily.common.util.Const;

/**
 * Provide current ui mode: day mode or night mode
 *
 * @author chentian
 */
public class UiModeProvider {

  public static enum UiMode {
    DayMode, NightMode
  }

  private static UiModeProvider instance;

  private UiMode currentUiMode;

  public static UiModeProvider getInstance() {
    if (instance == null) {
      instance = new UiModeProvider();
    }
    return instance;
  }

  /**
   * Get current ui mode
   */
  public UiMode get(Context context) {
    if (currentUiMode == null) {
      SharedPreferences sharedPreferences = getSharedPreferences(context);
      String uiModeName = sharedPreferences.getString(Const.SharedPreference.KEY_UI_MODE, UiMode.DayMode.name());
      currentUiMode = UiMode.valueOf(uiModeName);
    }
    return currentUiMode;
  }

  /**
   * Change ui mode to night mode if current is day mode or
   * change it to day mode if current is night mode
   */
  public void changeUiMode(Context context) {
    currentUiMode = (currentUiMode == UiMode.DayMode) ? UiMode.NightMode : UiMode.DayMode;

    SharedPreferences sharedPreferences = getSharedPreferences(context);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString(Const.SharedPreference.KEY_UI_MODE, currentUiMode.name());
    editor.apply();
  }

  private SharedPreferences getSharedPreferences(Context context) {
    return context.getSharedPreferences(
            Const.SharedPreference.PREFERENCE_NAME, Context.MODE_MULTI_PROCESS);
  }
}
