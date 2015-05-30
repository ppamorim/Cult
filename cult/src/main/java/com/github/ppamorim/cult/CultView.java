package com.github.ppamorim.cult;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.dd.ShadowLayout;
import com.github.ppamorim.cult.util.SlideHelper;
import com.github.ppamorim.cult.util.ViewUtil;

public class CultView extends FrameLayout {

  private boolean isAnimationRunning;

  private int toolbarHeight;

  private ShadowLayout shadowLayout;
  private FrameLayout innerView;
  private Toolbar innerToolbar;
  private Toolbar outToolbar;
  private FrameLayout content;

  private SlideHelper slideHelper;

  public CultView(Context context) {
    this(context, null);
  }

  public CultView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public CultView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    config();
    if(attrs != null) {
      getStyle(context, attrs, defStyleAttr);
    }
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    configSizes();
    configSlideHelper();
  }

  private CultView config() {
    addView(inflate(getContext(), R.layout.layout_cult, null));
    shadowLayout = (ShadowLayout) findViewById(R.id.shadow_layout);
    innerView = (FrameLayout) findViewById(R.id.inner_view);
    innerToolbar = (Toolbar) findViewById(R.id.inner_toolbar);
    outToolbar = (Toolbar) findViewById(R.id.out_toolbar);
    content = (FrameLayout) findViewById(R.id.content);
    return this;
  }

  public void configSlideHelper() {
    if(slideHelper == null) {
      slideHelper = new SlideHelper(getContext(), this);
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
    a.recycle();
  }

  public CultView setInnerPadding(int left, int top, int right, int bottom) {
    shadowLayout.setPadding(ViewUtil.dpToPx(getResources(), left),
        ViewUtil.dpToPx(getResources(), top), ViewUtil.dpToPx(getResources(), right),
        ViewUtil.dpToPx(getResources(), bottom));
    return this;
  }

  public void showSecondView() {
    slideHelper.slideInTop(outToolbar);
    slideHelper.slideInBottom(content);
  }

  public void hideSecondView() {
    slideHelper.slideOutTop(outToolbar);
    slideHelper.slideOutBottom(content);
  }

  public boolean isSecondViewAdded() {
    return outToolbar.getVisibility() == VISIBLE && content.getVisibility() == VISIBLE;
  }

  public void resetAnimation() {
    isAnimationRunning = false;
  }

}
