package com.chentian.zhihudaily.zhihudaily.util;

import android.os.Looper;

import com.chentian.zhihudaily.zhihudaily.BuildConfig;

/**
 * @author chentian
 */
public class ThreadUtils {
  public static void checkRunningOnMainThread() {
    if (BuildConfig.DEBUG && Looper.getMainLooper().getThread() == Thread.currentThread()) {
      throw new AssertionError("Run this on non UI thread");
    }
  }
}
