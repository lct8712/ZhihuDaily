package com.chentian.zhihudaily.data.datasource.memory;

import android.util.LruCache;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.Path;

import com.chentian.zhihudaily.data.datasource.DataSource;
import com.chentian.zhihudaily.data.model.StoryDetail;

/**
 * Data source which fetch data from memory
 *
 * @author chentian
 */
public class MemoryDataSource extends DataSource {

  private static final int CACHE_SIZE = 100;

  private LruCache<Long, StoryDetail> storyDetailLruCache = new LruCache<>(CACHE_SIZE);

  public MemoryDataSource(DataSource next) {
    super(next);
  }

  @Override
  public void getStoryDetail(@Path("id") final long id, final Callback<StoryDetail> callback) {
    StoryDetail storyDetail = storyDetailLruCache.get(id);
    if (storyDetail != null) {
      callback.success(storyDetail, null);
      return;
    }

    next.getStoryDetail(id, new Callback<StoryDetail>() {
      @Override
      public void success(StoryDetail storyDetail, Response response) {
        storyDetailLruCache.put(id, storyDetail);
        callback.success(storyDetail, response);
      }

      @Override
      public void failure(RetrofitError error) {
        callback.failure(error);
      }
    });
  }
}
