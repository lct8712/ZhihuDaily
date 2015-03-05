package com.chentian.zhihudaily.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chentian.zhihudaily.data.model.Theme;
import com.chentian.zhihudaily.R;
import com.chentian.zhihudaily.util.ViewUtils;

/**
 * Adapter of theme list, shows in left drawer, including both subscribed and un-subscribed themes
 *
 * @author chentian
 */
public class ThemeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  public static interface ThemeItemSelectListener {
    void onMainPageItemSelect(View view);
    void onThemeItemSelected(Theme theme, View view);
  }

  public static interface ThemeSubscribedListener {
    void onThemeSubscribed(Theme theme);
  }

  private static final int ITEM_TYPE_HEADER = 0;
  private static final int ITEM_TYPE_ITEM = 2;

  private Context context;
  private List<Theme> themeList;
  private View selectedItemView;
  private ThemeItemSelectListener themeItemSelectListener;
  private ThemeSubscribedListener themeSubscribedListener;

  public ThemeAdapter(Context context) {
    this.context = context;
    this.themeList = new ArrayList<>();
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.list_item_theme, parent, false);
    switch (viewType) {
      case ITEM_TYPE_HEADER:
        return new ViewHolderMainThemeItem(view);
      case ITEM_TYPE_ITEM:
        return new ViewHolderItem(view);
      default:
        throw new IllegalArgumentException();
    }
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    // Ignore the ViewHolderMainThemeItem since there is only one main theme item
    if (holder instanceof ViewHolderItem) {
      ViewHolderItem viewHolderItem = (ViewHolderItem) holder;
      viewHolderItem.bindTheme(themeList.get(position - 1));
    }

    // Set item background
    boolean isSelected = (holder.itemView == selectedItemView);
    ViewUtils.setSelectedBackground(holder.itemView, isSelected, context);
  }

  @Override
  public int getItemCount() {
    // "1" for main page
    return themeList.size() + 1;
  }

  @Override
  public int getItemViewType(int position) {
    return (position == 0) ? ITEM_TYPE_HEADER : ITEM_TYPE_ITEM;
  }

  public void setThemeList(List<Theme> themeList) {
    this.themeList = themeList;
    notifyDataSetChanged();
  }

  public void setThemeItemSelectListener(ThemeItemSelectListener themeItemSelectListener) {
    this.themeItemSelectListener = themeItemSelectListener;
  }

  public void setThemeSubscribedListener(ThemeSubscribedListener themeSubscribedListener) {
    this.themeSubscribedListener = themeSubscribedListener;
  }

  public void setSelectedItemView(View selectedItemView) {
    this.selectedItemView = selectedItemView;
  }

  private class ViewHolderMainThemeItem extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ViewHolderMainThemeItem(View itemView) {
      super(itemView);

      setSelectedItemView(itemView);

      itemView.setBackgroundColor(context.getResources().getColor(R.color.left_drawer_background_selected));
      itemView.setActivated(true);
      itemView.setOnClickListener(this);

      itemView.findViewById(R.id.icon).setVisibility(View.VISIBLE);
      itemView.findViewById(R.id.action).setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
      if (themeItemSelectListener != null) {
        themeItemSelectListener.onMainPageItemSelect(view);
      }
    }
  }

  private class ViewHolderItem extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView txtTitle;
    private ImageView imageAction;
    private Theme theme;

    public ViewHolderItem(View itemView) {
      super(itemView);

      txtTitle = (TextView) itemView.findViewById(R.id.title);
      imageAction = (ImageView) itemView.findViewById(R.id.action);
      imageAction.setOnClickListener(this);
      itemView.setOnClickListener(this);
    }

    public void bindTheme(Theme theme) {
      this.theme = theme;

      txtTitle.setText(theme.getName());
      int resId = theme.isSubscribed() ? R.attr.drawableActionNextItem : R.attr.drawableActionNew;
      imageAction.setImageResource(ViewUtils.getAttrResourceId(context, resId));
    }

    @Override
    public void onClick(View view) {
      if (theme == null) {
        return;
      }

      if (view == imageAction && !theme.isSubscribed() && themeSubscribedListener != null) {
        themeSubscribedListener.onThemeSubscribed(theme);
        return;
      }

      if (themeItemSelectListener != null) {
        themeItemSelectListener.onThemeItemSelected(theme, view);
      }
    }
  }
}
