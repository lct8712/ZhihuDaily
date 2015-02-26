package com.chentian.zhihudaily.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.widget.*;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.chentian.zhihudaily.R;
import com.chentian.zhihudaily.common.util.WebUtils;
import com.chentian.zhihudaily.data.model.StoryDetail;
import com.chentian.zhihudaily.mvp.presenter.StoryDetailPresenter;
import com.chentian.zhihudaily.mvp.presenter.impl.StoryDetailPresenterImpl;
import com.chentian.zhihudaily.mvp.view.MVPStoryDetailView;
import com.chentian.zhihudaily.ui.activity.DetailActivity;
import com.chentian.zhihudaily.ui.view.ArticleHeaderView;
import com.chentian.zhihudaily.util.ScrollPullDownHelper;

/**
 * Fragment containing detail of a story
 *
 * @author chentian
 */
public class StoryDetailFragment extends Fragment implements MVPStoryDetailView {

  @InjectView(R.id.web_view_article) WebView webViewContent;
  @InjectView(R.id.scroll_view_content) ScrollView scrollViewContent;
  @InjectView(R.id.progress_loading) ProgressBar progressBarLoading;

  private ArticleHeaderView articleHeader;
  private Toolbar toolbar;
  private ScrollPullDownHelper scrollPullDownHelper;
  private StoryDetailPresenter storyDetailPresenter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
    ButterKnife.inject(this, rootView);

    articleHeader = ArticleHeaderView.newInstance(container);
    RelativeLayout articleHeaderContainer = (RelativeLayout) rootView.findViewById(R.id.article_header_container);
    articleHeaderContainer.addView(articleHeader);
    scrollPullDownHelper = new ScrollPullDownHelper();

    storyDetailPresenter = new StoryDetailPresenterImpl(this);
    storyDetailPresenter.loadStoryDetail(getArguments().getLong(DetailActivity.EXTRA_ID));

    return rootView;
  }

  @Override
  public void onResume() {
    super.onResume();
    storyDetailPresenter.onResume();
  }

  @Override
  public void onPause() {
    super.onPause();
    storyDetailPresenter.onPause();
  }

  @Override
  public void showLoading() {
    progressBarLoading.setVisibility(View.VISIBLE);
  }

  @Override
  public void hideLoading() {
    progressBarLoading.setVisibility(View.GONE);
  }

  @Override
  public void showStoryDetail(StoryDetail storyDetail) {
    bindUI(storyDetail);
  }

  @Override
  public void loadStoryDetailFailed() {
    FragmentActivity activity = getActivity();
    if (activity != null) {
      Toast.makeText(activity, getString(R.string.load_failed), Toast.LENGTH_SHORT).show();
      activity.finish();
    }
  }

  @Override
  public Context getContext() {
    return getActivity();
  }

  public void setToolbar(Toolbar toolbar) {
    this.toolbar = toolbar;
  }

  private void bindUI(StoryDetail storyDetail) {
    Context context = getActivity();
    if (context == null) {
      return;
    }

    String data = WebUtils.BuildHtmlWithCss(storyDetail.getBody(), storyDetail.getCss());
    webViewContent.loadDataWithBaseURL(WebUtils.ASSERT_DIR,
            data, WebUtils.MIME_HTML_TYPE, WebUtils.DEFAULT_CHARSET, null);

    articleHeader.setData(storyDetail.getTitle(), storyDetail.getImage());

    scrollViewContent.fullScroll(HorizontalScrollView.FOCUS_DOWN);
    scrollViewContent.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
      @Override
      public void onScrollChanged() {
        changeHeaderPosition();
        changeToolbarAlpha();
      }
    });
  }

  private void changeHeaderPosition() {
    int scrollY = scrollViewContent.getScrollY();

    // Set height
    float articleHeight = getResources().getDimensionPixelSize(R.dimen.slide_image_height);
    if (scrollY < 0) {
      // Pull down, zoom in the image
      articleHeight += Math.abs(scrollY);
    }
    articleHeader.getLayoutParams().height = (int) articleHeight;

    // Set scroll
    int headerScrollY = (scrollY > 0) ? (scrollY / 2) : 0;
    articleHeader.setScrollY(headerScrollY);
    articleHeader.requestLayout();
  }

  private void changeToolbarAlpha() {
    if (toolbar == null) {
      return;
    }

    int scrollY = scrollViewContent.getScrollY();
    if (scrollY < 0) {
      toolbar.getBackground().setAlpha(0);
      return;
    }

    float articleHeight = getResources().getDimensionPixelSize(R.dimen.slide_image_height);
    float contentHeight = articleHeight - toolbar.getHeight();
    float ratio = Math.min(scrollY / contentHeight, 1.0f);
    toolbar.getBackground().setAlpha((int) (ratio * 0xFF));

    if (scrollY <= contentHeight) {
      toolbar.setY(0f);
      return;
    }

    // Don't show toolbar if user has pulled up the whole article
    if (scrollY + scrollViewContent.getHeight() > webViewContent.getMeasuredHeight() + articleHeight) {
      return;
    }

    // Show the toolbar if user is pulling down
    boolean isPullingDown = scrollPullDownHelper.onScrollChanged(scrollY);
    float toolBarPositionY = isPullingDown ? 0 : (contentHeight - scrollY);
    toolbar.setY(toolBarPositionY);
  }
}
