package com.chentian.zhihudaily.common.util;

import java.util.List;

import android.text.TextUtils;

/**
 * @author chentian
 */
public class WebUtils {

  public static final String MIME_HTML_TYPE = "text/html";

  public static final String DEFAULT_CHARSET = "UTF-8";

  public static final String ASSERT_DIR = "file:///android_asset/";

  private static final String CSS_LINK_PATTERN = "<link rel=\"stylesheet\" type=\"text/css\" href=\"%s\"/>";
  private static final String DIV_IMAGE_PLACE_HOLDER = "class=\"img-place-holder\"";
  private static final String DIV_IMAGE_PLACE_HOLDER_IGNORED = "class=\"img-place-holder-ignored\"";
  private static final String NIGHT_DIV_TAG_START = "<div class=\"night\">";
  private static final String DIV_TAG_END = "</div>";

  public static String BuildHtmlWithCss(String htmlContent, List<String> cssUrls, boolean isNightMode) {
    if (TextUtils.isEmpty(htmlContent)) {
      return Const.EMPTY_STRING;
    }

    StringBuilder result = new StringBuilder();
    for (String cssUrl : CollectionUtils.notNull(cssUrls)) {
      result.append(String.format(CSS_LINK_PATTERN, cssUrl));
    }

    if (isNightMode) {
      result.append(NIGHT_DIV_TAG_START);
    }
    result.append(htmlContent.replace(DIV_IMAGE_PLACE_HOLDER, DIV_IMAGE_PLACE_HOLDER_IGNORED));
    if (isNightMode) {
      result.append(DIV_TAG_END);
    }
    return result.toString();
  }
}
