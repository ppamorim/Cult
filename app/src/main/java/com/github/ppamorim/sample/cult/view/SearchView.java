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
package com.github.ppamorim.sample.cult.view;

import android.view.View;
import android.widget.EditText;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.github.ppamorim.sample.cult.R;

/**
 * This view is a ViewHolder of the SearchView
 *
 * @author Pedro Paulo Amorim
 *
 */
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

  /**
   * @return text of the edittext
   */
  public String getText() {
    return searchEditText.getText().toString();
  }

  public interface SearchViewCallback {
    void onCancelClick();
  }

}
