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

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.dd.ShadowLayout;
import com.github.ppamorim.cult.util.AnimationHelper;
import com.github.ppamorim.cult.util.ViewUtil;

/**
 * This is the main class of the library
 * here is inflated some views and added
 * some params to it. After is added a
 * isntance os CultHelperCallback to
 * provide a drag possibility to CultView
 * (not working yet)
 *
 * @author Pedro Paulo Amorim
 *
 */
public class CultView extends FrameLayout {

  private static final int INVALID_POINTER = -1;
  private static final int DEFAULT_HEIGHT = 64;
  private static final int DEFAULT_PADDING = 0;
  private static final float DEFAULT_DRAG_LIMIT = 0.5f;
  private static final float SENSITIVITY = 1.0f;

  private boolean isAnimationRunning;

  private int toolbarHeight;
  private int innerPadding;
  private int contentResId;
  private int activePointerId = INVALID_POINTER;
  private float verticalDragRange;

  private ShadowLayout shadowLayout;
  private FrameLayout innerView;
  private Toolbar innerToolbar;
  private FrameLayout contentInner;
  private Toolbar outToolbar;
  private FrameLayout contentOut;
  private View shadow;

  private CultHelperCallback cultHelperCallback;
  private ViewDragHelper dragHelper;
  private AnimationHelper animationHelper;

  public CultView(Context context) {
    this(context, null);
  }

