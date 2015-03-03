package com.chentian.zhihudaily.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.chentian.zhihudaily.R;
import com.chentian.zhihudaily.common.util.CommonUtils;
import com.chentian.zhihudaily.common.util.Const;
import com.chentian.zhihudaily.data.model.StoryCollection;
import com.chentian.zhihudaily.data.model.ThemeStoryCollection;
import com.chentian.zhihudaily.mvp.presenter.StoryListPresenter;
import com.chentian.zhihudaily.mvp.presenter.impl.StoryListPresenterImpl;
import com.chentian.zhihudaily.mvp.view.MVPStoryListView;
import com.chentian.zhihudaily.ui.adapter.StoryAdapter;

/**
 * Fragment containing list of stories
 *
 * @author chentian
 */
public class StoryListFragment extends Fragment implements MVPStoryListView {

  private static final int SCROLL_THRESHOLD = 5;

  @InjectView(R.id.list_view_story_main) RecyclerView listViewStoryMain;
  @InjectView(R.id.list_view_story_theme) RecyclerView listViewStoryTheme;
  @InjectView(R.id.swipe_refresh_story_main) SwipeRefreshLayout swipeRefreshLayoutMain;
  @InjectView(R.id.swipe_refresh_story_theme) SwipeRefreshLayout swipeRefreshLayoutTheme;

  private StoryAdapter storyAdapterMain;
  private StoryAdapter storyAdapterTheme;
  private Toolbar toolbar;
  private StoryListPresenter storyListPresenter;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_story, container, false);
    ButterKnife.inject(this, rootView);

    storyListPresenter = new StoryListPresenterImpl(this);

    bindSwipeRefreshView();
    bindMainListView();
    bindThemeListView();
    bindAdapter();

    loadMainPage();

    return rootView;
  }

  @Override
  public void onResume() {
    super.onResume();
    storyListPresenter.onResume();
  }

  @Override
  public void onPause() {
    super.onPause();
    storyListPresenter.onPause();
  }

  @Override
  public void loadMainPage() {
    swipeRefreshLayoutTheme.setVisibility(View.GONE);
    swipeRefreshLayoutMain.setVisibility(View.VISIBLE);

    storyListPresenter.loadLatestStories();
  }

  @Override
  public void loadTheme(long themeId) {
    swipeRefreshLayoutMain.setVisibility(View.GONE);
    swipeRefreshLayoutTheme.setVisibility(View.VISIBLE);

    storyListPresenter.loadThemeLatestStories(themeId);
  }

  @Override
  public void showLatestStory(StoryCollection storyCollection) {
    swipeRefreshLayoutMain.setRefreshing(false);

    storyAdapterMain.setStoryList(storyCollection.getStories(), getContext().getString(R.string.today_story));
    storyAdapterMain.setTopStories(storyCollection.getTopStories());
  }

  @Override
  public void showBeforeStory(StoryCollection storyCollection) {
    String date = CommonUtils.formatDate(storyCollection.getDate());
    storyAdapterMain.appendStoryList(storyCollection.getStories(), date);
  }

  @Override
  public void showThemeLatestStory(ThemeStoryCollection themeStoryCollection) {
    swipeRefreshLayoutTheme.setRefreshing(false);

    listViewStoryTheme.scrollToPosition(0);
    storyAdapterTheme.setStoryList(themeStoryCollection.getStories(), Const.EMPTY_STRING);
    storyAdapterTheme.setNormalHeaderData(themeStoryCollection.getDescription(), themeStoryCollection.getImage());
  }

  @Override
  public void showThemeBeforeStory(ThemeStoryCollection themeStoryCollection) {
    storyAdapterTheme.appendStoryList(themeStoryCollection.getStories(), Const.EMPTY_STRING);
  }

  @Override
  public Context getContext() {
    return getActivity();
  }

  public void setToolbar(Toolbar toolbar) {
    this.toolbar = toolbar;
  }

  private void bindSwipeRefreshView() {
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
  }

  private void bindMainListView() {
    final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    listViewStoryMain.setLayoutManager(layoutManager);
    listViewStoryMain.setOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        int itemCount = layoutManager.getItemCount();
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition >= itemCount - SCROLL_THRESHOLD) {
          storyListPresenter.loadBeforeStories();
        }
      }

      @Override
      public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        StoryAdapter storyAdapter = (StoryAdapter) listViewStoryMain.getAdapter();
        if (storyAdapter == null) {
          return;
        }

        CharSequence title;
        int position = layoutManager.findFirstVisibleItemPosition();
        if (dy > 0) {
          // Scroll down
          title = storyAdapter.getSectionTitle(position);
        } else {
          // Scroll up
          title = storyAdapter.getSectionTitleBeforePosition(position);
        }
        if (!TextUtils.isEmpty(title)) {
          toolbar.setTitle(title);
        }
      }
    });
  }

  private void bindThemeListView() {
    final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    listViewStoryTheme.setLayoutManager(layoutManager);
    listViewStoryTheme.setOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        int itemCount = layoutManager.getItemCount();
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition >= itemCount - SCROLL_THRESHOLD) {
          storyListPresenter.loadThemeBeforeStories();
        }
      }
    });
  }

  private void bindAdapter() {
    storyAdapterMain = new StoryAdapter(getActivity(), StoryAdapter.HeaderType.SlideHeader);
    listViewStoryMain.setAdapter(storyAdapterMain);

    storyAdapterTheme = new StoryAdapter(getActivity(), StoryAdapter.HeaderType.NormalHeader);
    listViewStoryTheme.setAdapter(storyAdapterTheme);
  }
}
