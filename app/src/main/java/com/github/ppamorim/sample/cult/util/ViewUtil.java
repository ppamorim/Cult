package com.github.ppamorim.sample.cult.util;

import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.github.ppamorim.sample.cult.R;

public class ViewUtil {

  public static void configToolbar(AppCompatActivity appCompatActivity, Toolbar toolbar) {
    if (toolbar == null || appCompatActivity == null) {
      throw new IllegalArgumentException("toolbar or appCompatActivity is null");
    }
    appCompatActivity.setSupportActionBar(toolbar);
    ActionBar actionBar = appCompatActivity.getSupportActionBar();
    if(actionBar == null) {
      return;
    }
    actionBar.setTitle(appCompatActivity.getResources().getString(R.string.app_name));
    actionBar.setHomeButtonEnabled(true);
    actionBar.setDisplayHomeAsUpEnabled(true);
  }

}
