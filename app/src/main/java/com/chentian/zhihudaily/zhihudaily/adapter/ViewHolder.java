package com.chentian.zhihudaily.zhihudaily.adapter;

import android.util.SparseArray;
import android.view.View;

/**
 * ViewHolder to quick find child views in adapter->getView()
 * Copied from http://www.piwai.info/android-adapter-good-practices/
 *
 * @author chentian
 */
public class ViewHolder {
  // I added a generic return type to reduce the casting noise in client code
  @SuppressWarnings("unchecked")
  public static <T extends View> T get(View view, int id) {
    SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
    if (viewHolder == null) {
      viewHolder = new SparseArray<View>();
      view.setTag(viewHolder);
    }
    View childView = viewHolder.get(id);
    if (childView == null) {
      childView = view.findViewById(id);
      viewHolder.put(id, childView);
    }
    return (T) childView;
  }
}
