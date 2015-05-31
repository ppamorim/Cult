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
package com.github.ppamorim.cult;

import android.support.v4.widget.ViewDragHelper;
import android.view.View;

/**
 * This is a callback to animate
 * the CultView's second view.
 *
 * @author Pedro Paulo Amorim
 *
 */
public class CultHelperCallback extends ViewDragHelper.Callback {

  private final View content;
  private final CultView cultView;

  /**
   * The constructor get the instance of FlapBar and Bar
   *
   * @param cultView provide the instance of CultView
   * @param content provide the instance view, this is inflated on CultView
   */
  public CultHelperCallback(CultView cultView, View content) {
    this.cultView = cultView;
    this.content = content;
  }

  /**
   * Check if view on focus is the bar
   *
   * @param child return the view on focus
   * @param pointerId return the id of view
   * @return if the child on focus is equals the bar
   */
  @Override public boolean tryCaptureView(View child, int pointerId) {
    return child.equals(content);
  }

  /**
   * Return the value of slide based
   * on left and width of the element
   *
   * @param child return the view on focus
   * @param top return the top size of cultView
   * @param dy return the scroll on y-axis
   * @return the offset of slide
   */
  @Override public int clampViewPositionVertical(View child, int top, int dy) {
    return Math.min(top, cultView.getHeight());
  }

  /**
   * This guy return the max value of view that can
   * slide based on #camplViewPositionHorizontal
   *
   * @param child return the view on focus
   * @return max distance that view on focus can slide
   */
  @Override public int getViewVerticalDragRange(View child) {
    return (int) cultView.getVerticalDragRange();
  }

  /**
   * This is called only the touch on bar view is released.
   *
   * @param releasedChild return the view on focus
   * @param xvel return the speed of X animation
   * @param yvel return the speed of Y animation
   */
  @Override public void onViewReleased(View releasedChild, float xvel, float yvel) {
    super.onViewReleased(releasedChild, xvel, yvel);
    //flapBar.verifyPosition();
  }

}