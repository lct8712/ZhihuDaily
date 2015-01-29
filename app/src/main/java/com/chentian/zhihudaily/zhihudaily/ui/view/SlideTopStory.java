package com.chentian.zhihudaily.zhihudaily.ui.view;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.chentian.zhihudaily.zhihudaily.R;
import com.chentian.zhihudaily.zhihudaily.api.model.StoryAbstract;
import com.chentian.zhihudaily.zhihudaily.util.ViewUtils;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

/**
 * Slide images show top stories
 *
 * @author chentian
 */
public class SlideTopStory extends SliderLayout {

  private Context context;

  public SlideTopStory(Context context, AttributeSet attrs) {
    super(context, attrs);

    this.context = context;
  }

  public static SlideTopStory newInstanceExpandableDescription(ViewGroup parent) {
    return (SlideTopStory) ViewUtils.newInstance(parent, R.layout.slide_top_story);
  }

  public void setTopStories(List<StoryAbstract> stories) {
    if (stories == null) {
      return;
    }

    for (StoryAbstract story : stories) {
      if (TextUtils.isEmpty(story.getImageUrl())) {
        continue;
      }

      TextSliderView textSliderView = new TextSliderView(context);
      textSliderView.description(story.getTitle()).image(story.getImageUrl())
              .setScaleType(BaseSliderView.ScaleType.CenterCrop);
      addSlider(textSliderView);
    }
  }
}
