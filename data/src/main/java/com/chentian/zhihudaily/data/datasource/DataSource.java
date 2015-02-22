package com.chentian.zhihudaily.data.datasource;

import android.content.Context;
import retrofit.Callback;
import retrofit.http.Path;

import com.chentian.zhihudaily.data.datasource.file.FileDataSource;
import com.chentian.zhihudaily.data.datasource.memory.MemoryDataSource;
import com.chentian.zhihudaily.data.datasource.rest.RestDataSource;
import com.chentian.zhihudaily.data.model.*;

/**
 * Use Chain of Responsibility Pattern to implement a serious of data sources with priority:
 *    MemoryDataSource -> FileDataSource -> RestDataSource
 *
 * @author chentian
 */
public abstract class DataSource {

  private static DataSource instance;

  protected DataSource next;

  protected DataSource(DataSource next) {
    this.next = next;
  }

  private static DataSource createChain(Context context) {
    RestDataSource restDataSource = new RestDataSource(null);
    FileDataSource fileDataSource = new FileDataSource(restDataSource, context);
    return new MemoryDataSource(fileDataSource);
  }

  public static DataSource getInstance(Context context) {
    if (instance == null) {
      instance = createChain(context);
    }
    return instance;
  }

  public void getThemeCollection(Callback<ThemeCollection> callback) {
    next.getThemeCollection(callback);
  }

  public void getStartImage(Callback<StartImage> callback) {
    next.getStartImage(callback);
  }

  public void getLatestStoryCollection(Callback<StoryCollection> callback) {
    next.getLatestStoryCollection(callback);
  }

  public void getBeforeStoryCollection(@Path("date") String date, Callback<StoryCollection> callback) {
    next.getBeforeStoryCollection(date, callback);
  }

  public void getStoryDetail(@Path("id") long id, Callback<StoryDetail> callback) {
    next.getStoryDetail(id, callback);
  }

  public void getThemeStoryCollection(@Path("id") long id, Callback<ThemeStoryCollection> callback) {
    next.getThemeStoryCollection(id, callback);
  }

  public void getStoryExtra(@Path("id") long id, Callback<StoryDetail> callback) {
    next.getStoryExtra(id, callback);
  }

  public void getShortComments(@Path("id") long id, Callback<CommentCollection> callback) {
    next.getShortComments(id, callback);
  }

  public void getLongComments(@Path("id") long id, Callback<CommentCollection> callback) {
    next.getLongComments(id, callback);
  }
}
