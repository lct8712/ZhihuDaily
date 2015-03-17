package com.chentian.zhihudaily.domain.bus;

import lombok.Data;

/**
 * Offline progress entity transfer on bus
 *
 * @author chentian
 */
@Data
@SuppressWarnings("unused")
public class OfflineProgress {

  public enum Type {
    SUCCESS, FAILED, PROCESSING
  }

  private Type type;

  private int percent;

  public OfflineProgress(Type type) {
    this.type = type;
  }

  public OfflineProgress(int percent) {
    this.type = Type.PROCESSING;
    this.percent = percent;
  }
}
