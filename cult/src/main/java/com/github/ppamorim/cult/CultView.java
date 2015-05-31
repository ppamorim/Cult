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

  public void configDragViewHelper() {
    cultHelperCallback = new CultHelperCallback(this, contentOut);
    dragHelper = ViewDragHelper.create(this, SENSITIVITY, cultHelperCallback);
  }

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

  public void configSlideHelper() {
    if(animationHelper == null) {
      animationHelper = new AnimationHelper(getContext(), this);
    }
  }

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

  private void getStyle(Context context, AttributeSet attrs, int defStyleAttr) {
    if(attrs != null) {
      TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.cult_view, defStyleAttr, 0);
      toolbarHeight = (int) a.getDimension(R.styleable.cult_view_toolbar_height, DEFAULT_HEIGHT);
      innerPadding = (int) a.getDimension(R.styleable.cult_view_inner_padding, DEFAULT_PADDING);
      contentResId = a.getResourceId(R.styleable.cult_view_content_view, 0);
      a.recycle();
    }
  }

  public CultView setInnerPadding(int left, int top, int right, int bottom) {
    shadowLayout.setPadding(ViewUtil.dpToPx(getResources(), left),
        ViewUtil.dpToPx(getResources(), top), ViewUtil.dpToPx(getResources(), right),
        ViewUtil.dpToPx(getResources(), bottom));
    return this;
  }

  public void setOutToolbarLayout(int resourceId) {
    outToolbar.addView(inflate(getContext(), resourceId, null));
  }

  public void setOutToolbarLayout(View view) {
    outToolbar.addView(view);
  }

  public void setOutContentLayout(int resourceId) {
    contentOut.addView(inflate(getContext(), resourceId, null));
  }

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

  public void hideSlide() {
    hideSlide(0);
  }

  public void hideSlide(long duration) {
    if(verifyAnimationRunning()) {
      hideShadow(duration);
      animationHelper.slideOutBottom(outToolbar, duration);
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

  private boolean smoothSlideTo(View view, int x, int y) {
    if (dragHelper.smoothSlideViewTo(view, x, y)) {
      ViewCompat.postInvalidateOnAnimation(this);
      return true;
    }
    return false;
  }

  public boolean isSecondViewAdded() {
    return outToolbar.getVisibility() == VISIBLE && contentOut.getVisibility() == VISIBLE;
  }

  private void setVerticalDragRange(float verticalDragRange) {
    this.verticalDragRange = verticalDragRange;
  }

  public float getVerticalDragRange() {
    return verticalDragRange;
  }

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
