package com.chentian.zhihudaily.zhihudaily.ui.view;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.chentian.zhihudaily.zhihudaily.R;
import com.chentian.zhihudaily.zhihudaily.api.model.StoryAbstract;
import com.chentian.zhihudaily.zhihudaily.util.CollectionUtils;
import com.chentian.zhihudaily.zhihudaily.util.ViewUtils;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;

/**
 * Slide of images, showing top stories
 *   https://github.com/daimajia/AndroidImageSlider
 *
 * @author chentian
 */
public class SlideTopStory extends SliderLayout {

  private static final String STORY_ID = "STORY_ID";
  private Context context;

  public SlideTopStory(Context context, AttributeSet attrs) {
    super(context, attrs);

    this.context = context;
  }

  public static SlideTopStory newInstance(ViewGroup parent) {
    return (SlideTopStory) ViewUtils.newInstance(parent, R.layout.slide_top_story);
  }

  public void setTopStories(List<StoryAbstract> stories) {
    removeAllSliders();

    for (StoryAbstract story : CollectionUtils.notNull(stories)) {
      if (TextUtils.isEmpty(story.getImageUrl())) {
        continue;
      }

      TitleSliderView textSliderView = new TitleSliderView(context);
      textSliderView.title(story.getTitle()).image(story.getImageUrl())
              .setScaleType(BaseSliderView.ScaleType.CenterCrop);
      textSliderView.getBundle().putLong(STORY_ID, story.getId());
      textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
        @Override
        public void onSliderClick(BaseSliderView baseSliderView) {
          long id = baseSliderView.getBundle().getLong(STORY_ID);
          ViewUtils.openDetailActivity(id, context);
        }
      });
      addSlider(textSliderView);
    }
  }
}
