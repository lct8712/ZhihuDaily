package com.chentian.zhihudaily.data.model;

import java.util.List;

import lombok.Data;

import com.chentian.zhihudaily.common.util.CollectionUtils;
import com.chentian.zhihudaily.common.util.Const;
import com.google.gson.annotations.SerializedName;

/**
 * Story model displayed in list page, such as:
 *   http://news-at.zhihu.com/api/4/news/latest
 *
 * @author chentian
 */
@Data
@SuppressWarnings("unused")
public class StoryAbstract {

  private long id;

  private String title;

  @SerializedName("ga_prefix")
  private String gaPrefix;

  private List<String> images;

  private String image;

  @SerializedName("multipic")
  private boolean multiPic;

  @SerializedName("share_url")
  private String shareUrl;

  private int type;

  /**
   * Is current story has been read
   * Store in database table using {@link com.chentian.zhihudaily.data.model.ReadStory}
   */
  private boolean isRead;

  public String getImageUrl() {
    if (image != null && !image.isEmpty()) {
      return image;
    }

    if (CollectionUtils.isEmpty(images) || images.size() < 1) {
      return Const.EMPTY_STRING;
    }
    return images.get(0);
  }
}
