package com.chentian.zhihudaily.zhihudaily.adapter;

import java.util.ArrayList;
import java.util.List;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chentian.zhihudaily.zhihudaily.R;
import com.chentian.zhihudaily.data.model.StoryAbstract;
import com.chentian.zhihudaily.zhihudaily.ui.view.ArticleHeaderView;
import com.chentian.zhihudaily.zhihudaily.ui.view.SlideTopStory;
import com.chentian.zhihudaily.zhihudaily.util.ViewUtils;
import com.koushikdutta.ion.Ion;

/**
 * Adapter used in both main page list and theme list
 *
 * @author chentian
 */
public class StoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  public enum HeaderType {
    SlideHeader, NormalHeader
  }

  private static final int ITEM_TYPE_HEADER_SLIDE = 0;
  private static final int ITEM_TYPE_HEADER_NORMAL = 1;
  private static final int ITEM_TYPE_ITEM = 2;

  private Context context;

  private List<StoryAbstract> topStoryList;
  private List<StoryAbstract> storyList;
  private String headerTitle;
  private String headerImageUrl;
  private String latestDate;
  private HeaderType headerType;

  public StoryAdapter(Context context, HeaderType headerType) {
    this.context = context;
    this.headerType = headerType;
    this.storyList = new ArrayList<>();
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    switch (viewType) {
      case ITEM_TYPE_HEADER_SLIDE:
        return new ViewHolderHeaderSlide(SlideTopStory.newInstance(parent));
      case ITEM_TYPE_HEADER_NORMAL:
        return new ViewHolderHeaderNormal(ArticleHeaderView.newInstance(parent));
      case ITEM_TYPE_ITEM:
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_story, parent, false);
        return new ViewHolderItem(view);
      default:
        throw new IllegalArgumentException();
    }
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (holder instanceof ViewHolderHeaderSlide) {
      ViewHolderHeaderSlide viewHolderHeaderSlide = (ViewHolderHeaderSlide) holder;
      viewHolderHeaderSlide.getSlideTopStory().setTopStories(topStoryList);

    } else if (holder instanceof ViewHolderHeaderNormal) {
      ViewHolderHeaderNormal viewHolderHeaderNormal = (ViewHolderHeaderNormal) holder;
      viewHolderHeaderNormal.getArticleHeaderView().setData(headerTitle, headerImageUrl);

    } else if (holder instanceof ViewHolderItem) {
      ViewHolderItem viewHolderItem = (ViewHolderItem) holder;
      viewHolderItem.bindStory(storyList.get(position));
    }
  }

  @Override
  public int getItemCount() {
    return storyList.size();
  }

  @Override
  public int getItemViewType(int position) {
    if (position != 0) {
      return ITEM_TYPE_ITEM;
    }

    switch (headerType) {
      case SlideHeader:
        return ITEM_TYPE_HEADER_SLIDE;
      case NormalHeader:
        return ITEM_TYPE_HEADER_NORMAL;
      default:
        throw new IllegalArgumentException();
    }
  }

  public void setTopStories(List<StoryAbstract> topStoryList) {
    this.topStoryList = topStoryList;
    notifyItemChanged(0);
  }

  public void setNormalHeaderData(String headerTitle, String headerImageurl) {
    this.headerTitle = headerTitle;
    this.headerImageUrl = headerImageurl;
  }

  public void setStoryList(List<StoryAbstract> data) {
    this.storyList = data;
    notifyDataSetChanged();
  }

  public void appendStoryList(List<StoryAbstract> data) {
    this.storyList.addAll(data);
    notifyDataSetChanged();
  }

  public String getLatestDate() {
    return latestDate;
  }

  public void setLatestDate(String latestDate) {
    this.latestDate = latestDate;
  }

  /**
   * View holder for main page header slide
   */
  public static class ViewHolderHeaderSlide extends RecyclerView.ViewHolder {

    private final SlideTopStory slideTopStory;

    public ViewHolderHeaderSlide(View itemView) {
      super(itemView);

      slideTopStory = (SlideTopStory) itemView;
    }

    public SlideTopStory getSlideTopStory() {
      return slideTopStory;
    }
  }

  /**
   * View holder for theme page header image
   */
  private static class ViewHolderHeaderNormal extends RecyclerView.ViewHolder {

    private final ArticleHeaderView articleHeaderView;

    public ViewHolderHeaderNormal(View itemView) {
      super(itemView);

      articleHeaderView = (ArticleHeaderView) itemView;
    }

    public ArticleHeaderView getArticleHeaderView() {
      return articleHeaderView;
    }
  }

  /**
   * View holder for a story card item
   */
  private static class ViewHolderItem extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final TextView txtTitle;
    private final ImageView imageIcon;
    private StoryAbstract story;

    public ViewHolderItem(View itemView) {
      super(itemView);

      txtTitle = (TextView) itemView.findViewById(R.id.title);
      imageIcon = (ImageView) itemView.findViewById(R.id.image);
      itemView.setOnClickListener(this);
    }

    public void bindStory(StoryAbstract story) {
      this.story = story;

      txtTitle.setText(story.getTitle());

      String imageUrl = story.getImageUrl();
      if (!TextUtils.isEmpty(imageUrl)) {
        Ion.with(imageIcon)
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)
                .load(imageUrl);
        imageIcon.setVisibility(View.VISIBLE);
      } else {
        imageIcon.setVisibility(View.GONE);
      }
    }

    @Override
    public void onClick(final View view) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        int cx = (view.getLeft() + view.getRight()) / 2;
        int cy = (view.getTop() + view.getBottom()) / 2;

        int finalRadius = view.getWidth();

        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
        anim.start();
        anim.addListener(new AnimatorListenerAdapter() {
          @Override
          public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            ViewUtils.openDetailActivity(story.getId(), view.getContext());
          }
        });
      } else {
        ViewUtils.openDetailActivity(story.getId(), view.getContext());
      }
    }
  }
}
