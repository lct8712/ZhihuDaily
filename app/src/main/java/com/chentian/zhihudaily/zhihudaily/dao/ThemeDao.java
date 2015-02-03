package com.chentian.zhihudaily.zhihudaily.dao;

import java.util.*;

import android.os.Looper;

import com.chentian.zhihudaily.zhihudaily.BuildConfig;
import com.chentian.zhihudaily.zhihudaily.api.model.Theme;
import com.chentian.zhihudaily.zhihudaily.api.model.ThemeCollection;
import com.chentian.zhihudaily.zhihudaily.util.CollectionUtils;
import com.orm.query.Select;

/**
 * @author chentian
 */
public class ThemeDao {

  /**
   * Update themes in database
   * With subscribed ids not be overwritten
   */
  public static void updateDatabase(ThemeCollection themeCollection) {
    if (BuildConfig.DEBUG && Looper.getMainLooper().getThread() == Thread.currentThread()) {
      throw new AssertionError("Run this on non UI thread");
    }

    Set<Long> subscribedThemeIds = new HashSet<>();
    for (Theme theme : CollectionUtils.notNull(Theme.listAll(Theme.class))) {
      if (theme.isSubscribed()) {
        subscribedThemeIds.add(theme.getThemeApiId());
      }
    }

    List<Theme> subscribedThemes = themeCollection.getSubscribed();
    List<Theme> otherThemes = new ArrayList<>();
    for (Theme theme : themeCollection.getOthers()) {
      if (subscribedThemeIds.contains(theme.getThemeApiId())) {
        subscribedThemes.add(theme);
      } else {
        otherThemes.add(theme);
      }
    }

    normalizeThemeId(subscribedThemes, true);
    normalizeThemeId(otherThemes, false);

    Theme.deleteAll(Theme.class);
    Theme.saveInTx(subscribedThemes);
    Theme.saveInTx(otherThemes);
  }

  public static List<Theme> listAll() {
    if (BuildConfig.DEBUG && Looper.getMainLooper().getThread() == Thread.currentThread()) {
      throw new AssertionError("Run this on non UI thread");
    }

    List<Theme> themes = Theme.listAll(Theme.class);
    if (CollectionUtils.isEmpty(themes)) {
      return Collections.emptyList();
    }

    return Select.from(Theme.class).orderBy("is_subscribed desc").list();
  }

  private static void normalizeThemeId(List<Theme> themes, boolean isSubsribed) {
    for (Theme theme : themes) {
      theme.setSubscribed(isSubsribed);
    }
  }
}
