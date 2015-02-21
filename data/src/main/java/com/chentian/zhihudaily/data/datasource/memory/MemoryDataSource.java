package com.chentian.zhihudaily.data.datasource.memory;

import com.chentian.zhihudaily.data.datasource.DataSource;

/**
 * Data source which fetch data from memory
 * TODO: implement this
 *
 * @author chentian
 */
public class MemoryDataSource extends DataSource {
  public MemoryDataSource(DataSource next) {
    super(next);
  }
}
