package com.foxconn.remote.control.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockFragment;
import com.foxconn.remote.control.R;

/**
 * @fileName BaseFragment.java
 * @package net.iaround.android
 * @description Fragment基类
 * @author 任东卫
 * @email 86930007@qq.com
 * @version 1.0
 */
public abstract class BaseFragment extends SherlockFragment {
	/** Appliction基类对象 **/
	protected BaseApplication mApplication;
	private final static String TAG = BaseFragment.class.getSimpleName();
	/** Acitivity对象 **/
	protected Activity mActivity;
	/** 上下文 **/
	protected Context mContext;
	/** 当前显示的内容 **/
	protected View mView;
	
	public BaseFragment(){};

	public BaseFragment(BaseApplication application, Activity activity,
			Context context) {
		mContext = context;
	}

	/** 通过ID绑定UI **/
	protected View findViewById(int id) {
		return mView.findViewById(id);
	}

	/** 短暂显示Toast提示(来自res) **/
	protected void showShortToast(int resId) {
		Toast.makeText(mContext, getString(resId), Toast.LENGTH_SHORT).show();
	}

	/** 短暂显示Toast提示(来自String) **/
	protected void showShortToast(String text) {
		Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
	}

	/** 长时间显示Toast提示(来自res) **/
	protected void showLongToast(int resId) {
		Toast.makeText(mContext, getString(resId), Toast.LENGTH_LONG).show();
	}

	/** 长时间显示Toast提示(来自String) **/
	protected void showLongToast(String text) {
		Toast.makeText(mContext, text, Toast.LENGTH_LONG).show();
	}

	/** Debug输出Log日志 **/
	protected void showLogDebug(String tag, String msg) {
		Log.d(tag, msg);
	}

	/** Error输出Log日志 **/
	protected void showLogError(String tag, String msg) {
		Log.e(tag, msg);
	}

	/** 通过Class跳转界面 **/
	protected void startActivity(Class<?> cls) {
		startActivity(cls, null);
	}

	/** 含有Bundle通过Class跳转界面 **/
	protected void startActivity(Class<?> cls, Bundle bundle) {
		Intent intent = new Intent();
		intent.setClass(mContext, cls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		if (intent.resolveActivity(mContext.getPackageManager()) != null) {
			startActivity(intent);
		} else {
			showLogError(TAG, "there is no activity can handle this intent: "
					+ intent.getAction().toString());
		}
	}

	/** 通过Action跳转界面 **/
	protected void startActivity(String action) {
		Intent intent = new Intent();
		intent.setAction(action);
		if (intent.resolveActivity(mContext.getPackageManager()) != null) {
			startActivity(intent);
		} else {
			showLogError(TAG, "there is no activity can handle this intent: "
					+ intent.getAction().toString());
		}
	}

	/** 含有Date通过Action跳转界面 **/
	protected void startActivity(String action, Uri data) {
		Intent intent = new Intent();
		intent.setAction(action);
		intent.setData(data);
		if (intent.resolveActivity(mContext.getPackageManager()) != null) {
			startActivity(intent);
		} else {
			showLogError(TAG, "there is no activity can handle this intent: "
					+ intent.getAction().toString());
		}
	}

	/** 含有Bundle通过Action跳转界面 **/
	protected void startActivity(String action, Bundle bundle) {
		Intent intent = new Intent();
		intent.setAction(action);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		if (intent.resolveActivity(mContext.getPackageManager()) != null) {
			startActivity(intent);
		} else {
			showLogError(TAG, "there is no activity can handle this intent: "
					+ intent.getAction().toString());
		}
	}

	/** 含有标题和内容的对话框 **/
	protected AlertDialog showAlertDialog(String title, String message) {
		AlertDialog alertDialog = new AlertDialog.Builder(mContext)
				.setTitle(title).setMessage(message).show();
		return alertDialog;
	}

	/** 含有标题、内容、图标、两个按钮的对话框 **/
	protected AlertDialog showAlertDialog(String title, String message,
			int icon, String positiveText,
			DialogInterface.OnClickListener onPositiveClickListener,
			String negativeText,
			DialogInterface.OnClickListener onNegativeClickListener) {
		AlertDialog alertDialog = new AlertDialog.Builder(mContext)
				.setTitle(title).setMessage(message).setIcon(icon)
				.setPositiveButton(positiveText, onPositiveClickListener)
				.setNegativeButton(negativeText, onNegativeClickListener)
				.show();
		return alertDialog;
	}

	/** 含有标题、内容、两个按钮的对话框 **/
	protected AlertDialog showAlertDialog(String title, String message,
			String positiveText,
			DialogInterface.OnClickListener onPositiveClickListener,
			String negativeText,
			DialogInterface.OnClickListener onNegativeClickListener) {
		AlertDialog alertDialog = new AlertDialog.Builder(mContext)
				.setTitle(title).setMessage(message)
				.setPositiveButton(positiveText, onPositiveClickListener)
				.setNegativeButton(negativeText, onNegativeClickListener)
				.show();
		return alertDialog;
	}
}
