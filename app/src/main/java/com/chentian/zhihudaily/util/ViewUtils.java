package com.chentian.zhihudaily.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chentian.zhihudaily.DailyApplication;
import com.chentian.zhihudaily.R;
import com.chentian.zhihudaily.common.provider.UiModeProvider;
import com.chentian.zhihudaily.ui.activity.DetailActivity;

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

  /**
   * Open story detail activity, with an animate
   */
  public static void openDetailActivity(long id, Context context) {
    Intent intent = new Intent(context, DetailActivity.class);
    intent.putExtra(DetailActivity.EXTRA_ID, id);
    context.startActivity(intent);
    if (context instanceof Activity) {
      ((Activity) context).overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
    }
  }

  /**
   * Close an activity with an slice animate, usually applies to a detail activity
   */
  public static void finishActivityWithSlideAnim(Activity activity) {
    activity.finish();
    activity.overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
  }

  /**
   * Set view's background as selected or normal
   */
  public static void setSelectedBackground(View view, boolean isSelected, Context context) {
    int attrId = isSelected ? R.attr.colorLeftDrawerBackgroundSelected : R.attr.colorLeftDrawerBackgroundNormal;
    view.setBackgroundColor(getAttrValue(context, attrId));
  }

  /**
   * Get value from attr, such as color
   */
  public static int getAttrValue(Context context, int attrId) {
    TypedValue typedValue = new TypedValue();
    context.getTheme().resolveAttribute(attrId, typedValue, true);
    return  typedValue.data;
  }

  /**
   * Get resource id from attr, such as drawable id
   */
  public static int getAttrResourceId(Context context, int attrId) {
    TypedArray typedArray = context.getTheme().obtainStyledAttributes(new int[]{attrId});
    return typedArray.getResourceId(0, 0);
  }

  /**
   * Set activity theme by current setting
   */
  public static void setTheme(Activity activity) {
    UiModeProvider.UiMode uiMode = DailyApplication.getInstance().getUiModeProvider().getCurrentMode(activity);
    switch (uiMode) {
      case DayMode:
        activity.setTheme(R.style.AppTheme_Light);
        break;
      case NightMode:
        activity.setTheme(R.style.AppTheme_Dark);
        break;
      default:
        throw new IllegalArgumentException("UI mode not found: " + uiMode.name());
    }
  }
}
