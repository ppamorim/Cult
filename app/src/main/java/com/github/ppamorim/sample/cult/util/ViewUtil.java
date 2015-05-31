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
package com.github.ppamorim.sample.cult.util;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.github.ppamorim.sample.cult.R;

/**
 * This class is a helper to configure
 * some view on your application
 *
 * @author Pedro Paulo Amorim
 *
 */
public class ViewUtil {

  /**
   *
   * @param appCompatActivity provide the instance of activity
   * @param toolbar provide the isntance of the base toolbar of application (can be CultView)
   */
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
