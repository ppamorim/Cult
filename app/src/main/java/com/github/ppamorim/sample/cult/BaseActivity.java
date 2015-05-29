package com.github.ppamorim.sample.cult;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

public class BaseActivity extends ActionBarActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_base);
  }

}
