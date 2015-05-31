/*
* Copyright (C) 2015 Pedro Paulo de Amorim
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.github.ppamorim.cult.util;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.github.ppamorim.cult.CultView;
import com.github.ppamorim.cult.R;

/**
 * This class provide some helpers
 * to make any animation more easier.
 *
 * @author Pedro Paulo Amorim
 *
 */
public class AnimationHelper {

  private Context context;
  private CultView cultView;

  public AnimationHelper(Context context, CultView cultView) {
    this.context = context;
    this.cultView = cultView;
  }

  public void slideInTop(View view) {
    slideInTop(view, 0);
  }

  public void slideInTop(View view, long duration) {
    animationIn(view, R.anim.slide_in_top, duration);
  }

  public void slideInBottom(View view) {
    slideInBottom(view, 0);
  }

  public void slideInBottom(View view, long duration) {
    animationIn(view, R.anim.slide_int_bottom, duration);
  }

  public void slideOutTop(View view) {
    slideOutTop(view, 0);
  }

  public void slideOutTop(View view, long duration) {
    animationOut(view, R.anim.slide_out_top, duration);
  }

  public void slideOutBottom(View view) {
    slideOutBottom(view, 0);
  }

  public void slideOutBottom(View view, long duration) {
    animationOut(view, R.anim.slide_out_bottom, duration);
  }

  public void fadeIn(View view) {
    fadeIn(view, 0);
  }

  public void fadeIn(View view, long duration) {
    animationIn(view, R.anim.fade_in, duration);
  }

  public void fadeOut(View view) {
    fadeOut(view, 0);
  }

  public void fadeOut(View view, long duration) {
    animationOut(view, R.anim.fade_out, duration);
  }

  private void animationIn(final View view, int animationId, long duration) {
    view.setVisibility(View.VISIBLE);
    Animation animation = AnimationUtils.loadAnimation(context, animationId);
    if(duration > 0) {
      animation.setDuration(duration);
    }
    animation.setAnimationListener(new Animation.AnimationListener() {
      @Override public void onAnimationStart(Animation animation) {
        view.setVisibility(View.VISIBLE);
      }

      @Override public void onAnimationEnd(Animation animation) {
        view.setVisibility(View.VISIBLE);
        cultView.setAnimationRunning(false);
      }

      @Override public void onAnimationRepeat(Animation animation) {
      }
    });
    view.startAnimation(animation);
  }

  private void animationOut(final View view, int animationId, long duration) {
    view.setVisibility(View.VISIBLE);
    Animation animation = AnimationUtils.loadAnimation(context, animationId);
    if(duration > 0) {
      animation.setDuration(duration);
    }
    animation.setAnimationListener(new Animation.AnimationListener() {
      @Override public void onAnimationStart(Animation animation) {
        view.setVisibility(View.VISIBLE);
      }

      @Override public void onAnimationEnd(Animation animation) {
        view.setVisibility(View.GONE);
        cultView.setAnimationRunning(false);
      }

      @Override public void onAnimationRepeat(Animation animation) {
      }
    });
    view.startAnimation(animation);
  }

}
