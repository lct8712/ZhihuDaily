package com.chentian.zhihudaily.zhihudaily.util;

import java.util.List;

/**
 * @author chentian
 */
public class WebUtil {

  public static final String MIME_HTML_TYPE = "text/html";

  public static final String DEFAULT_CHARSET = "UTF-8";

  public static final String ASSERT_DIR = "file:///android_asset/";

  private static final String CSS_LINK_PATTERN = "<link rel=\"stylesheet\" type=\"text/css\" href=\"%s\"/>";
  private static final String DIV_IMAGE_PLACE_HOLDER = "class=\"img-place-holder\"";
  private static final String DIV_IMAGE_PLACE_HOLDER_IGNORED = "class=\"img-place-holder-ignored\"";

  public static String BuildHtmlWithCss(String htmlContent, List<String> cssUrls) {
    StringBuilder result = new StringBuilder();
    for (String cssUrl : CollectionUtils.notNull(cssUrls)) {
      result.append(String.format(CSS_LINK_PATTERN, cssUrl));
    }
    result.append(htmlContent.replace(DIV_IMAGE_PLACE_HOLDER, DIV_IMAGE_PLACE_HOLDER_IGNORED));
//    result.append(htmlContent);
    return result.toString();
  }
}
