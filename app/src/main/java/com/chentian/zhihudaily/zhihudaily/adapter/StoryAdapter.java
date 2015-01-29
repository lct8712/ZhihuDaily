package com.chentian.zhihudaily.zhihudaily.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chentian.zhihudaily.zhihudaily.R;
import com.chentian.zhihudaily.zhihudaily.api.model.StoryAbstract;
import com.chentian.zhihudaily.zhihudaily.ui.view.SlideTopStory;
import com.koushikdutta.ion.Ion;

/**
 * @author chentian
 */
public class StoryAdapter extends BaseAdapter {

  private Context context;
  private SlideTopStory slideTopStory;

  private List<StoryAbstract> topStoryList;
  private List<StoryAbstract> storyList;
  private String latestDate;

  public StoryAdapter(Context context) {
    this.storyList = new ArrayList<>();
    this.context = context;
  }

  @Override
  public int getCount() {
    return storyList.size();
  }

  @Override
  public StoryAbstract getItem(int position) {
    return storyList.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    // The first item of list is the sliding image view
    if (position == 0) {
      if (slideTopStory == null) {
        slideTopStory = SlideTopStory.newInstanceExpandableDescription(parent);
        slideTopStory.setTopStories(topStoryList);
      }
      convertView = slideTopStory;
    } else if (convertView == null || convertView instanceof SlideTopStory) {
      convertView = LayoutInflater.from(context).inflate(R.layout.story_list_item, parent, false);
    }

    // Fill story item data
    if (position != 0) {
      TextView txtTitle = ViewHolder.get(convertView, R.id.title);
      ImageView imageIcon = ViewHolder.get(convertView, R.id.image);

      StoryAbstract storyAbstract = getItem(position);
      txtTitle.setText(storyAbstract.getTitle());

      String imageUrl = storyAbstract.getImageUrl();
      if (!TextUtils.isEmpty(imageUrl)) {
        Ion.with(imageIcon)
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)
                .load(imageUrl);
      }
    }

    return convertView;
  }

  public void setTopStoryList(List<StoryAbstract> data) {
    topStoryList = data;
    if (slideTopStory != null) {
      slideTopStory.setTopStories(data);
    }
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
}
