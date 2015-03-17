package com.chentian.zhihudaily.data.datasource.file;

import java.lang.reflect.Type;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.chentian.zhihudaily.common.util.ThreadUtils;
import com.chentian.zhihudaily.data.datasource.CacheableDataSource;
import com.chentian.zhihudaily.data.model.StoryCollection;
import com.chentian.zhihudaily.data.model.StoryDetail;
import com.chentian.zhihudaily.data.model.ThemeStoryCollection;
import com.google.gson.Gson;

/**
 * Data source which fetch data from disk file
 * If data is not found in disk, fetch it from next DataSource, then save data to disk
 *
 * @author chentian
 */
public class FileDataSource extends CacheableDataSource {

  private static final String THREAD_NAME = "file_data_source_background";
  private static final String SEPARATE = "-";

  private Handler handler;
  private FileCache cache;
  private Gson gson;

  public FileDataSource(Context context) {
    this(null, context);
  }

  public FileDataSource(CacheableDataSource next, Context context) {
    super(next);

    HandlerThread handlerThread = new HandlerThread(THREAD_NAME);
    handlerThread.start();
    handler = new Handler(handlerThread.getLooper());

    cache = new FileCache(context.getCacheDir().getPath());
    gson = new Gson();
  }

  @Override
  public void getStoryDetail(final long id, final Callback<StoryDetail> callback) {
    handler.post(new Runnable() {
      @Override
      public void run() {
        final String key = String.valueOf(id);
        if (tryToReadFromCache(FileCache.DataType.STORY_DETAIL, key, callback, false, StoryDetail.class)) {
          return;
        }

        next.getStoryDetail(id, new Callback<StoryDetail>() {
          @Override
          public void success(StoryDetail t, Response response) {
            String content = gson.toJson(t);
            cache.writeContent(FileCache.DataType.STORY_DETAIL, key, content);
            callback.success(t, response);
          }

          @Override
          public void failure(RetrofitError error) {
            callback.failure(error);
          }
        });
      }
    });
  }

  public void getLatestStoryCollection(final Callback<StoryCollection> callback) {
    handler.post(new Runnable() {
      @Override
      public void run() {
        final String key = StoryCollection.class.getName();
        tryToReadFromCache(FileCache.DataType.STORY_LIST, key, callback, true, StoryCollection.class);
      }
    });
  }

  public void getBeforeStoryCollection(final String date, final Callback<StoryCollection> callback) {
    handler.post(new Runnable() {
      @Override
      public void run() {
        final String key = date + SEPARATE + StoryCollection.class.getName();
        tryToReadFromCache(FileCache.DataType.STORY_LIST, key, callback, true, StoryCollection.class);
      }
    });
  }

  public void getThemeLatestStoryCollection(final long id, final Callback<ThemeStoryCollection> callback) {
    handler.post(new Runnable() {
      @Override
      public void run() {
        final String key = String.valueOf(id) + SEPARATE + ThemeStoryCollection.class.getName();
        tryToReadFromCache(FileCache.DataType.STORY_LIST, key, callback, true, ThemeStoryCollection.class);
      }
    });
  }

  public void getThemeBeforeStoryCollection(final long themeId, final long storyId,
                                            final Callback<ThemeStoryCollection> callback) {
    handler.post(new Runnable() {
      @Override
      public void run() {
        final String key = String.valueOf(themeId) + SEPARATE + storyId +
                SEPARATE + ThemeStoryCollection.class.getName();
        tryToReadFromCache(FileCache.DataType.STORY_LIST, key, callback, true, ThemeStoryCollection.class);
      }
    });
  }

  public void saveLatestStoryCollection(final StoryCollection storyCollection) {
    String key = StoryCollection.class.getName();
    saveToFile(key, storyCollection);
  }

  public void saveLatestStoryCollection(String date, StoryCollection storyCollection) {
    String key = date + SEPARATE + StoryCollection.class.getName();
    saveToFile(key, storyCollection);
  }

  public void saveThemeLatestStoryCollection(long id, ThemeStoryCollection storyCollection) {
    String key = String.valueOf(id) + SEPARATE + ThemeStoryCollection.class.getName();
    saveToFile(key, storyCollection);
  }

  public void saveThemeBeforeStoryCollection(long themeId, long storyId, ThemeStoryCollection storyCollection) {
    String key = String.valueOf(themeId) + SEPARATE + storyId + SEPARATE + ThemeStoryCollection.class.getName();
    saveToFile(key, storyCollection);
  }

  private void saveToFile(final String key, final Object object) {
    handler.post(new Runnable() {
      @Override
      public void run() {
        String content = gson.toJson(object);
        cache.writeContent(FileCache.DataType.STORY_LIST, key, content);
      }
    });
  }

  /**
   * Try to read data from local file cache, return true if read success
   */
  private <T> boolean tryToReadFromCache(FileCache.DataType dataType, String key,
                                         final Callback<T> callback, boolean callbackOnFail, Type type) {
    if (!cache.exists(dataType, key)) {
      if (callbackOnFail) {
        ThreadUtils.runOnMainThread(new Runnable() {
          @Override
          public void run() {
            callback.failure(null);
          }
        });
      }
      return false;
    }

    String content = cache.readContent(dataType, key);
    final T data = gson.fromJson(content, type);
    if (data != null) {
      ThreadUtils.runOnMainThread(new Runnable() {
        @Override
        public void run() {
          callback.success(data, null);
        }
      });
    }
    return true;
  }
}
