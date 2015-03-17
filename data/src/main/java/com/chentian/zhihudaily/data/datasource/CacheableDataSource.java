package com.chentian.zhihudaily.data.datasource;

import android.content.Context;
import retrofit.Callback;

import com.chentian.zhihudaily.data.datasource.file.FileDataSource;
import com.chentian.zhihudaily.data.datasource.memory.MemoryDataSource;
import com.chentian.zhihudaily.data.datasource.rest.RestDataSource;
import com.chentian.zhihudaily.data.model.StoryDetail;

/**
 * Use Chain of Responsibility Pattern to implement a serious of data sources with priority:
 *    MemoryDataSource -> FileDataSource -> RestDataSource
 *
 * @author chentian
 */
public class CacheableDataSource {

  protected CacheableDataSource next;

  public CacheableDataSource(Context context) {
    RestDataSource restDataSource = new RestDataSource(null);
    FileDataSource fileDataSource = new FileDataSource(restDataSource, context);
    next = new MemoryDataSource(fileDataSource);
  }

  protected CacheableDataSource(CacheableDataSource next) {
    this.next = next;
  }

  public void getStoryDetail(long id, Callback<StoryDetail> callback) {
    next.getStoryDetail(id, callback);
  }
}
