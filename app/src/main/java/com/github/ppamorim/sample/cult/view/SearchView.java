package com.github.ppamorim.sample.cult.view;

import android.view.View;
import android.widget.EditText;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.github.ppamorim.sample.cult.R;

public class SearchView {

  private SearchViewCallback searchViewCallback;

  @OnClick(R.id.cancel) void onClickCancel() {
    if(searchViewCallback != null) {
      searchViewCallback.onCancelClick();
    }
  }

  @InjectView(R.id.search_edit_text) EditText searchEditText;

  public View getView(View view, SearchViewCallback searchViewCallback) {
    ButterKnife.inject(this, view);
    this.searchViewCallback = searchViewCallback;
    return view;
  }

  public String getText() {
    return searchEditText.getText().toString();
  }

  public interface SearchViewCallback {
    void onCancelClick();
  }

}
