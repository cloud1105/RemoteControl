package com.foxconn.remote.control.utils;

import com.foxconn.remote.control.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferencesUtils {
	
	/**
	 * 主题
	 */
	public static final String KEY_THEME = "theme";
	
	/**
	 * 是否第一次進入應用
	 */
	public static final String IS_USED_BEFORE_TAG = "usedTag";
	
	/**
	 * SharedPreferences
	 */
	private static SharedPreferences.Editor mEditor = null;
	private static SharedPreferences mdPreferences = null;
	
	/** 得到写配置文件用的Editor **/
	private static SharedPreferences.Editor getEditor(Context paramContext) {
		if (mEditor == null)
			mEditor = PreferenceManager.getDefaultSharedPreferences(
					paramContext).edit();
		return mEditor;
	}

	/** 读配置文件 **/
	private static SharedPreferences getSharedPreferences(Context paramContext) {
		if (mdPreferences == null)
			mdPreferences = PreferenceManager
					.getDefaultSharedPreferences(paramContext);
		return mdPreferences;
	}
	
	public static boolean getUsedTag(Context context){
		return getSharedPreferences(context).getBoolean(IS_USED_BEFORE_TAG,
				false);
	}
	
	public static void setUsedTag(Context context, boolean hasUsed){
		getEditor(context).putBoolean(IS_USED_BEFORE_TAG, hasUsed);
	}
	

	/** 得到配置文件中的主題 **/
	public static int getTheme(Context context) {
		return getSharedPreferences(context).getInt(KEY_THEME,
				R.style.AppBaseTheme);
	}

	/** 設置主題并寫入配置文件 **/
	public static void setTheme(Context context, int theme) {
		getEditor(context).putInt(KEY_THEME, theme).commit();
	}
}
