package com.chentian.zhihudaily.ui.adapter;

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

import com.chentian.zhihudaily.R;
import com.chentian.zhihudaily.data.model.StoryAbstract;
import com.chentian.zhihudaily.domain.StoryRepository;
import com.chentian.zhihudaily.ui.view.ArticleHeaderView;
import com.chentian.zhihudaily.ui.view.SectionedRecycleViewAdapter;
import com.chentian.zhihudaily.ui.view.SlideTopStory;
import com.chentian.zhihudaily.util.ViewUtils;
import com.koushikdutta.ion.Ion;

/**
 * Adapter used in both main page list and theme list
 *
 * @author chentian
 */
public class StoryAdapter extends SectionedRecycleViewAdapter {

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
  private HeaderType headerType;

  public StoryAdapter(Context context, HeaderType headerType) {
    this.context = context;
    this.headerType = headerType;
    this.storyList = new ArrayList<>();
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolderForItem(ViewGroup parent, int viewType) {
    switch (viewType) {
      case ITEM_TYPE_HEADER_SLIDE:
        return new ViewHolderHeaderSlide(SlideTopStory.newInstance(parent));
      case ITEM_TYPE_HEADER_NORMAL:
        return new ViewHolderHeaderNormal(ArticleHeaderView.newInstance(parent));
      case ITEM_TYPE_ITEM:
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_story, parent, false);
        return new ViewHolderItem(view, context);
      default:
        throw new IllegalArgumentException();
    }
  }

  @Override
  public void onBindViewHolderForItem(RecyclerView.ViewHolder holder, int position) {
    if (holder instanceof ViewHolderHeaderSlide) {
      ViewHolderHeaderSlide viewHolderHeaderSlide = (ViewHolderHeaderSlide) holder;
      viewHolderHeaderSlide.getSlideTopStory().setTopStories(topStoryList);

    } else if (holder instanceof ViewHolderHeaderNormal) {
      ViewHolderHeaderNormal viewHolderHeaderNormal = (ViewHolderHeaderNormal) holder;
      viewHolderHeaderNormal.getArticleHeaderView().setData(headerTitle, headerImageUrl);

    } else if (holder instanceof ViewHolderItem) {
      ViewHolderItem viewHolderItem = (ViewHolderItem) holder;
      viewHolderItem.bindStory(storyList.get(position - 1));
    }
  }

  @Override
  public int getItemCountForItem() {
    // "1" for header, slide header or normal header, not section headers
    return storyList.size() + 1;
  }

  @Override
  public int getItemViewTypeForItem(int position) {
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

  @Override
  protected View getSectionHeaderView(ViewGroup parent) {
    return LayoutInflater.from(context).inflate(R.layout.list_item_section_header, parent, false);
  }

  public void setTopStories(List<StoryAbstract> topStoryList) {
    this.topStoryList = topStoryList;
    notifyDataSetChanged();
  }

  public void setNormalHeaderData(String headerTitle, String headerImageUrl) {
    this.headerTitle = headerTitle;
    this.headerImageUrl = headerImageUrl;
  }

  public void setStoryList(List<StoryAbstract> data, String sectionTitle) {
    clearSections();
    if (!TextUtils.isEmpty(sectionTitle)) {
      addSection(new Section(1, sectionTitle));
    }

    storyList = data;
    notifyDataSetChanged();
  }

  public void appendStoryList(List<StoryAbstract> data, String sectionTitle) {
    if (!TextUtils.isEmpty(sectionTitle)) {
      addSection(new Section(storyList.size() + 1, sectionTitle));
    }

    storyList.addAll(data);
    notifyDataSetChanged();
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
    private final TextView imageTag;
    private Context context;
    private StoryAbstract story;

    public ViewHolderItem(View itemView, Context context) {
      super(itemView);

      this.context = context;
      txtTitle = (TextView) itemView.findViewById(R.id.title);
      imageIcon = (ImageView) itemView.findViewById(R.id.image);
      imageTag = (TextView) itemView.findViewById(R.id.image_tag);
      itemView.setOnClickListener(this);
    }

    public void bindStory(StoryAbstract story) {
      this.story = story;

      txtTitle.setText(story.getTitle());
      setTextTitleRead(story.isRead());

      imageTag.setVisibility(story.isMultiPic() ? View.VISIBLE : View.GONE);

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

    private void setTextTitleRead(boolean read) {
      int colorDeep = context.getResources().getColor(R.color.text_deep);
      int colorRead = context.getResources().getColor(R.color.list_item_text_read);
      txtTitle.setTextColor(read ? colorRead : colorDeep);
    }

    @Override
    public void onClick(final View view) {
      StoryRepository.markAsRead(story);
      setTextTitleRead(true);
      ViewUtils.openDetailActivity(story.getId(), context);
    }
  }
}
