package com.chentian.zhihudaily.mvp.presenter.impl;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.chentian.zhihudaily.DailyApplication;
import com.chentian.zhihudaily.data.model.StoryDetail;
import com.chentian.zhihudaily.mvp.presenter.StoryDetailPresenter;
import com.chentian.zhihudaily.mvp.view.MVPStoryDetailView;

/**
 * @author chentian
 */
public class StoryDetailPresenterImpl implements StoryDetailPresenter {

  private MVPStoryDetailView storyDetailView;

  public StoryDetailPresenterImpl(MVPStoryDetailView storyDetailView) {
    this.storyDetailView = storyDetailView;
  }

  @Override
  public void onResume() {
    DailyApplication.getInstance().getUiBus().register(this);
  }

  @Override
  public void onPause() {
    DailyApplication.getInstance().getUiBus().unregister(this);
  }

  @Override
  public void loadStoryDetail(long id) {
    storyDetailView.showLoading();

    DailyApplication.getInstance().getDataRepository().getStoryDetail(id, new Callback<StoryDetail>() {
      @Override
      public void success(StoryDetail storyDetail, Response response) {
        storyDetailView.hideLoading();
        storyDetailView.showStoryDetail(storyDetail);
      }

      @Override
      public void failure(RetrofitError error) {
        storyDetailView.hideLoading();
        storyDetailView.loadStoryDetailFailed();
      }
    });
  }
}
