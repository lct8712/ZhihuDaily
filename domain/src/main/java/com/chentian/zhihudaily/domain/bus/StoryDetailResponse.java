package com.chentian.zhihudaily.domain.bus;

import lombok.Data;

import com.chentian.zhihudaily.data.model.StoryDetail;

/**
 * Story detail entity transfer on bus
 *
 * @author chentian
 */
@Data
@SuppressWarnings("unused")
public class StoryDetailResponse {

  private StoryDetail storyDetail;

  private boolean isSuccess;

  public StoryDetailResponse(StoryDetail storyDetail, boolean isSuccess) {
    this.storyDetail = storyDetail;
    this.isSuccess = isSuccess;
  }
}
