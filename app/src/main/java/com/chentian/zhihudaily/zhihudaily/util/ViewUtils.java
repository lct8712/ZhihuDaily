package com.chentian.zhihudaily.zhihudaily.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author chentian
 */
public class ViewUtils {

  /**
   * Creates a view.
   *
   * @param parent parent view
   * @param resId resource id
   * @return view
   */
  public static View newInstance(ViewGroup parent, int resId) {
    return LayoutInflater.from(parent.getContext()).inflate(resId, parent, false);
  }

  /**
   * Creates a view.
   *
   * @param context context
   * @param resId resource id
   * @return view
   */
  public static View newInstance(Context context, int resId) {
    return LayoutInflater.from(context).inflate(resId, null);
  }
}
