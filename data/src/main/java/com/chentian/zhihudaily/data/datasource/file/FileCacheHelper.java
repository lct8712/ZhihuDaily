package com.chentian.zhihudaily.data.datasource.file;

import java.io.*;

import android.content.Context;
import org.apache.commons.io.FileUtils;

import com.chentian.zhihudaily.common.util.Const;

/**
 * @author chentian
 */
public class FileCacheHelper {

  public enum DataType {
    Story, Image
  }

  private Context context;
  private DataType dataType;

  public FileCacheHelper(Context context, DataType dataType) {
    this.context = context;
    this.dataType = dataType;
  }

  public boolean exists(String key) {
    return getFile(key).exists();
  }

  public boolean writeContent(String key, String content) {
    try {
      FileUtils.writeStringToFile(getFile(key), content, Const.ENCODE_UTF8);
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public String readContent(String key) {
    try {
      return FileUtils.readFileToString(getFile(key), Const.ENCODE_UTF8);
    } catch (IOException e) {
      e.printStackTrace();
      return Const.EMPTY_STRING;
    }
  }

  private File getFile(String key) {
    return new File(context.getCacheDir().getPath() + File.separator + dataType.toString() + key);
  }
}
