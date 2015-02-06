package com.chentian.zhihudaily.zhihudaily.ui.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chentian.zhihudaily.zhihudaily.R;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;

/**
 * A slider with a title
 *
 * @author chentian
 */
public class TitleSliderView extends BaseSliderView {

  private String title;

  public TitleSliderView(Context context) {
    super(context);
  }

  @Override
  public View getView() {
    ArticleHeaderView view = ArticleHeaderView.newInstance(getContext());
    ImageView target = (ImageView) view.findViewById(R.id.daimajia_slider_image);
    TextView txtTitle = (TextView) view.findViewById(R.id.title);
    txtTitle.setText(title);
    bindEventAndShow(view, target);
    return view;
  }

  public TitleSliderView title(String title) {
    this.title = title;
    return this;
  }
}
