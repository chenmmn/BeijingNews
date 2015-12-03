package com.example.beijingnews.Fragment;

import com.example.beijingnews.R;

import android.view.LayoutInflater;
import android.view.View;

/**
 * @author acer
 * 
 */
public class ContentFragment extends BaseFragment {

	@Override
	public View initViews() {

		View view = LayoutInflater.from(mActivity).inflate(R.layout.fragment_left_menu,
				null);
		return view;
	}

}
