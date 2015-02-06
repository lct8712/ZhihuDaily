package com.chentian.zhihudaily.zhihudaily.ui.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.chentian.zhihudaily.zhihudaily.R;

/**
 * @author chentian
 */
public class RecycleViewWithDivider extends RecyclerView {

  protected Context context;
  protected LinearLayoutManager layoutManager;

  public RecycleViewWithDivider(Context context, AttributeSet attrs) {
    super(context, attrs);

    layoutManager = new LinearLayoutManager(context);
    setLayoutManager(layoutManager);

    Drawable divider = getResources().getDrawable(R.drawable.story_list_item_divider);
    addItemDecoration(new DividerItemDecoration(LinearLayoutManager.VERTICAL, divider));

    this.context = context;
  }
}
