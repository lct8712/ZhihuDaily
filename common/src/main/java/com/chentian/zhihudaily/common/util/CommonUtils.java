package com.chentian.zhihudaily.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author chentian
 */
public class CommonUtils {

  private static SimpleDateFormat simpleDateFormatFrom = new SimpleDateFormat("yyyyMMdd");
  private static SimpleDateFormat simpleDateFormatTo = new SimpleDateFormat("MMMdd EEE");

  /**
   * Format specified date string
   * @param dateString Date string with format yyyyMMdd
   * @return readable string with day in week
   */
  public static String formatDate(String dateString) {
    try {
      Date date = simpleDateFormatFrom.parse(dateString);
      return simpleDateFormatTo.format(date);
    } catch (ParseException e) {
      return dateString;
    }
  }
}
