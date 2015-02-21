package com.chentian.zhihudaily.common.provider;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Provide instance of otto bus
 *
 * @author chentian
 */
public class BusProvider {

  /**
   * Be able to post from any thread to main thread
   */
  public static class MainThreadBus extends Bus {
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override public void post(final Object event) {
      if (Looper.myLooper() == Looper.getMainLooper()) {
        super.post(event);
      } else {
        handler.post(new Runnable() {
          @Override
          public void run() {
            MainThreadBus.super.post(event);
          }
        });
      }
    }
  }

  private static Bus dataBus = new Bus(ThreadEnforcer.ANY);
  private static Bus uiBus = new MainThreadBus();

  public static Bus getDataBus() {
    return dataBus;
  }

  public static Bus getUiBus() {
    return uiBus;
  }
}
