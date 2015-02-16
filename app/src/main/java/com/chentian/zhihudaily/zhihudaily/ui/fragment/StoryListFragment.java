package com.chentian.zhihudaily.zhihudaily.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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

  private long currentThemeId;
  private StoryListView listViewStoryMain;
  private ThemeStoryListView listViewStoryTheme;
  private SwipeRefreshLayout swipeRefreshLayoutMain;
  private SwipeRefreshLayout swipeRefreshLayoutTheme;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_story, container, false);

    listViewStoryMain = (StoryListView) view.findViewById(R.id.list_view_story_main);
    listViewStoryTheme = (ThemeStoryListView) view.findViewById(R.id.list_view_story_theme);
    swipeRefreshLayoutMain = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_story_main);
    swipeRefreshLayoutTheme = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_story_theme);

    buildUI();

    loadMainPage();

    return view;
  }

  public void loadMainPage() {
    currentThemeId = -1;
    swipeRefreshLayoutTheme.setVisibility(View.GONE);
    swipeRefreshLayoutMain.setVisibility(View.VISIBLE);
    // TODO: use event bus, and handle the failure
    listViewStoryMain.loadTopStories(new Runnable() {
      @Override
      public void run() {
        swipeRefreshLayoutMain.setRefreshing(false);
      }
    });
  }

  public void loadTheme(long themeId) {
    currentThemeId = themeId;
    swipeRefreshLayoutMain.setVisibility(View.GONE);
    swipeRefreshLayoutTheme.setVisibility(View.VISIBLE);
    listViewStoryTheme.loadThemeStories(themeId, new Runnable() {
      @Override
      public void run() {
        swipeRefreshLayoutTheme.setRefreshing(false);
      }
    });
  }

  private void buildUI() {
    swipeRefreshLayoutMain.setColorSchemeResources(R.color.color_primary);
    swipeRefreshLayoutTheme.setColorSchemeResources(R.color.color_primary);

    swipeRefreshLayoutMain.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        loadMainPage();
      }
    });

    swipeRefreshLayoutTheme.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        loadTheme(currentThemeId);
      }
    });
  }
}
