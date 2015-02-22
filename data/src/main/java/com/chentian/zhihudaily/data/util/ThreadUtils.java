package com.chentian.zhihudaily.data.util;

import android.os.Handler;
import android.os.Looper;

import com.chentian.zhihudaily.data.BuildConfig;

/**
 * @author chentian
 */
public class ThreadUtils {

  private static Handler mainThreadHandler = new Handler(Looper.getMainLooper());

  public static void checkRunningOnNonUiThread() {
    if (BuildConfig.DEBUG && Looper.myLooper() == Looper.getMainLooper()) {
      throw new AssertionError("Run this on non UI thread");
    }
  }

  public static void runOnMainThread(Runnable runnable) {
    mainThreadHandler.post(runnable);
  }
}
