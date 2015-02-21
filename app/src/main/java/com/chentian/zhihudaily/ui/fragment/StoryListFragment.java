package com.chentian.zhihudaily.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.chentian.zhihudaily.data.model.StoryAbstract;
import com.chentian.zhihudaily.data.model.StoryCollection;
import com.chentian.zhihudaily.data.model.ThemeStoryCollection;
import com.chentian.zhihudaily.mvp.presenter.StoryListPresenter;
import com.chentian.zhihudaily.mvp.presenter.impl.StoryListPresenterImpl;
import com.chentian.zhihudaily.mvp.view.MVPStoryListView;
import com.chentian.zhihudaily.ui.adapter.StoryAdapter;
import com.chentian.zhihudaily.zhihudaily.R;

/**
 * Fragment containing list of stories
 *
 * @author chentian
 */
public class StoryListFragment extends Fragment implements MVPStoryListView {

  @InjectView(R.id.list_view_story_main) RecyclerView listViewStoryMain;
  @InjectView(R.id.list_view_story_theme) RecyclerView listViewStoryTheme;
  @InjectView(R.id.swipe_refresh_story_main) SwipeRefreshLayout swipeRefreshLayoutMain;
  @InjectView(R.id.swipe_refresh_story_theme) SwipeRefreshLayout swipeRefreshLayoutTheme;

  private StoryAdapter storyAdapterMain;
  private StoryAdapter storyAdapterTheme;
  private StoryListPresenter storyListPresenter;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_story, container, false);
    ButterKnife.inject(this, rootView);

    storyListPresenter = new StoryListPresenterImpl(this);
    buildUI();
    loadMainPage();

    return rootView;
  }

  public void loadMainPage() {
    swipeRefreshLayoutTheme.setVisibility(View.GONE);
    swipeRefreshLayoutMain.setVisibility(View.VISIBLE);

    storyListPresenter.loadTopStories();
  }

  public void loadTheme(long themeId) {
    swipeRefreshLayoutMain.setVisibility(View.GONE);
    swipeRefreshLayoutTheme.setVisibility(View.VISIBLE);

    storyListPresenter.loadThemeStories(themeId);
  }

  @Override
  public void showMainStory(StoryCollection storyCollection) {
    swipeRefreshLayoutMain.setRefreshing(false);

    storyAdapterMain.setStoryList(storyCollection.getStories());
    storyAdapterMain.setTopStories(storyCollection.getTop_stories());
  }

  @Override
  public void showMoreStory(StoryCollection storyCollection) {
    storyAdapterMain.appendStoryList(storyCollection.getStories());
  }

  @Override
  public void showThemeStory(ThemeStoryCollection themeStoryCollection) {
    swipeRefreshLayoutTheme.setRefreshing(false);

    listViewStoryTheme.scrollToPosition(0);
    storyAdapterTheme.setStoryList(themeStoryCollection.getStories());
    storyAdapterTheme.setNormalHeaderData(themeStoryCollection.getDescription(), themeStoryCollection.getImage());
  }

  private void buildUI() {
    // Swipe refresh view
    swipeRefreshLayoutMain.setColorSchemeResources(R.color.color_primary);
    swipeRefreshLayoutTheme.setColorSchemeResources(R.color.color_primary);

    swipeRefreshLayoutMain.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        storyListPresenter.onRefreshMainStories();
      }
    });

    swipeRefreshLayoutTheme.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        storyListPresenter.onRefreshThemeStories();
      }
    });

    // List view
    final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    listViewStoryMain.setLayoutManager(layoutManager);
    listViewStoryMain.setOnScrollListener(new RecyclerView.OnScrollListener() {
      private static final int THRESHOLD = 5;

      @Override
      public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        int itemCount = layoutManager.getItemCount();
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition >= itemCount - THRESHOLD) {
          storyListPresenter.loadMoreStories();
        }
      }
    });
    listViewStoryTheme.setLayoutManager(new LinearLayoutManager(getActivity()));

    // Adapter
    StoryAdapter.OnCardItemClickListener onCardItemClickListener = new StoryAdapter.OnCardItemClickListener() {
      @Override
      public void onStoryCardItemClick(View view, StoryAbstract story) {
        storyListPresenter.onStoryCardItemClick(view, story);
      }
    };

    storyAdapterMain = new StoryAdapter(getActivity(), StoryAdapter.HeaderType.SlideHeader);
    storyAdapterMain.setOnCardItemClickListener(onCardItemClickListener);
    listViewStoryMain.setAdapter(storyAdapterMain);

    storyAdapterTheme = new StoryAdapter(getActivity(), StoryAdapter.HeaderType.NormalHeader);
    storyAdapterTheme.setOnCardItemClickListener(onCardItemClickListener);
    listViewStoryTheme.setAdapter(storyAdapterTheme);
  }
}