package com.chentian.zhihudaily.mvp.presenter.impl;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.chentian.zhihudaily.common.provider.BusProvider;
import com.chentian.zhihudaily.data.datasource.DataSource;
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
    BusProvider.getUiBus().register(this);
  }

  @Override
  public void onPause() {
    BusProvider.getUiBus().unregister(this);
  }

  @Override
  public void loadStoryDetail(long id) {
    storyDetailView.showLoading();

    DataSource.getInstance(storyDetailView.getContext()).getStoryDetail(id, new Callback<StoryDetail>() {
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
