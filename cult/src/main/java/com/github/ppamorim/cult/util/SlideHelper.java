package com.github.ppamorim.cult.util;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.github.ppamorim.cult.CultView;
import com.github.ppamorim.cult.R;

public class SlideHelper {

  private Context context;
  private CultView cultView;

  public SlideHelper(Context context, CultView cultView) {
    this.context = context;
    this.cultView = cultView;
  }

  public void slideInTop(View view) {
   slideIn(view, R.anim.slide_in_top);
  }

  public void slideInBottom(View view) {
    slideIn(view, R.anim.slide_int_bottom);
  }

  public void slideOutTop(View view) {
    slideOut(view, R.anim.slide_out_top);
  }

  public void slideOutBottom(View view) {
    slideOut(view, R.anim.slide_out_bottom);
  }

  private void slideIn(final View view, int animation) {
    view.setVisibility(View.VISIBLE);
    Animation slideIn = AnimationUtils.loadAnimation(context, animation);
    slideIn.setAnimationListener(new Animation.AnimationListener() {
      @Override public void onAnimationStart(Animation animation) {
        view.setVisibility(View.VISIBLE);
      }

      @Override public void onAnimationEnd(Animation animation) {
        view.setVisibility(View.VISIBLE);
      }

      @Override public void onAnimationRepeat(Animation animation) {
      }
    });
    view.startAnimation(slideIn);
  }

  private void slideOut(final View view, int animation) {
    Animation slideOut = AnimationUtils.loadAnimation(context, animation);
    slideOut.setAnimationListener(new Animation.AnimationListener() {
      @Override public void onAnimationStart(Animation animation) {
        view.setVisibility(View.VISIBLE);
      }

      @Override public void onAnimationEnd(Animation animation) {
        view.setVisibility(View.GONE);
        cultView.resetAnimation();
      }

      @Override public void onAnimationRepeat(Animation animation) {
      }
    });
    view.startAnimation(slideOut);
  }

}
