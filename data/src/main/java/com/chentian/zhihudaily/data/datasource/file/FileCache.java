package com.chentian.zhihudaily.data.datasource.file;

import java.io.File;
import java.io.IOException;

import android.text.TextUtils;
import org.apache.commons.io.FileUtils;

import com.chentian.zhihudaily.common.util.Const;

/**
 * @author chentian
 */
public class FileCache {

  public enum DataType {
    STORY_DETAIL, STORY_LIST
  }

  private String cacheDir;

  public FileCache(String cacheDir) {
    if (TextUtils.isEmpty(cacheDir)) {
      throw new IllegalArgumentException("cache dir is empty.");
    }

    if (!cacheDir.endsWith(File.separator)) {
      cacheDir += File.separator;
    }
    this.cacheDir = cacheDir;
  }

  public boolean exists(DataType dataType, String key) {
    return getFile(dataType, key).exists();
  }

  public boolean writeContent(DataType dataType, String key, String content) {
    try {
      FileUtils.writeStringToFile(getFile(dataType, key), content, Const.ENCODE_UTF8);
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public String readContent(DataType dataType, String key) {
    try {
      return FileUtils.readFileToString(getFile(dataType, key), Const.ENCODE_UTF8);
    } catch (IOException e) {
      e.printStackTrace();
      return Const.EMPTY_STRING;
    }
  }

  private File getFile(DataType dataType, String key) {
    return new File(cacheDir + dataType.toString() + key);
  }
}
