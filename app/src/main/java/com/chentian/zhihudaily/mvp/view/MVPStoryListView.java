package com.chentian.zhihudaily.mvp.view;

import com.chentian.zhihudaily.data.model.StoryCollection;
import com.chentian.zhihudaily.data.model.ThemeStoryCollection;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing list of stories.
 *
 * @author chentian
 */
public interface MVPStoryListView extends MVPView {

  void loadMainPage();

  void loadTheme(long themeId);

  void showLatestStory(StoryCollection storyCollection);

  void showBeforeStory(StoryCollection storyCollection);

  void showThemeLatestStory(ThemeStoryCollection themeStoryCollection);

  void showThemeBeforeStory(ThemeStoryCollection themeStoryCollection);

}
