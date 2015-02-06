package com.chentian.zhihudaily.zhihudaily.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chentian.zhihudaily.zhihudaily.R;
import com.chentian.zhihudaily.zhihudaily.ui.view.StoryListView;
import com.chentian.zhihudaily.zhihudaily.ui.view.ThemeStoryListView;

/**
 * Fragment containing list of stories
 *
 * @author chentian
 */
public class StoryListFragment extends Fragment {

  private StoryListView listViewStory;
  private ThemeStoryListView listViewThemeStory;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_story, container, false);
    listViewStory = (StoryListView) view.findViewById(R.id.list_view_story);
    listViewThemeStory = (ThemeStoryListView) view.findViewById(R.id.list_view_theme_story);

    listViewStory.loadTopStories();

    return view;
  }

  public void loadTheme(long themeId) {
    listViewStory.setVisibility(View.GONE);
    listViewThemeStory.setVisibility(View.VISIBLE);
    listViewThemeStory.loadThemeStories(themeId);
  }
}
