package com.chentian.zhihudaily.data.datasource.file;

import com.chentian.zhihudaily.data.datasource.DataSource;

/**
 * Data source which fetch data from disk file
 * TODO: implement this
 *
 * @author chentian
 */
public class FileDataSource extends DataSource {
  public FileDataSource(DataSource next) {
    super(next);
  }
}
