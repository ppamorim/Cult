package com.github.ppamorim.sample.cult.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.github.ppamorim.cult.CultView;
import com.github.ppamorim.sample.cult.R;
import com.github.ppamorim.sample.cult.fragment.CultFragment;
import com.github.ppamorim.sample.cult.util.NavigationDrawerUtil;
import com.github.ppamorim.sample.cult.util.ViewUtil;
import com.github.ppamorim.sample.cult.view.SearchView;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class BaseActivity extends AppCompatActivity {

  private ActionBarDrawerToggle mDrawerToggle;
  private SearchView searchView;
  private FragmentPagerItemAdapter adapter;

  @InjectView(R.id.cult_view) CultView cultView;
  @InjectView(R.id.drawer_left) DrawerLayout drawerLayout;
  @InjectView(R.id.smart_tab_layout) SmartTabLayout smartTabLayout;
  @InjectView(R.id.view_pager) ViewPager viewPager;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_base);
    ButterKnife.inject(this);
  }

  @Override protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    ViewUtil.configToolbar(this, cultView.getInnerToolbar());
    mDrawerToggle = NavigationDrawerUtil
        .configNavigationDrawer(this, drawerLayout, null);
    initializeViewPager();
    configCultView();
  }

  @Override protected void onResume() {
    super.onResume();
    mDrawerToggle.syncState();
  }

  @Override public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    mDrawerToggle.onConfigurationChanged(newConfig);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.search, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        return mDrawerToggle.onOptionsItemSelected(item);
      case R.id.action_search:
        cultView.showFade();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override public void onBackPressed() {
    if(cultView.isSecondViewAdded()) {
      cultView.hideFade();
      return;
    }
    super.onBackPressed();
  }

  private void initializeViewPager() {
    adapter = new FragmentPagerItemAdapter(
        getSupportFragmentManager(), FragmentPagerItems.with(this)
        .add(R.string.home, CultFragment.class)
        .add(R.string.more, CultFragment.class)
        .create());
    viewPager.setAdapter(adapter);
    smartTabLayout.setViewPager(viewPager);
  }

  private void configCultView() {
    searchView = new SearchView();
    cultView.setOutToolbarLayout(searchView.getView(
        LayoutInflater.from(this).inflate(R.layout.layout_search, null), searchViewCallback));
    cultView.setOutContentLayout(R.layout.fragment_cult);
  }

  private SearchView.SearchViewCallback searchViewCallback =
      new SearchView.SearchViewCallback() {
    @Override public void onCancelClick() {
      cultView.hideFade();
    }
  };

}