  public CultView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public CultView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    getStyle(context, attrs, defStyleAttr);
  }

  /**
   * Override method to map some views and to configure the
   * view height, animation and to initialize DragViewHelper.
   */
  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    if (!isInEditMode()) {
      config();
      configSizes();
      configDragViewHelper();
      configSlideHelper();
    }
  }

  /**
   * Force to measure the #contentOut view
   *
   * @param widthMeasureSpec provide the width of this view based on pixels
   * @param heightMeasureSpec provide the height of this view based on pixels
   */
  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    int measureWidth = MeasureSpec.makeMeasureSpec(
        getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),
        MeasureSpec.EXACTLY);
    int measureHeight = MeasureSpec.makeMeasureSpec(
        getMeasuredHeight() - getPaddingTop() - getPaddingBottom(),
        MeasureSpec.EXACTLY);
    if (contentOut != null) {
      contentOut.measure(measureWidth, measureHeight);
    }
  }

  /**
   *  When the system verify that is needed to
   *  measure the view, was added a method to
   *  set the vertical drag range based on
   *  the height of the view
   */
  @Override protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
    super.onSizeChanged(width, height, oldWidth, oldHeight);
    setVerticalDragRange(height);
  }

  /**
   * To ensure the animation is going to work this method has been override to call
   * postInvalidateOnAnimation if the view is not settled yet.
   */
  @Override public void computeScroll() {
    if (!isInEditMode() && dragHelper != null && dragHelper.continueSettling(true)) {
      ViewCompat.postInvalidateOnAnimation(this);
    }
  }

  /**
   * Override method to intercept only touch events over the drag view and to cancel the drag when
   * the action associated to the MotionEvent is equals to ACTION_CANCEL or ACTION_UP.
   *
   * @param ev captured.
   * @return true if the view is going to process the touch event or false if not.
   */
  @Override public boolean onInterceptTouchEvent(MotionEvent ev) {
    if (!isEnabled()) {
      return false;
    }
    switch (MotionEventCompat.getActionMasked(ev)) {
      case MotionEvent.ACTION_CANCEL:
      case MotionEvent.ACTION_UP:
        dragHelper.cancel();
        return false;
      case MotionEvent.ACTION_DOWN:
        int index = MotionEventCompat.getActionIndex(ev);
        activePointerId = MotionEventCompat.getPointerId(ev, index);
        if (activePointerId == INVALID_POINTER) {
          return false;
        }
      default:
        dragHelper.processTouchEvent(ev);
        return dragHelper.shouldInterceptTouchEvent(ev);
    }
  }

  /**
   * Override method to dispatch touch
   * event to the dragged view.
   *
   * @param ev captured.
   * @return true if the touch event is realized over the drag.
   */
  @Override public boolean onTouchEvent(MotionEvent ev) {
    int actionMasked = MotionEventCompat.getActionMasked(ev);
    if ((actionMasked & MotionEventCompat.ACTION_MASK) == MotionEvent.ACTION_DOWN) {
      activePointerId = MotionEventCompat.getPointerId(ev, actionMasked);
    }
    if (activePointerId == INVALID_POINTER) {
      return false;
    }
    dragHelper.processTouchEvent(ev);
    return ViewUtil.isViewHit(this, contentOut, (int) ev.getX(), (int) ev.getY());
  }

  /**
   * Configure the instance of DragViewHelper
   */
  public void configDragViewHelper() {
    cultHelperCallback = new CultHelperCallback(this, contentOut);
    dragHelper = ViewDragHelper.create(this, SENSITIVITY, cultHelperCallback);
  }

  /**
   * This method inflate the base view and
   * instantiate some layouts based on
   * this view
   *
   * @return instance of CultView
   */
  private CultView config() {
    addView(inflate(getContext(), R.layout.layout_cult, null));
    shadowLayout = (ShadowLayout) findViewById(R.id.shadow_layout);
    innerView = (FrameLayout) findViewById(R.id.inner_view);
    innerToolbar = (Toolbar) findViewById(R.id.inner_toolbar);
    outToolbar = (Toolbar) findViewById(R.id.out_toolbar);
    contentOut = (FrameLayout) findViewById(R.id.content_out);
    contentInner = (FrameLayout) findViewById(R.id.content);
    shadow = findViewById(R.id.shadow);
    if(contentResId != 0) {
      contentInner.addView(inflate(getContext(), contentResId, null));
    }
    return this;
  }

  /**
   * Configure the instance of AnimationHelper
   * that provide the base animation
   * of the library
   */
  public void configSlideHelper() {
    if(animationHelper == null) {
      animationHelper = new AnimationHelper(getContext(), this);
    }
  }

  /**
   * Configure the size of the Toolbars based
   * on a height param
   */
  private void configSizes() {
    RelativeLayout.LayoutParams layoutParams =
        new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, toolbarHeight
            + innerPadding * 2);
    outToolbar.setLayoutParams(layoutParams);
    RelativeLayout.LayoutParams layoutParamsShadow =
        new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, toolbarHeight);
    layoutParamsShadow.setMargins(innerPadding, innerPadding, innerPadding, innerPadding);
    shadowLayout.setLayoutParams(layoutParamsShadow);
  }

  /**
   * Initialize some attributes to provide
   * the height, padding and content to CultView
   */
  private void getStyle(Context context, AttributeSet attrs, int defStyleAttr) {
    if(attrs != null) {
      TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.cult_view, defStyleAttr, 0);
      toolbarHeight = (int) a.getDimension(R.styleable.cult_view_toolbar_height, DEFAULT_HEIGHT);
      innerPadding = (int) a.getDimension(R.styleable.cult_view_inner_padding, DEFAULT_PADDING);
      contentResId = a.getResourceId(R.styleable.cult_view_content_view, 0);
      a.recycle();
    }
  }

  /**
   * This method is used to set
   * the padding to shadowLayout
   *
   * @param left left padding
   * @param top top padding
   * @param right right padding
   * @param bottom bottom padding
   * @return instance of CultView
   */
  public CultView setInnerPadding(int left, int top, int right, int bottom) {
    shadowLayout.setPadding(ViewUtil.dpToPx(getResources(), left),
        ViewUtil.dpToPx(getResources(), top), ViewUtil.dpToPx(getResources(), right),
        ViewUtil.dpToPx(getResources(), bottom));
    return this;
  }

  /**
   * Set the OutToolbar layout based of the @resourceId
   *
   * @param resourceId id of layout
   */
  public void setOutToolbarLayout(int resourceId) {
    outToolbar.addView(inflate(getContext(), resourceId, null));
  }

  /**
   * Set the OutToolbar layout based of the view instance
   *
   * @param view View of layout
   */
  public void setOutToolbarLayout(View view) {
    outToolbar.addView(view);
  }

  /**
   * Set the OutContent layout based of the @resourceId
   *
   * @param resourceId id of layout
   */
  public void setOutContentLayout(int resourceId) {
    contentOut.addView(inflate(getContext(), resourceId, null));
  }

  /**
   * Set the OutContent layout based of the view instance
   *
   * @param view View of layout
   */
  public void setOutContentLayout(View view) {
    contentOut.addView(view);
  }

  public void showFade() {
    showFade(0);
  }

  public void showFade(long duration) {
    if(verifyAnimationRunning()) {
      showShadow(duration);
      animationHelper.fadeIn(outToolbar, duration);
      showContentSlide(duration);
    }
  }

  public void showSlide() {
    showSlide(0);
  }

  public void showSlide(long duration) {
    if(verifyAnimationRunning()) {
      showShadow(duration);
      animationHelper.slideInTop(outToolbar, duration);
      showContentSlide(duration);
    }
  }

  public void hideSlideBottom() {
    hideSlideBottom(0);
  }

  public void hideSlideBottom(long duration) {
    if(verifyAnimationRunning()) {
      hideShadow(duration);
      animationHelper.slideOutBottom(outToolbar, duration);
      hideContentSlide(duration);
    }
  }

  public void hideSlideTop() {
    hideSlideTop(0);
  }

  public void hideSlideTop(long duration) {
    if(verifyAnimationRunning()) {
      hideShadow(duration);
      animationHelper.slideOutTop(outToolbar, duration);
      hideContentSlide(duration);
    }
  }

  public void hideFade() {
    hideFade(0);
  }

  public void hideFade(long duration) {
    if(verifyAnimationRunning()) {
      hideShadow(duration);
      animationHelper.fadeOut(outToolbar, duration);
      hideContentSlide(duration);
    }
  }

  public void showContentSlide(long duration) {
    animationHelper.slideInBottom(contentOut, duration);
  }

  public void hideContentSlide(long duration) {
    animationHelper.slideOutBottom(contentOut, duration);
  }

  public void hideShadow(long duration) {
    animationHelper.fadeOut(shadow, duration);
  }

  public void showShadow(long duration) {
    animationHelper.fadeIn(shadow, duration);
  }

  /**
   * Realize an smooth slide to an slide offset passed as argument. This method is the base of
   * maximize, minimize and close methods.
   *
   * @return true if the view is slided.
   */
  private boolean smoothSlideTo(View view, int x, int y) {
    if (dragHelper.smoothSlideViewTo(view, x, y)) {
      ViewCompat.postInvalidateOnAnimation(this);
      return true;
    }
    return false;
  }

  /**
   * @return if outToolbar and contentOut is visible
   */
  public boolean isSecondViewAdded() {
    return outToolbar.getVisibility() == VISIBLE && contentOut.getVisibility() == VISIBLE;
  }

  private void setVerticalDragRange(float verticalDragRange) {
    this.verticalDragRange = verticalDragRange;
  }

  public float getVerticalDragRange() {
    return verticalDragRange;
  }

  /**
   * This class verify if animation is running,
   * if it isn't running, set is running and
   * return true
   *
   * @return if animation is running
   */
  public boolean verifyAnimationRunning() {
    if(!isAnimationRunning) {
      isAnimationRunning = true;
      return true;
    } else {
      return false;
    }
  }

  public void setAnimationRunning(Boolean isAnimationRunning) {
    this.isAnimationRunning = isAnimationRunning;
  }

  public Toolbar getInnerToolbar() {
    return innerToolbar;
  }

  public Toolbar getOutToolbar() {
    return outToolbar;
  }

}
