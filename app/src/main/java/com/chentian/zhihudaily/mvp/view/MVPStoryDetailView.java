package com.chentian.zhihudaily.mvp.view;

import com.chentian.zhihudaily.data.model.StoryDetail;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing detail of a story.
 *
 * @author chentian
 */
public interface MVPStoryDetailView extends MVPView {

  void showLoading();

  void hideLoading();

  void showStoryDetail(StoryDetail storyDetail);

  void loadStoryDetailFailed();

}
