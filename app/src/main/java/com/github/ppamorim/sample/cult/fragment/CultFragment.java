package com.github.ppamorim.sample.cult.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import android.support.v4.app.Fragment;
import com.github.ppamorim.sample.cult.R;

public class CultFragment extends Fragment {

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_cult, container, false);
    ButterKnife.inject(this, view);
    return view;
  }

}
