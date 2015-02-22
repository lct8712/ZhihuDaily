package com.chentian.zhihudaily.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author chentian
 */
@SuppressWarnings("unused")
public class CollectionUtils {

  private CollectionUtils() {
  }

  public static boolean isEmpty(Collection<?> collection) {
    return collection == null || collection.isEmpty();
  }

  public static boolean isNotEmpty(Collection<?> collection) {
    return !isEmpty(collection);
  }

  public static <T> List<T> safeSubList(List<T> list, int start, int end) {
    if (start >= list.size()) {
      return Collections.emptyList();
    }
    return list.subList(start, Math.min(end, list.size()));
  }

  /**
   * If list is null, change to empty list
   */
  public static <T> List<T> notNull(List<T> list) {
    if (list == null) {
      return Collections.emptyList();
    }
    return list;
  }

  /**
   * Pick n elements from list randomly
   */
  public static <T> List<T> pickNRandom(List<T> list, int n) {
    List<T> copy = new ArrayList<>(list);
    Collections.shuffle(copy);
    return safeSubList(copy, 0, n);
  }
}
