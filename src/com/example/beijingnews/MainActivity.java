package com.example.beijingnews;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.beijingnews.Fragment.ContentFragment;
import com.example.beijingnews.Fragment.LeftMenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity {

	private static final String FRAGMENT_LEFT_MENU = "fragment_left_menu";
	private static final String FRAGMENT_CONTENT_MENU = "fragment_content_menu";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		// 设置侧边栏布局
		setBehindContentView(R.layout.activity_left_menu);
		SlidingMenu slidingMenu = getSlidingMenu();
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		slidingMenu.setMode(SlidingMenu.LEFT);
		slidingMenu.setBehindOffset(200);//设置预留屏幕的宽度是200像素

	}
	
	/*
	 * 初始化fragment,将fragment数据填充给布局文件
	 */
	private void initFragment(){
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();//开启事务
		
		transaction.replace(R.id.fl_left_menu, new LeftMenuFragment(),FRAGMENT_LEFT_MENU);
		transaction.replace(R.id.fl_content_menu, new ContentFragment(), FRAGMENT_CONTENT_MENU);
		
		transaction.commit();
	}
}
