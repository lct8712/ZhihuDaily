package com.chentian.zhihudaily.domain.respository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import android.util.Log;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.chentian.zhihudaily.common.util.CollectionUtils;
import com.chentian.zhihudaily.common.util.Const;
import com.chentian.zhihudaily.data.datasource.rest.RestDataSource;
import com.chentian.zhihudaily.data.model.*;
import com.chentian.zhihudaily.domain.bus.OfflineProgress;
import com.squareup.otto.Bus;

/**
 * Cache data to local file to support offline read
 * Post progress on bus with {@link OfflineProgress}
 *    0 -  10: getting list
 *   11 - 100: getting detail
 *
 * @author chentian
 */
public class OfflineHelper {

  private List<StoryAbstract> stories;
  private int succeedNumber;

  public OfflineHelper() {
    stories = new ArrayList<>();
  }

  public void run(final RestDataSource dataRepository, final Bus bus) {
    dataRepository.getLatestStoryCollection(new Callback<StoryCollection>() {
      @Override
      public void success(StoryCollection storyCollection, Response response) {
        stories.addAll(storyCollection.getStories());
        bus.post(new OfflineProgress(1));

        getThemes(dataRepository, bus);

        Log.d(Const.LogTag.OFFLINE, "Offline get latest story collection success.");
      }

      @Override
      public void failure(RetrofitError error) {
        bus.post(new OfflineProgress(OfflineProgress.Type.FAILED));
        Log.d(Const.LogTag.OFFLINE, "Offline get latest story collection failure: " + error + ", " + error.getUrl());
      }
    });
  }

  private void getThemes(final RestDataSource dataRepository, final Bus bus) {
    dataRepository.getThemeCollection(new Callback<ThemeCollection>() {
      @Override
      public void success(ThemeCollection themeCollection, Response response) {
        Queue<Theme> themes = new LinkedList<>(themeCollection.getAll());
        getThemeStoryList(dataRepository, bus, themes, 2);

        Log.d(Const.LogTag.OFFLINE, "Offline get theme collection success.");
      }

      @Override
      public void failure(RetrofitError error) {
        bus.post(new OfflineProgress(OfflineProgress.Type.FAILED));
        Log.d(Const.LogTag.OFFLINE, "Offline get theme collection failure: " + error + ", " + error.getUrl());
      }
    });
  }

  private void getThemeStoryList(final RestDataSource dataRepository, final Bus bus, final Queue<Theme> themes,
                                 final int currentPercent) {
    if (CollectionUtils.isEmpty(themes)) {
      getStoryDetail(dataRepository, bus, currentPercent);
      bus.post(new OfflineProgress(currentPercent));
      Log.d(Const.LogTag.OFFLINE, "Offline get theme story list collection success, percent: " + currentPercent);
      return;
    }

    dataRepository.getThemeLatestStoryCollection(themes.poll().getThemeApiId(), new Callback<ThemeStoryCollection>() {
      @Override
      public void success(ThemeStoryCollection themeStoryCollection, Response response) {
        stories.addAll(themeStoryCollection.getStories());
        bus.post(new OfflineProgress(currentPercent));

        getThemeStoryList(dataRepository, bus, themes, currentPercent + 1);
      }

      @Override
      public void failure(RetrofitError error) {
        bus.post(new OfflineProgress(OfflineProgress.Type.FAILED));
        Log.d(Const.LogTag.OFFLINE, "Offline get theme latest collection failure: " + error + ", " + error.getUrl());
      }
    });
  }

  private void getStoryDetail(RestDataSource dataRepository, final Bus bus, final int currentPercent) {
    succeedNumber = 0;
    for (final StoryAbstract story : stories) {
      dataRepository.getStoryDetail(story.getId(), new Callback<StoryDetail>() {
        @Override
        public void success(StoryDetail storyDetail, Response response) {
          int percent = currentPercent + (100 - currentPercent) * succeedNumber / stories.size();
          bus.post(new OfflineProgress(percent));

          if (++succeedNumber == stories.size()) {
            bus.post(new OfflineProgress(OfflineProgress.Type.SUCCESS));
            Log.d(Const.LogTag.OFFLINE, "Offline get story detail success.");
          }
        }

        @Override
        public void failure(RetrofitError error) {
          bus.post(new OfflineProgress(OfflineProgress.Type.FAILED));
          Log.d(Const.LogTag.OFFLINE, "Offline get story detail failure:~ " + error + ", " + error.getUrl());
        }
      });
    }


  }
}
