package com.github.ppamorim.sample.cult.util;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by Pedro Paulo on 5/30/2015.
 */
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
    actionBar.setTitle("");
    actionBar.setHomeButtonEnabled(true);
    actionBar.setDisplayHomeAsUpEnabled(true);
  }

}
