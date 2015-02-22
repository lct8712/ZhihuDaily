package com.chentian.zhihudaily.data.model;

import java.util.List;

import com.chentian.zhihudaily.common.util.CollectionUtils;
import com.chentian.zhihudaily.common.util.Const;
import com.google.gson.annotations.SerializedName;

/**
 * Story model displayed in list page, such as:
 *   http://news-at.zhihu.com/api/4/news/latest
 *
 * @author chentian
 */
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

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getGaPrefix() {
    return gaPrefix;
  }

  public void setGaPrefix(String gaPrefix) {
    this.gaPrefix = gaPrefix;
  }

  public List<String> getImages() {
    return images;
  }

  public void setImages(List<String> images) {
    this.images = images;
  }

  public boolean isMultiPic() {
    return multiPic;
  }

  public void setMultiPic(boolean multiPic) {
    this.multiPic = multiPic;
  }

  public String getShareUrl() {
    return shareUrl;
  }

  public void setShareUrl(String shareUrl) {
    this.shareUrl = shareUrl;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

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
