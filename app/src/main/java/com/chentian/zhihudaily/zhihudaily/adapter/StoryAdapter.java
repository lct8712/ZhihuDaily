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
import com.chentian.zhihudaily.zhihudaily.ui.view.SlideTopStory;
import com.chentian.zhihudaily.zhihudaily.util.ViewUtils;
import com.koushikdutta.ion.Ion;

/**
 * @author chentian
 */
public class StoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private static final int ITEM_TYPE_HEADER = 0;
  private static final int ITEM_TYPE_ITEM = 1;

  private Context context;

  private List<StoryAbstract> topStoryList;
  private List<StoryAbstract> storyList;
  private String latestDate;

  public StoryAdapter(Context context) {
    this.context = context;
    this.storyList = new ArrayList<>();
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
    switch (viewType) {
      case ITEM_TYPE_HEADER:
        return new ViewHolderHeader(SlideTopStory.newInstance(viewGroup));
      case ITEM_TYPE_ITEM:
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_story, viewGroup, false);
        return new ViewHolderItem(view);
      default:
        throw new IllegalArgumentException();
    }
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
    if (viewHolder instanceof ViewHolderHeader) {
      ViewHolderHeader viewHolderHeader = (ViewHolderHeader) viewHolder;
      viewHolderHeader.getSlideTopStory().setTopStories(topStoryList);
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
      }
    }
  }

  @Override
  public int getItemCount() {
    return storyList.size();
  }

  @Override
  public int getItemViewType(int position) {
    return (position == 0) ? ITEM_TYPE_HEADER : ITEM_TYPE_ITEM;
  }

  public void setTopStories(List<StoryAbstract> topStoryList) {
    this.topStoryList = topStoryList;
    notifyItemChanged(0);
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


  public static class ViewHolderHeader extends RecyclerView.ViewHolder {

    private final SlideTopStory slideTopStory;

    public ViewHolderHeader(View itemView) {
      super(itemView);

      slideTopStory = (SlideTopStory) itemView;
    }

    public SlideTopStory getSlideTopStory() {
      return slideTopStory;
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
