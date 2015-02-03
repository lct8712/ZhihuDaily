package com.chentian.zhihudaily.zhihudaily.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chentian.zhihudaily.zhihudaily.R;
import com.chentian.zhihudaily.zhihudaily.api.model.Theme;
import com.chentian.zhihudaily.zhihudaily.dao.ThemeDao;

/**
 * @author chentian
 */
public class ThemeAdapter extends BaseAdapter {

  private Context context;

  private List<Theme> themeList;

  public ThemeAdapter(Context context) {
    this.context = context;
    this.themeList = new ArrayList<>();
  }

  @Override
  public int getCount() {
    return themeList.size();
  }

  @Override
  public Theme getItem(int position) {
    return themeList.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (convertView == null) {
      convertView = LayoutInflater.from(context).inflate(R.layout.list_item_theme, parent, false);
    }

    final Theme theme = getItem(position);

    ImageView imageIcon = ViewHolder.get(convertView, R.id.icon);
    TextView txtTitle = ViewHolder.get(convertView, R.id.title);
    ImageView imageAction = ViewHolder.get(convertView, R.id.action);

    txtTitle.setText(theme.getName());

    imageIcon.setVisibility(View.GONE);
    int resId = theme.isSubscribed() ? R.drawable.ic_action_next_item : R.drawable.ic_action_new;
    imageAction.setImageResource(resId);
    imageAction.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onThemeItemAction(theme);
      }
    });

    return convertView;
  }

  private void onThemeItemAction(final Theme theme) {
    if (theme.isSubscribed()) {
      return;
    }

    theme.setSubscribed(true);
    new AsyncTask<Void, Void, Void>() {
      @Override
      protected Void doInBackground(Void... params) {
        theme.save();
        setThemeList();

        return null;
      }
    }.execute();
  }

  public void setThemeList() {
    this.themeList = ThemeDao.listAll();
    new Handler(Looper.getMainLooper()).post(new Runnable() {
      @Override
      public void run() {
        notifyDataSetChanged();
      }
    });
  }
}
