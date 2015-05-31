package com.github.ppamorim.cult.util;

import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import com.github.ppamorim.cult.CultView;

public class ViewUtil {

  public static int dpToPx(Resources resources, float dp) {
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
        resources.getDisplayMetrics());
  }

  public static boolean isViewHit(View viewParent, View view, int x, int y) {
    int[] viewLocation = new int[2];
    view.getLocationOnScreen(viewLocation);
    int[] parentLocation = new int[2];
    viewParent.getLocationOnScreen(parentLocation);
    int screenX = parentLocation[0] + x;
    int screenY = parentLocation[1] + y;
    return screenX >= viewLocation[0]
        && screenX < viewLocation[0] + view.getWidth()
        && screenY >= viewLocation[1]
        && screenY < viewLocation[1] + view.getHeight();
  }

}
