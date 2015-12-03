package com.example.beijingnews.Fragment;

import com.example.beijingnews.R;

import android.view.LayoutInflater;
import android.view.View;

public class LeftMenuFragment extends BaseFragment {

	@Override
	public View initViews() {
		View view = LayoutInflater.from(mActivity).inflate(
				R.layout.fragment_content_menu, null);
		return view;
	}

}
