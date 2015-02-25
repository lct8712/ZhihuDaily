package com.chentian.zhihudaily.ui.view;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chentian.zhihudaily.R;
import com.chentian.zhihudaily.common.util.Const;

/**
 * Abstract recycle view with sections
 *
 * This is a porting of the class SimpleSectionedListAdapter provided by Google
 *   https://github.com/google/iosched
 *
 * @author chentian
 */
public abstract class SectionedRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  protected static class Section {

    private int firstPosition;
    private int sectionedPosition;
    private String title;

    public Section(int firstPosition, String title) {
      this.firstPosition = firstPosition;
      this.title = title;
    }

    public String getTitle() {
      return title;
    }
  }

  private static final int VIEW_TYPE_HEADER = 10000;

  private SparseArray<Section> sections = new SparseArray<>();

  protected abstract RecyclerView.ViewHolder onCreateViewHolderForItem(ViewGroup parent, int viewType);

  protected abstract void onBindViewHolderForItem(RecyclerView.ViewHolder holder, int position);

  protected abstract int getItemCountForItem();

  protected abstract int getItemViewTypeForItem(int position);

  protected abstract View getSectionHeaderView(ViewGroup parent);

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (viewType == VIEW_TYPE_HEADER) {
      return new ViewHolderSectionHeader(getSectionHeaderView(parent));
    }
    return onCreateViewHolderForItem(parent, viewType);
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (isSectionHeaderPosition(position)) {
      ViewHolderSectionHeader viewHolderSectionHeader = (ViewHolderSectionHeader) holder;
      viewHolderSectionHeader.bindSection(sections.get(position));
    } else {
      onBindViewHolderForItem(holder, sectionedPositionToPosition(position));
    }
  }

  @Override
  public int getItemCount() {
    return getItemCountForItem() + sections.size();
  }

  @Override
  public int getItemViewType(int position) {
    return isSectionHeaderPosition(position) ?
            VIEW_TYPE_HEADER : getItemViewTypeForItem(sectionedPositionToPosition(position));
  }

  /**
   * Return title the last section before position
   */
  public String getSectionTitleBeforePosition(int position) {
    for (int i = sections.size() - 1; i >= 0; i--) {
      Section section = sections.get(sections.keyAt(i));
      if (section.sectionedPosition < position) {
        return section.getTitle();
      }
    }
    return Const.EMPTY_STRING;
  }

  public String getSectionTitle(int position) {
    if (!isSectionHeaderPosition(position)) {
      return Const.EMPTY_STRING;
    }
    return sections.get(position).getTitle();
  }

  protected void addSection(Section section) {
    section.sectionedPosition = section.firstPosition + sections.size();
    sections.append(section.sectionedPosition, section);
  }

  protected void clearSections() {
    sections.clear();
  }

  private boolean isSectionHeaderPosition(int position) {
    return sections.get(position) != null;
  }

  private int sectionedPositionToPosition(int sectionedPosition) {
    if (isSectionHeaderPosition(sectionedPosition)) {
      return RecyclerView.NO_POSITION;
    }

    int offset = 0;
    for (int i = 0; i < sections.size(); i++) {
      if (sections.valueAt(i).sectionedPosition > sectionedPosition) {
        break;
      }
      --offset;
    }
    return sectionedPosition + offset;
  }

  /**
   * View holder for a section header
   */
  private static class ViewHolderSectionHeader extends RecyclerView.ViewHolder {

    private final TextView txtTitle;

    public ViewHolderSectionHeader(View itemView) {
      super(itemView);

      txtTitle = (TextView) itemView.findViewById(R.id.title);
      if (txtTitle == null) {
        throw new IllegalArgumentException("Section header view should have a TextView with id 'title'");
      }
    }

    public void bindSection(Section section) {
      txtTitle.setText(section.getTitle());
    }
  }
}
