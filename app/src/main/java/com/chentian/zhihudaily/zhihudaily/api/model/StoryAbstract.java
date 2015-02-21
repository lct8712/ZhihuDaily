package com.chentian.zhihudaily.zhihudaily.api.model;

import java.util.List;

import android.text.TextUtils;

import com.chentian.zhihudaily.zhihudaily.util.CollectionUtils;
import com.chentian.zhihudaily.zhihudaily.util.Const;
import com.google.gson.annotations.SerializedName;

/**
 * Story model displayed in list page, such as:
 *   http://news-at.zhihu.com/api/4/news/latest
 *
 * @author chentian
 */
public class StoryAbstract {

  private long id;

  private String title;

  @SerializedName("ga_prefix")
  private String gaPrefix;

  private List<String> images;

  private String image;

  private boolean multipic;

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

  public boolean isMultipic() {
    return multipic;
  }

  public void setMultipic(boolean multipic) {
    this.multipic = multipic;
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
    if (!TextUtils.isEmpty(image)) {
      return image;
    }

    if (CollectionUtils.isEmpty(images) || images.size() < 1) {
      return Const.EMPTY_STRING;
    }
    return images.get(0);
  }
}
