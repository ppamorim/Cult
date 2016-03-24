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
package com.github.ppamorim.sample.cult.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import com.github.ppamorim.sample.cult.R;

/**
 * This is the base activity of the application, here
 * are injected the view and configured the drawerLayout
 *
 * @author Pedro Paulo Amorim
 *
 */
public class BaseActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_base);
    ButterKnife.bind(this);
  }

}
//
//  private SearchView searchView;
//  private FragmentPagerItemAdapter adapter;
//
//  @InjectView(R.id.cult_view) CultView cultView;
//  @Optional @InjectView(R.id.drawer_left) DrawerLayout drawerLayout;
//  @Optional @InjectView(R.id.smart_tab_layout) SmartTabLayout smartTabLayout;
//  @Optional @InjectView(R.id.view_pager) ViewPager viewPager;
//
//  @Override protected void onCreate(Bundle savedInstanceState) {
//    super.onCreate(savedInstanceState);
//    setContentView(R.layout.activity_base);
//    ButterKnife.inject(this);
//  }
//
//  @Override protected void onPostCreate(Bundle savedInstanceState) {
//    super.onPostCreate(savedInstanceState);
//    ViewUtil.configToolbar(this, cultView.getInnerToolbar());
//    mDrawerToggle = NavigationDrawerUtil
//        .configNavigationDrawer(this, drawerLayout, null);
//    initializeViewPager();
//    configCultView();
//  }
//
//  @Override protected void onResume() {
//    super.onResume();
//    if (mDrawerToggle != null) {
//      mDrawerToggle.syncState();
//    }
//  }
//
//  @Override public void onConfigurationChanged(Configuration newConfig) {
//    super.onConfigurationChanged(newConfig);
//    if (mDrawerToggle != null) {
//      mDrawerToggle.onConfigurationChanged(newConfig);
//    }
//  }
//
//  @Override public boolean onCreateOptionsMenu(Menu menu) {
//    getMenuInflater().inflate(R.menu.search, menu);
//    return true;
//  }
//
//  @Override public boolean onOptionsItemSelected(MenuItem item) {
//    switch (item.getItemId()) {
//      case android.R.id.home:
//        return mDrawerToggle != null && mDrawerToggle.onOptionsItemSelected(item);
//      case R.id.action_search:
//        cultView.showSlide();
//        return true;
//      default:
//        return super.onOptionsItemSelected(item);
//    }
//  }
//
//  @Override public void onBackPressed() {
//    if (cultView.isSecondViewAdded()) {
//      cultView.hideSlideTop();
//      return;
//    }
//    super.onBackPressed();
//  }
//
//  @Override protected void onSaveInstanceState(Bundle outState) {
//    super.onSaveInstanceState(outState);
//  }
//
//  private void initializeViewPager() {
//    adapter = new FragmentPagerItemAdapter(
//        getSupportFragmentManager(), FragmentPagerItems.with(this)
//        .add(R.string.home, CultFragment.class)
//        .add(R.string.more, CultFragment.class)
//        .create());
//    if (viewPager == null || smartTabLayout == null) {
//      return;
//    }
//    viewPager.setAdapter(adapter);
//    smartTabLayout.setViewPager(viewPager);
//  }
//
//  private void configCultView() {
//    searchView = new SearchView();
//    cultView.setOutToolbarLayout(searchView.getView(
//        LayoutInflater.from(this).inflate(R.layout.layout_search, null), searchViewCallback));
//    cultView.setOutContentLayout(R.layout.fragment_cult);
//  }
//
//  private SearchView.SearchViewCallback searchViewCallback =
//      new SearchView.SearchViewCallback() {
//    @Override public void onCancelClick() {
//      hideKeyboard();
//      onBackPressed();
//    }
//  };
//
//  private void hideKeyboard() {
//    runOnUiThread(new Runnable() {
//      @Override public void run() {
//        if (getCurrentFocus() != null) {
//          ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
//              .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
//                  InputMethodManager.HIDE_NOT_ALWAYS);
//        }
//      }
//    });
//  }
//
//}
