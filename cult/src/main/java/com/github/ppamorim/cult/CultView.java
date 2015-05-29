package com.github.ppamorim.cult;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.dd.ShadowLayout;
import com.github.ppamorim.cult.util.ViewUtil;

public class CultView extends FrameLayout {

  private int imagePadding;

  private ShadowLayout shadowLayout;
  private RelativeLayout innerView;
  private ImageView searchIcon;

  public CultView(Context context) {
    this(context, null);
  }

  public CultView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public CultView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    config();
    if(attrs != null && defStyleAttr != 0) {
      getStyle(context, attrs, defStyleAttr);
    }
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    if(imagePadding != 0) {
      configSearchButton();
    }
  }

  private CultView config() {
    addView(inflate(getContext(), R.layout.layout_cult, null));
    shadowLayout = (ShadowLayout) findViewById(R.id.shadow_layout);
    innerView = (RelativeLayout) findViewById(R.id.inner_view);
    searchIcon = (ImageView) findViewById(R.id.search_icon);
    return this;
  }

  public CultView setBorderRadius(int radius) {
    //shadowLayout.set
    return this;
  }

  public CultView setInnerPadding(int left, int top, int right, int bottom) {
    shadowLayout.setPadding(
        ViewUtil.dpToPx(getResources(), left),
        ViewUtil.dpToPx(getResources(), top),
        ViewUtil.dpToPx(getResources(), right),
        ViewUtil.dpToPx(getResources(), bottom));
    return this;
  }

  public CultView configSearchButton() {
    searchIcon.setPadding(imagePadding, imagePadding, imagePadding, imagePadding);
    return this;
  }

  private void getStyle(Context context, AttributeSet attrs, int defStyleAttr) {
    TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.cult_view, defStyleAttr, 0);
    imagePadding = a.getInt(R.styleable.cult_view_search_padding, 100);
    a.recycle();
  }

}
