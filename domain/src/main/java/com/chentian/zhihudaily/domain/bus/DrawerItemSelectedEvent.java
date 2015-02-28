package com.chentian.zhihudaily.domain.bus;

import lombok.Data;

/**
 * Event post on bus after left drawer item selected
 *
 * @author chentian
 */
@Data
public class DrawerItemSelectedEvent {

  public static enum ItemType {
    MainPage, Theme
  }

  private ItemType itemType;

  private long themeId;

  public DrawerItemSelectedEvent(ItemType itemType) {
    this.itemType = itemType;
  }

  public DrawerItemSelectedEvent(ItemType itemType, long themeId) {
    this.itemType = itemType;
    this.themeId = themeId;
  }
}
