package com.chentian.zhihudaily.data.util;

import android.os.Looper;

import com.chentian.zhihudaily.data.BuildConfig;

/**
 * @author chentian
 */
public class ThreadUtils {
  public static void checkRunningOnNonUiThread() {
    if (BuildConfig.DEBUG && Looper.myLooper() == Looper.getMainLooper()) {
      throw new AssertionError("Run this on non UI thread");
    }
  }
}
