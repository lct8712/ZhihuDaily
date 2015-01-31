package com.chentian.zhihudaily.zhihudaily.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.chentian.zhihudaily.zhihudaily.R;
import com.chentian.zhihudaily.zhihudaily.api.RestClient;
import com.chentian.zhihudaily.zhihudaily.api.model.StoryDetail;
import com.chentian.zhihudaily.zhihudaily.api.service.NewsService;
import com.chentian.zhihudaily.zhihudaily.ui.activity.DetailActivity;
import com.chentian.zhihudaily.zhihudaily.ui.view.ArticleHeaderView;
import com.chentian.zhihudaily.zhihudaily.util.WebUtil;

/**
 * A placeholder fragment containing a simple view.
 */
public class StoryDetailFragment extends Fragment {

  private WebView webViewContent;
  private ArticleHeaderView articleHeader;
  private ScrollView scrollViewContent;

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
        // TODO: handle this
      }
    });
  }

  private void bindUI(StoryDetail storyDetail) {
    Context context = getActivity();
    if (context == null) {
      return;
    }

    String data = WebUtil.BuildHtmlWithCss(storyDetail.getBody(), storyDetail.getCss());
    webViewContent.loadDataWithBaseURL(WebUtil.ASSERT_DIR, data, WebUtil.MIME_HTML_TYPE, WebUtil.DEFAULT_CHARSET, null);

    articleHeader.setData(storyDetail.getTitle(), storyDetail.getImage());

    scrollViewContent.fullScroll(HorizontalScrollView.FOCUS_DOWN);
    scrollViewContent.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
      @Override
      public void onScrollChanged() {
        changeHeaderPosition();
      }
    });
  }

  private void changeHeaderPosition() {
    int scrollY = scrollViewContent.getScrollY();

    // Set height
    float articleHeight = getResources().getDimensionPixelSize(R.dimen.slide_image_height);
    if (scrollY < 0) {
      articleHeight -= scrollY;
    }
    articleHeader.getLayoutParams().height = (int) articleHeight;

    // Set scroll
    int headerScrollY = (scrollY > 0) ? (scrollY / 2) : 0;
    articleHeader.setScrollY(headerScrollY);
    articleHeader.requestLayout();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

    webViewContent = (WebView) rootView.findViewById(R.id.web_view_article);
    articleHeader = (ArticleHeaderView) rootView.findViewById(R.id.article_header);
    scrollViewContent = (ScrollView) rootView.findViewById(R.id.scroll_view_content);

    return rootView;
  }
}
