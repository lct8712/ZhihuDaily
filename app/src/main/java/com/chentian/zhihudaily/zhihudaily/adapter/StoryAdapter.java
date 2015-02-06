package com.chentian.zhihudaily.zhihudaily.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chentian.zhihudaily.zhihudaily.R;
import com.chentian.zhihudaily.zhihudaily.api.model.StoryAbstract;
import com.chentian.zhihudaily.zhihudaily.ui.view.ArticleHeaderView;
import com.chentian.zhihudaily.zhihudaily.ui.view.SlideTopStory;
import com.chentian.zhihudaily.zhihudaily.util.ViewUtils;
import com.koushikdutta.ion.Ion;

/**
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
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
    switch (viewType) {
      case ITEM_TYPE_HEADER_SLIDE:
        return new ViewHolderHeaderSlide(SlideTopStory.newInstance(viewGroup));
      case ITEM_TYPE_HEADER_NORMAL:
        return new ViewHolderHeaderNormal(ArticleHeaderView.newInstance(viewGroup));
      case ITEM_TYPE_ITEM:
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_story, viewGroup, false);
        return new ViewHolderItem(view);
      default:
        throw new IllegalArgumentException();
    }
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
    if (viewHolder instanceof ViewHolderHeaderSlide) {
      ViewHolderHeaderSlide viewHolderHeaderSlide = (ViewHolderHeaderSlide) viewHolder;
      viewHolderHeaderSlide.getSlideTopStory().setTopStories(topStoryList);

    } else if (viewHolder instanceof ViewHolderHeaderNormal) {
      ViewHolderHeaderNormal viewHolderHeaderNormal = (ViewHolderHeaderNormal) viewHolder;
      viewHolderHeaderNormal.getArticleHeaderView().setData(headerTitle, headerImageUrl);

    } else if (viewHolder instanceof ViewHolderItem) {
      StoryAbstract storyAbstract = storyList.get(position);

      ViewHolderItem viewHolderItem = (ViewHolderItem) viewHolder;
      viewHolderItem.getTxtTitle().setText(storyAbstract.getTitle());
      viewHolderItem.setStoryId(storyAbstract.getId());

      String imageUrl = storyAbstract.getImageUrl();
      if (!TextUtils.isEmpty(imageUrl)) {
        Ion.with(viewHolderItem.getImageIcon())
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)
                .load(imageUrl);
        viewHolderItem.getImageIcon().setVisibility(View.VISIBLE);
      } else {
        viewHolderItem.getImageIcon().setVisibility(View.GONE);
      }
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

  public static class ViewHolderHeaderNormal extends RecyclerView.ViewHolder {

    private final ArticleHeaderView articleHeaderView;

    public ViewHolderHeaderNormal(View itemView) {
      super(itemView);

      articleHeaderView = (ArticleHeaderView) itemView;
    }

    public ArticleHeaderView getArticleHeaderView() {
      return articleHeaderView;
    }
  }

  public static class ViewHolderItem extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final TextView txtTitle;
    private final ImageView imageIcon;
    private long storyId;

    public ViewHolderItem(View itemView) {
      super(itemView);

      txtTitle = (TextView) itemView.findViewById(R.id.title);
      imageIcon = (ImageView) itemView.findViewById(R.id.image);
      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
      ViewUtils.openDetailActivity(storyId, view.getContext());
    }

    public TextView getTxtTitle() {
      return txtTitle;
    }

    public ImageView getImageIcon() {
      return imageIcon;
    }

    public void setStoryId(long storyId) {
      this.storyId = storyId;
    }
  }
}
