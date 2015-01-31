package com.chentian.zhihudaily.zhihudaily.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.chentian.zhihudaily.zhihudaily.R;
import com.chentian.zhihudaily.zhihudaily.api.model.StoryAbstract;
import com.chentian.zhihudaily.zhihudaily.ui.view.StoryListView;
import com.chentian.zhihudaily.zhihudaily.util.ViewUtils;

/**
 * Fragment containing list of stories
 */
public class StoryListFragment extends Fragment {

  private StoryListView listView;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_story, container, false);
    listView = (StoryListView) view.findViewById(R.id.list_view);
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        StoryAbstract storyAbstract = (StoryAbstract) listView.getAdapter().getItem(position);
        ViewUtils.openDetailActivity(storyAbstract.getId(), getActivity());
      }
    });

    return view;
  }
}
