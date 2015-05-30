package com.github.ppamorim.cult;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.dd.ShadowLayout;
import com.github.ppamorim.cult.util.AnimationHelper;
import com.github.ppamorim.cult.util.ViewUtil;

public class CultView extends RelativeLayout {

  private boolean isAnimationRunning;

  private int toolbarHeight;
  private int contentResId;

  private ShadowLayout shadowLayout;
  private FrameLayout innerView;
  private Toolbar innerToolbar;
  private FrameLayout contentInner;
  private Toolbar outToolbar;
  private FrameLayout contentOut;
  private View shadow;

  private AnimationHelper animationHelper;

  public CultView(Context context) {
    this(context, null);
  }

  public CultView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public CultView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    if(attrs != null) {
      getStyle(context, attrs, defStyleAttr);
    }
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    config();
    configSizes();
    configSlideHelper();
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
    contentInner.addView(inflate(getContext(), contentResId, null));
    return this;
  }

  public void configSlideHelper() {
    if(animationHelper == null) {
      animationHelper = new AnimationHelper(getContext(), this);
    }
  }

  private void configSizes() {
    RelativeLayout.LayoutParams layoutParams =
        new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, toolbarHeight);
    shadowLayout.setLayoutParams(layoutParams);
    outToolbar.setLayoutParams(layoutParams);
  }

  private void getStyle(Context context, AttributeSet attrs, int defStyleAttr) {
    TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.cult_view, defStyleAttr, 0);
    toolbarHeight = (int) a.getDimension(R.styleable.cult_view_toolbar_height, 100);
    contentResId = a.getResourceId(R.styleable.cult_view_content_view, 0);
    a.recycle();
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

  public boolean isSecondViewAdded() {
    return outToolbar.getVisibility() == VISIBLE && contentOut.getVisibility() == VISIBLE;
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
