package com.chentian.zhihudaily.mvp.presenter;

/**
 * Interface representing a Presenter in a model view presenter (MVP) pattern.
 *
 * @author chentian
 */
public interface MVPPresenter {
  /**
   * Method that control the lifecycle of the view.
   * It should be called in the view's(Activity or Fragment) onResume() method.
   */
  void onResume();

  /**
   * Method that control the lifecycle of the view.
   * It should be called in the view's(Activity or Fragment) onPause() method.
   */
  void onPause();
}
