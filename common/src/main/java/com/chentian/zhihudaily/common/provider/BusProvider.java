package com.chentian.zhihudaily.common.provider;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Provide instance of otto bus
 *
 * @author chentian
 */
public class BusProvider {

  private static Bus dataBus = new Bus(ThreadEnforcer.ANY);
  private static Bus UIBus = new Bus(ThreadEnforcer.MAIN);

  public static Bus getDataBus() {
    return dataBus;
  }

  public static Bus getUIBus() {
    return UIBus;
  }
}
