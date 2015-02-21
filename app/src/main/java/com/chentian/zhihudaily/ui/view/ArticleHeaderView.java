package com.chentian.zhihudaily.ui.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.chentian.zhihudaily.zhihudaily.R;
import com.chentian.zhihudaily.util.ViewUtils;
import com.koushikdutta.ion.Ion;

/**
 * Display a header image in story detail view
 *
 * @author chentian
 */
public class ArticleHeaderView extends RelativeLayout {

  @InjectView(R.id.daimajia_slider_image) ImageView imageViewHeader;
  @InjectView(R.id.title) TextView txtTitle;

  public ArticleHeaderView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public static ArticleHeaderView newInstance(ViewGroup parent) {
    return (ArticleHeaderView) ViewUtils.newInstance(parent, R.layout.article_header_view);
  }

  public static ArticleHeaderView newInstance(Context context) {
    return (ArticleHeaderView) ViewUtils.newInstance(context, R.layout.article_header_view);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();

    ButterKnife.inject(this);

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
