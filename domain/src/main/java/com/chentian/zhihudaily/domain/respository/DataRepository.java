package com.chentian.zhihudaily.domain.respository;

import retrofit.Callback;

import com.chentian.zhihudaily.data.model.*;

/**
 * Repository for reading and writing data.
 *
 * Most of the methods are just a wrapper of DataSource in data layer
 *
 * Methods starts with "sync" may return a result more than once,
 * so they post result to BUS instead of using callback.
 *
 * @author chentian
 */
public interface DataRepository {

  /**
   * Get story detail from cacheable data source
   */
  void getStoryDetail(long id, Callback<StoryDetail> callback);

  void getStartImage(Callback<StartImage> callback);

  void getStoryExtra(long id, Callback<StoryDetail> callback);

  void getShortComments(long id, Callback<CommentCollection> callback);

  void getLongComments(long id, Callback<CommentCollection> callback);

  /**
   * Get latest {@link StoryCollection} from rest data source, then fill the read info from database
   * If rest data source failed, try file data source
   */
  void syncLatestStoryCollection();

  /**
   * Get before {@link StoryCollection} from rest data source, then fill the read info from database
   * If rest data source failed, try file data source
   */
  void syncBeforeStoryCollection(String date);

  /**
   * Get theme latest {@link ThemeStoryCollection} from rest data source, then fill the read info from database
   * If rest data source failed, try file data source
   */
  void syncThemeLatestStoryCollection(long themeId);

  /**
   * Get theme before {@link ThemeStoryCollection} from rest data source, then fill the read info from database
   * If rest data source failed, try file data source
   */
  void syncThemeBeforeStoryCollection(long themeId, long storyId);

  /**
   * Mark specify story as read and save it into database
   */
  void markStoryAsRead(StoryAbstract storyAbstract);

  /**
   * Get {@link ThemeCollection } from data base at first, then get it from rest data source
   * The results are posted on ui data bus
   */
  void syncThemeCollection();

  /**
   * Save specify theme into database
   */
  void saveTheme(Theme theme);

  /**
   * Start the offline process
   */
  void startOffline();
}
