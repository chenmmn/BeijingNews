package com.example.beijingnews;

import java.util.ArrayList;


import com.example.beijingnews.Utils.PrefUtils;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class GuideActivity extends Activity {

	private static final int[] mImageIds = new int[] { R.drawable.guide_1,
			R.drawable.guide_2, R.drawable.guide_3 };

	private ViewPager vpGuide;

	/*
	 * 小圆点的那个布局，父控件，实现动态的添加，因为如果有三个滑动也，我们就创建三个，如果有四个，就创建四个
	 */
	private LinearLayout mPointLayout;

	private ArrayList<ImageView> mImageViewList;

	private Button mStartButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_guide);

		vpGuide = (ViewPager) findViewById(R.id.vp_guide);
		mPointLayout = (LinearLayout) findViewById(R.id.ll_point_group);
		mStartButton = (Button) findViewById(R.id.bt_start);

		initViews();
		Log.e("TAG", vpGuide + "");
		vpGuide.setAdapter(new GuideAdapter());
		vpGuide.setOnPageChangeListener(new PageChange());
		updateDot();

		mStartButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
//				SharedPreferences prefs = getSharedPreferences("config", MODE_PRIVATE);
//				prefs.edit().putBoolean("is_user_guide_showed", true).commit();
				PrefUtils.setBoolean(GuideActivity.this, "is_user_guide_showed", true);
				
				// 跳转到主页面
				Intent intent = new Intent(GuideActivity.this,
						MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}

	/*
	 * 初始化界面
	 */
	private void initViews() {
		mImageViewList = new ArrayList<ImageView>();

		// 初始化引导页的3页面
		for (int i = 0; i < mImageIds.length; i++) {
			ImageView image = new ImageView(this);
			image.setBackgroundResource(mImageIds[i]);
			mImageViewList.add(image);
		}

		// 初始化引导页的小圆点
		for (int i = 0; i < mImageIds.length; i++) {
			View point = new View(this);
			// point.setBackgroundResource(R.drawable.shape_point_gray);// 默认的原点

			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					10, 10);// 这里的10是像素

			if (i > 0) {// 为原点设置间距，从第二个点开始
				params.leftMargin = 10;
			}

			// 动态设置point的尺寸,原点的父布局是LinearLayout,所以我们用Linearlayout的layoutparams参数来给控件设置尺寸
			point.setLayoutParams(params);
			point.setBackgroundResource(R.drawable.shape_dot_selector);
			// 将原点添加个线性布局
			mPointLayout.addView(point);

			/*
			 * measure(测量大小) layout(界面位置) onDraw(画出具体的形)
			 * 但是这三个方法都是在onCreate方法之后被执行的
			 */

			Log.e("TAG", mPointLayout + "");
			/*
			 * 获取视图树，对layout方法进行监听
			 * 咱们的布局文件，就像一颗视图树，最顶层是Relativelayout,下面是各种layout或者view
			 */
			mPointLayout.getViewTreeObserver().addOnGlobalLayoutListener(
					new OnGlobalLayoutListener() {

						// 当layout方法执行完成之后会回调此方法
						@Override
						public void onGlobalLayout() {
							// 监听一次就好(底层有多个布局，这个方法会被调用多次)，然后取消监听，避免该方法被重复执行
							System.out.println("layout 结束");
							// 取消监听
							mPointLayout.getViewTreeObserver()
									.removeGlobalOnLayoutListener(this);
							/*
							 * 这是最新的方法，但是要求api 16才能使用
							 * mPointLayout.getViewTreeObserver
							 * ().removeOnGlobalLayoutListener(this);
							 */

							// 第二个圆点左边的x坐标 - 第一个圆点左边的x坐标，就的两个圆点之间了距离啰
							int width = mPointLayout.getChildAt(1).getLeft()
									- mPointLayout.getChildAt(0).getLeft();
							System.out.println("圆的之间的距离：" + width);
						}
					});

		}
	}

	class GuideAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return mImageIds.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {

			container.addView(mImageViewList.get(position));
			return mImageViewList.get(position);
		}

	}

	class PageChange implements OnPageChangeListener {

		// 滑动事件
		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			System.out.println("当前位置：" + position + ",百分比" + positionOffset
					+ ";移动距离" + positionOffsetPixels);
		}

		@Override
		public void onPageSelected(int position) {
			updateDot();

			if (position == mImageIds.length - 1) {
				mStartButton.setVisibility(View.VISIBLE);
			} else {
				mStartButton.setVisibility(View.INVISIBLE);
			}
		}

		@Override
		public void onPageScrollStateChanged(int state) {
		}

	}

	private void updateDot() {
		int currentPage = vpGuide.getCurrentItem();
		for (int i = 0; i < mPointLayout.getChildCount(); i++) {
			mPointLayout.getChildAt(i).setEnabled(i == currentPage);
		}
	}
}
