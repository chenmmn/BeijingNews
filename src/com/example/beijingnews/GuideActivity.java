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
	 * СԲ����Ǹ����֣����ؼ���ʵ�ֶ�̬����ӣ���Ϊ�������������Ҳ�����Ǿʹ���������������ĸ����ʹ����ĸ�
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
				
				// ��ת����ҳ��
				Intent intent = new Intent(GuideActivity.this,
						MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}

	/*
	 * ��ʼ������
	 */
	private void initViews() {
		mImageViewList = new ArrayList<ImageView>();

		// ��ʼ������ҳ��3ҳ��
		for (int i = 0; i < mImageIds.length; i++) {
			ImageView image = new ImageView(this);
			image.setBackgroundResource(mImageIds[i]);
			mImageViewList.add(image);
		}

		// ��ʼ������ҳ��СԲ��
		for (int i = 0; i < mImageIds.length; i++) {
			View point = new View(this);
			// point.setBackgroundResource(R.drawable.shape_point_gray);// Ĭ�ϵ�ԭ��

			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					10, 10);// �����10������

			if (i > 0) {// Ϊԭ�����ü�࣬�ӵڶ����㿪ʼ
				params.leftMargin = 10;
			}

			// ��̬����point�ĳߴ�,ԭ��ĸ�������LinearLayout,����������Linearlayout��layoutparams���������ؼ����óߴ�
			point.setLayoutParams(params);
			point.setBackgroundResource(R.drawable.shape_dot_selector);
			// ��ԭ����Ӹ����Բ���
			mPointLayout.addView(point);

			/*
			 * measure(������С) layout(����λ��) onDraw(�����������)
			 * ��������������������onCreate����֮��ִ�е�
			 */

			Log.e("TAG", mPointLayout + "");
			/*
			 * ��ȡ��ͼ������layout�������м���
			 * ���ǵĲ����ļ�������һ����ͼ���������Relativelayout,�����Ǹ���layout����view
			 */
			mPointLayout.getViewTreeObserver().addOnGlobalLayoutListener(
					new OnGlobalLayoutListener() {

						// ��layout����ִ�����֮���ص��˷���
						@Override
						public void onGlobalLayout() {
							// ����һ�ξͺ�(�ײ��ж�����֣���������ᱻ���ö��)��Ȼ��ȡ������������÷������ظ�ִ��
							System.out.println("layout ����");
							// ȡ������
							mPointLayout.getViewTreeObserver()
									.removeGlobalOnLayoutListener(this);
							/*
							 * �������µķ���������Ҫ��api 16����ʹ��
							 * mPointLayout.getViewTreeObserver
							 * ().removeOnGlobalLayoutListener(this);
							 */

							// �ڶ���Բ����ߵ�x���� - ��һ��Բ����ߵ�x���꣬�͵�����Բ��֮���˾��놪
							int width = mPointLayout.getChildAt(1).getLeft()
									- mPointLayout.getChildAt(0).getLeft();
							System.out.println("Բ��֮��ľ��룺" + width);
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

		// �����¼�
		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			System.out.println("��ǰλ�ã�" + position + ",�ٷֱ�" + positionOffset
					+ ";�ƶ�����" + positionOffsetPixels);
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
