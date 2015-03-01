package com.chentian.zhihudaily.data.datasource.file;

import android.content.Context;
import android.os.AsyncTask;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.chentian.zhihudaily.common.util.ThreadUtils;
import com.chentian.zhihudaily.data.datasource.DataSource;
import com.chentian.zhihudaily.data.model.StoryDetail;
import com.google.gson.Gson;

/**
 * Data source which fetch data from disk file
 *
 * @author chentian
 */
public class FileDataSource extends DataSource {

  private FileCacheHelper storyCache;
  private Gson gson;

  public FileDataSource(DataSource next, Context context) {
    super(next);

    storyCache = new FileCacheHelper(context, FileCacheHelper.DataType.Story);
    gson = new Gson();
  }

  @Override
  public void getStoryDetail(final long id, final Callback<StoryDetail> callback) {
    new AsyncTask<Void, Void, Void>() {
      @Override
      protected Void doInBackground(Void... params) {
        getStoryDetailBackground(id, callback);
        return null;
      }
    }.execute();
  }

  private void getStoryDetailBackground(long id, final Callback<StoryDetail> callback) {
    final String key = String.valueOf(id);
    if (storyCache.exists(key)) {
      String content = storyCache.readContent(key);
      final StoryDetail storyDetail = gson.fromJson(content, StoryDetail.class);
      if (storyDetail != null) {
        ThreadUtils.runOnMainThread(new Runnable() {
          @Override
          public void run() {
            callback.success(storyDetail, null);
          }
        });
        return;
      }
    }

    next.getStoryDetail(id, new Callback<StoryDetail>() {
      @Override
      public void success(StoryDetail storyDetail, Response response) {
        String content = gson.toJson(storyDetail);
        storyCache.writeContent(key, content);
        callback.success(storyDetail, response);
      }

      @Override
      public void failure(RetrofitError error) {
        callback.failure(error);
      }
    });
  }
}
