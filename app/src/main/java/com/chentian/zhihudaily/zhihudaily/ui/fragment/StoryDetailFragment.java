package com.chentian.zhihudaily.zhihudaily.ui.fragment;

import java.util.LinkedList;
import java.util.Queue;

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
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.chentian.zhihudaily.zhihudaily.R;
import com.chentian.zhihudaily.zhihudaily.api.RestClient;
import com.chentian.zhihudaily.zhihudaily.api.model.StoryDetail;
import com.chentian.zhihudaily.zhihudaily.api.service.NewsService;
import com.chentian.zhihudaily.zhihudaily.ui.activity.DetailActivity;
import com.chentian.zhihudaily.zhihudaily.ui.view.ArticleHeaderView;
import com.chentian.zhihudaily.zhihudaily.util.WebUtils;

/**
 * Fragment containing detail of a story
 *
 * @author chentian
 */
public class StoryDetailFragment extends Fragment {

  private WebView webViewContent;
  private ArticleHeaderView articleHeader;
  private ScrollView scrollViewContent;
  private Toolbar toolbar;

  private int lastScrollY = 0;
  private Queue<Boolean> latestPullingDown;
  private static final int PULLING_DOWN_TIME_MAX = 7;
  private static final int PULLING_DOWN_TIME_THRESHOLD = 5;

  public StoryDetailFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Long id = getArguments().getLong(DetailActivity.EXTRA_ID);
    NewsService newsService = RestClient.getInstance().getNewsService();
    newsService.getStoryDetail(id, new Callback<StoryDetail>() {
      @Override
      public void success(StoryDetail storyDetail, Response response) {
        bindUI(storyDetail);
      }

      @Override
      public void failure(RetrofitError error) {
        FragmentActivity activity = getActivity();
        if (activity != null) {
          Toast.makeText(activity, getString(R.string.load_failed), Toast.LENGTH_SHORT).show();
          activity.finish();
        }
      }
    });
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

    webViewContent = (WebView) rootView.findViewById(R.id.web_view_article);
    articleHeader = ArticleHeaderView.newInstance(container);
    scrollViewContent = (ScrollView) rootView.findViewById(R.id.scroll_view_content);

    RelativeLayout articleHeaderContainer = (RelativeLayout) rootView.findViewById(R.id.article_header_container);
    articleHeaderContainer.addView(articleHeader);

    latestPullingDown = new LinkedList<>();

    return rootView;
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
    webViewContent.loadDataWithBaseURL(WebUtils.ASSERT_DIR, data, WebUtils.MIME_HTML_TYPE, WebUtils.DEFAULT_CHARSET, null);

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

    // Don't show toolbar if use has pulled up the whole article
    if (scrollY + scrollViewContent.getHeight() > webViewContent.getMeasuredHeight() + articleHeight) {
      return;
    }

    // We detect several latest moves to ensure a smooth showing-hiding toolbar effect
    // Show the toolbar if user is pulling down
    boolean isPullingDown = scrollY < lastScrollY;
    latestPullingDown.offer(isPullingDown);
    if (latestPullingDown.size() > PULLING_DOWN_TIME_MAX) {
      latestPullingDown.poll();
    }

    float toolBarPositionY = (getPullingDownTime() < PULLING_DOWN_TIME_THRESHOLD) ? (contentHeight - scrollY) : 0;
    toolbar.setY(toolBarPositionY);

    lastScrollY = scrollY;
  }

  public int getPullingDownTime() {
    int result = 0;
    for (Boolean isPullingDown : latestPullingDown) {
      if (isPullingDown) {
        result++;
      }
    }
    return result;
  }
}
