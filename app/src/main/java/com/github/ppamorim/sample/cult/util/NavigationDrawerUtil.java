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

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import com.github.ppamorim.sample.cult.R;

/**
 * This class is a helper for to
 * create a instance of navigationDrawer
 *
 * @author Pedro Paulo Amorim
 *
 */
public class NavigationDrawerUtil {

  /**
   *
   * @param appCompatActivity provide the instance of activity
   * @param drawerLayout provide the instance of drawerLayout
   * that's inflated at the activity
   * @param drawerCallback provide the callback of drawerLayout actions
   * @return a new instance of ActionBarDrawerToggle based on the params
   */
  public static ActionBarDrawerToggle configNavigationDrawer(
      AppCompatActivity appCompatActivity,
      DrawerLayout drawerLayout,
      final DrawerCallback drawerCallback) {

    if (appCompatActivity == null || drawerLayout == null) {
      return null;
    }

    drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, Gravity.LEFT);
    ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
        appCompatActivity, drawerLayout, R.string.app_name, R.string.app_name) {

      @Override public void onDrawerOpened(View drawerView) {
        super.onDrawerOpened(drawerView);
        if (drawerCallback != null) {
          drawerCallback.onDrawerOpened();
        }
      }

      @Override public void onDrawerClosed(View drawerView) {
        super.onDrawerClosed(drawerView);
        if (drawerCallback != null) {
          drawerCallback.onDrawerClosed();
        }
      }

      @Override public void onDrawerSlide(View drawerView, float slideOffset) {
        super.onDrawerSlide(drawerView, slideOffset);
        if (drawerCallback != null) {
          drawerCallback.onDrawerSlide(drawerView, slideOffset);
        }
      }
    };
    drawerLayout.setDrawerListener(actionBarDrawerToggle);
    return actionBarDrawerToggle;
  }

  public interface DrawerCallback {
    void onDrawerOpened();
    void onDrawerClosed();
    void onDrawerSlide(View drawerView, float slideOffset);
  }

}
