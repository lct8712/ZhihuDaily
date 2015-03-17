package com.chentian.zhihudaily;

import android.content.Context;

import com.chentian.zhihudaily.common.provider.BusProvider;
import com.chentian.zhihudaily.common.provider.UiModeProvider;
import com.chentian.zhihudaily.domain.respository.DataRepository;
import com.chentian.zhihudaily.domain.respository.DataRepositoryImpl;
import com.orm.SugarApp;
import com.squareup.otto.Bus;

/**
 * @author chentian
 */
public class DailyApplication extends SugarApp {

  private static DailyApplication instance;

  private BusProvider busProvider;
  private DataRepository dataRepository;
  private UiModeProvider uiModeProvider;

  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    instance = this;
  }

  @Override
  public void onCreate() {
    super.onCreate();

    busProvider = new BusProvider();
    dataRepository = new DataRepositoryImpl(getApplicationContext(), getUiBus());
    uiModeProvider = new UiModeProvider();
  }

  public static DailyApplication getInstance() {
    return instance;
  }

  public DataRepository getDataRepository() {
    return dataRepository;
  }

  public UiModeProvider getUiModeProvider() {
    return uiModeProvider;
  }

  public Bus getDataBus() {
    return busProvider.getDataBus();
  }

  public Bus getUiBus() {
    return busProvider.getUiBus();
  }
}
