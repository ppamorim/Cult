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
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.ViewGroup;

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
public class CultView extends CardView {

  private Toolbar toolbar;

  public CultView(Context context) {
    this(context, null);
  }

  public CultView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public CultView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initializeToolbar(context);
    getStyle(context, attrs, defStyleAttr);
  }

  /**
   * Override method to map some views and to configure the
   * view height, animation and to initialize DragViewHelper.
   */
  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    if (!isInEditMode()) {

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
  }

  /**
   * Perform the save of the instance state of some params that's used at dragView.
   * @return Parcelable
   */
  @Override public Parcelable onSaveInstanceState() {
    Parcelable superState = super.onSaveInstanceState();
    SavedState ss = new SavedState(superState);
    ss.toolbarTitle = this.toolbar.getTitle().toString();
    return ss;
  }

  /**
   * Called when the view is restored
   * @param state Return the state
   */
  @Override public void onRestoreInstanceState(Parcelable state) {
    SavedState ss = (SavedState) state;
    super.onRestoreInstanceState(ss.getSuperState());
    this.toolbar.setTitle(ss.toolbarTitle);
  }


  /**
   * Initialize some attributes to provide
   * the height, padding and content to CultView
   */
  private void getStyle(Context context, AttributeSet attrs, int defStyleAttr) {
    if (attrs != null) {
      TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.cult_view, defStyleAttr, 0);
      toolbar.setTitle(a.getString(R.styleable.cult_view_cult_title));
      a.recycle();
    }
  }

  private void initializeToolbar(Context context) {
    if (toolbar == null) {
      toolbar = new Toolbar(context);
      ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
          ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
      addView(toolbar, params);
    }
  }

  private CultView setCultColor(int color) {
    toolbar.setBackgroundColor(color);
    return this;
  }

  private CultView setCultTitle(int titleId) {
    this.setCultTitle(getContext().getResources().getText(titleId).toString());
    return this;
  }

  private CultView setCultTitle(String title) {
    toolbar.setTitle(title);
    return this;
  }

  private static class SavedState extends BaseSavedState {

    private String toolbarTitle;

    SavedState(Parcelable superState) {
      super(superState);
    }

    private SavedState(Parcel in) {
      super(in);
      this.toolbarTitle = in.readString();
    }

    @Override public void writeToParcel(Parcel out, int flags) {
      super.writeToParcel(out, flags);
      out.writeString(toolbarTitle);
    }

    public static final Parcelable.Creator<SavedState> CREATOR
        = new Parcelable.Creator<SavedState>() {
      public SavedState createFromParcel(Parcel in) {
        return new SavedState(in);
      }

      public SavedState[] newArray(int size) {
        return new SavedState[size];
      }
    };
  }

}
