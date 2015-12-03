package com.example.beijingnews;

import com.example.beijingnews.Utils.PrefUtils;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

public class SplashActivity extends Activity {

	/*
	 * �����ĸ�����
	 */
	private RelativeLayout rlRoot;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);

		rlRoot = (RelativeLayout) findViewById(R.id.rl_root);
		startAnimation();
	}

	/*
	 * ��������,��ת������
	 */
	private void startAnimation() {

		AnimationSet set = new AnimationSet(false);

		// ��ת����,����������ĵ�Ϊ���ᣬ��0��ת��360��
		RotateAnimation rotate = new RotateAnimation(0, 360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);

		rotate.setDuration(1000);// ����������ʱ��1s
		rotate.setFillAfter(true);// �Ƕ����ڽ���֮�󱣳ָ�״̬

		// ���Ŷ���
		ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		scale.setDuration(1000);
		scale.setFillAfter(true);

		// ���䶯��
		AlphaAnimation alpha = new AlphaAnimation(0f, 1f);
		alpha.setDuration(1000);
		alpha.setFillAfter(true);

		set.addAnimation(rotate);
		set.addAnimation(scale);
		set.addAnimation(alpha);

		set.setAnimationListener(new AnimationListener() {

			// ������ʼ��ʱ��ص�
			@Override
			public void onAnimationStart(Animation animation) {

			}

			// �����ظ����ŵ�ʱ�����
			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			// ����������ʱ�����,ȥ��ת������������ҳ��
			@Override
			public void onAnimationEnd(Animation animation) {
				/*Intent intent = new Intent(SplashActivity.this,
						GuideActivity.class);
				startActivity(intent);
				finish();*/
				jumpNextPage();
			}
		});

		rlRoot.startAnimation(set);
	}
	
	/*
	 * ��ת��һ��ҳ��
	 */
	private void jumpNextPage(){
		//�ж�֮ǰ��û����ʾ����������
/*		SharedPreferences prefs = getSharedPreferences("config", MODE_PRIVATE);
		boolean userGuide = prefs.getBoolean("is_user_guide_showed", false);*/
		
		//sharedPreference������������ҳ�еİ�ť���֮�󴴽��ģ�������ʱ��ͽ�����ֵ����Ϊ��true
		
		boolean userGuide = PrefUtils.getBoolean(SplashActivity.this, "is_user_guide_showed", false);
		
		if (!userGuide){//֮ǰû��չʾ����������ҳ
			//��ת����������ҳ
			startActivity(new Intent(SplashActivity.this, GuideActivity.class));
		}else {
			startActivity(new Intent(SplashActivity.this, MainActivity.class));
		}
		
		finish();
	}
}
