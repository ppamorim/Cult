package com.github.ppamorim.cult.util;

import android.content.res.Resources;
import android.util.TypedValue;

public class ViewUtil {

  public static int dpToPx(Resources resources, float dp) {
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
        resources.getDisplayMetrics());
  }

}
