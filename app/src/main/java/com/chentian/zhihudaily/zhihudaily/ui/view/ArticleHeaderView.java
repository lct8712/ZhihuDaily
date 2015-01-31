package com.chentian.zhihudaily.zhihudaily.ui.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chentian.zhihudaily.zhihudaily.R;
import com.koushikdutta.ion.Ion;

/**
 * @author chentian
 */
public class ArticleHeaderView extends RelativeLayout {

  private ImageView imageViewHeader;
  private TextView txtTitle;

  public ArticleHeaderView(Context context) {
    super(context);
    initViews(context);
  }

  public ArticleHeaderView(Context context, AttributeSet attrs) {
    super(context, attrs);
    initViews(context);
  }

  public ArticleHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initViews(context);
  }

  private void initViews(Context context) {
    View.inflate(context, R.layout.title_slide_view, this);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();

    imageViewHeader = (ImageView) findViewById(R.id.daimajia_slider_image);
    txtTitle = (TextView) findViewById(R.id.title);
    findViewById(R.id.loading_bar).setVisibility(View.GONE);
  }

  public void setData(String title, String imageUrl) {
    txtTitle.setText(title);
    if (!TextUtils.isEmpty(imageUrl)) {
      Ion.with(imageViewHeader)
              .load(imageUrl);
    }
  }
}
