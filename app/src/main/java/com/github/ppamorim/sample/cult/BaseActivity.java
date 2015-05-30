package com.github.ppamorim.sample.cult;

import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.github.ppamorim.cult.CultView;

public class BaseActivity extends AppCompatActivity {

  @InjectView(R.id.cult_view) CultView cultView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_base);
    ButterKnife.inject(this);
  }

  @Override protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    new Handler().postDelayed(new Runnable() {
      @Override public void run() {
        cultView.showSecondView();
      }
    }, 1000);
  }

  @Override public void onBackPressed() {
    if(cultView.isSecondViewAdded()) {
      cultView.hideSecondView();
      return;
    }
    super.onBackPressed();
  }
}
