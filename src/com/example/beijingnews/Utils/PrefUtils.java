package com.example.beijingnews.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author acer ¶ÔSharedPreference·â×°
 */
public class PrefUtils {

	public static final String PREF_NAME = "config";

	public static boolean getBoolean(Context context, String key,
			boolean defaultValue) {
		SharedPreferences prefs = context.getSharedPreferences(PREF_NAME,
				Context.MODE_PRIVATE);

		return prefs.getBoolean(key, defaultValue);
	}

	public static void setBoolean(Context context, String key,
			boolean value) {
		SharedPreferences prefs = context.getSharedPreferences(PREF_NAME,
				context.MODE_PRIVATE);
		
		prefs.edit().putBoolean(key, value).commit();
	}
}
