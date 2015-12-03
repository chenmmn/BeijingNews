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
	 * 动画的根布局
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
	 * 开启动画,旋转，缩放
	 */
	private void startAnimation() {

		AnimationSet set = new AnimationSet(false);

		// 旋转动画,以自身的中心点为枢轴，从0旋转到360度
		RotateAnimation rotate = new RotateAnimation(0, 360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);

		rotate.setDuration(1000);// 动画持续的时间1s
		rotate.setFillAfter(true);// 是动画在结束之后保持该状态

		// 缩放动画
		ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		scale.setDuration(1000);
		scale.setFillAfter(true);

		// 渐变动画
		AlphaAnimation alpha = new AlphaAnimation(0f, 1f);
		alpha.setDuration(1000);
		alpha.setFillAfter(true);

		set.addAnimation(rotate);
		set.addAnimation(scale);
		set.addAnimation(alpha);

		set.setAnimationListener(new AnimationListener() {

			// 动画开始的时候回调
			@Override
			public void onAnimationStart(Animation animation) {

			}

			// 动画重复播放的时候调用
			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			// 动画结束的时候调用,去跳转到新手引导的页面
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
	 * 跳转下一个页面
	 */
	private void jumpNextPage(){
		//判断之前有没有显示过新手引导
/*		SharedPreferences prefs = getSharedPreferences("config", MODE_PRIVATE);
		boolean userGuide = prefs.getBoolean("is_user_guide_showed", false);*/
		
		//sharedPreference是在新手引导页中的按钮点击之后创建的，创建的时候就将变量值设置为了true
		
		boolean userGuide = PrefUtils.getBoolean(SplashActivity.this, "is_user_guide_showed", false);
		
		if (!userGuide){//之前没有展示过新手引导页
			//跳转到新手引导页
			startActivity(new Intent(SplashActivity.this, GuideActivity.class));
		}else {
			startActivity(new Intent(SplashActivity.this, MainActivity.class));
		}
		
		finish();
	}
}
