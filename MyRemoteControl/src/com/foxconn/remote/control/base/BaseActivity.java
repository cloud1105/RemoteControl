package com.foxconn.remote.control.base;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

import java.io.Serializable;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockActivity;
import com.foxconn.remote.control.R;
import com.foxconn.remote.control.utils.view.ViewFinder;

/**
 * @author KrisLight
 */
public abstract class BaseActivity extends SherlockActivity {
	/** Appliction基类对象 **/
	protected BaseApplication mApplication;
	private final static String TAG = BaseActivity.class.getSimpleName();
	private static final boolean Debug = true;
    /**
     * Finder bound to this activity's view
     */
    protected ViewFinder finder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 获取BaseApplication对象
		mApplication = (BaseApplication) getApplication();
		finder = new ViewFinder(this);
		
		//暫時用嚴苛模式 API 11  正式取消還原到API8
//		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//		.detectAll().penaltyLog().build());
//        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
//		.detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
//		.penaltyLog().penaltyDeath().build());
	}

	/** 绑定界面UI **/
	protected abstract void findViewById();

	/** 界面UI事件监听 **/
	protected abstract void setListener();

	/** 界面数据初始化 **/
	protected abstract void init();

	/** 短暂显示Toast提示(来自res) **/
	protected void showShortToast(int resId) {
		Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show();
	}

	/** 短暂显示Toast提示(来自String) **/
	protected void showShortToast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}

	/** 长时间显示Toast提示(来自res) **/
	protected void showLongToast(int resId) {
		Toast.makeText(this, getString(resId), Toast.LENGTH_LONG).show();
	}

	/** 长时间显示Toast提示(来自String) **/
	protected void showLongToast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}

	/** Debug输出Log日志 **/
	protected void showLogDebug(String tag, String msg) {
		if (Debug)
			Log.d(tag, msg);
	}

	/** Error输出Log日志 **/
	protected void showLogError(String tag, String msg) {
		if (Debug)
			Log.e(tag, msg);
	}
	
	/** 通过Class和Intent Flag跳转界面 **/
	protected void startActivityWithFlag(Class<?> cls,int flag) {
		startActivityWithFlag(cls, null,flag);
	}

	/** 通过Class跳转界面 **/
	protected void startActivity(Class<?> cls) {
		startActivity(cls, null);
	}
	
	/** 含有Bundle通过Class跳转界面 **/
	protected void startActivityWithFlag(Class<?> cls, Bundle bundle,int intentFlag) {
		Intent intent = new Intent();
		intent.setClass(this, cls);
		intent.addFlags(intentFlag);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		if (intent.resolveActivity(getPackageManager()) != null) {
			startActivity(intent);
		} else {
			showLogError(TAG, "there is no activity can handle this intent: "
					+ intent.getAction().toString());
		}
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}

	/** 含有Bundle通过Class跳转界面 **/
	protected void startActivity(Class<?> cls, Bundle bundle) {
		Intent intent = new Intent();
		intent.setClass(this, cls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		if (intent.resolveActivity(getPackageManager()) != null) {
			startActivity(intent);
		} else {
			showLogError(TAG, "there is no activity can handle this intent: "
					+ intent.getAction().toString());
		}
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}

	/** 通过Action跳转界面 **/
	protected void startActivity(String action) {
		Intent intent = new Intent();
		intent.setAction(action);
		if (intent.resolveActivity(getPackageManager()) != null) {
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
		if (intent.resolveActivity(getPackageManager()) != null) {
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
		if (intent.resolveActivity(getPackageManager()) != null) {
			startActivity(intent);
		} else {
			showLogError(TAG, "there is no activity can handle this intent: "
					+ intent.getAction().toString());
		}
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
	
	 /**
     * 得到序列化的Intent參數
     *
     * @param name
     * @return serializable
     */
    @SuppressWarnings("unchecked")
    protected <V extends Serializable> V getSerializableExtra(final String name) {
        return (V) getIntent().getSerializableExtra(name);
    }

    /**
     * 得到整型的Intent參數
     *
     * @param name
     * @return int
     */
    protected int getIntExtra(final String name) {
        return getIntent().getIntExtra(name, -1);
    }

    /**
     * 得到字符串的Intent參數
     *
     * @param name
     * @return string
     */
    protected String getStringExtra(final String name) {
        return getIntent().getStringExtra(name);
    }

    /**
     * 得到字符串數組的Intent參數
     *
     * @param name
     * @return string array
     */
    protected String[] getStringArrayExtra(final String name) {
        return getIntent().getStringArrayExtra(name);
    }

	/** 含有标题和内容的对话框 **/
	protected AlertDialog showAlertDialog(String title, String message) {
		AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle(title)
				.setMessage(message).show();
		return alertDialog;
	}

	/** 含有标题、内容、两个按钮的对话框 **/
	protected AlertDialog showAlertDialog(String title, String message,
			String positiveText,
			DialogInterface.OnClickListener onPositiveClickListener,
			String negativeText,
			DialogInterface.OnClickListener onNegativeClickListener) {
		AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle(title)
				.setMessage(message)
				.setPositiveButton(positiveText, onPositiveClickListener)
				.setNegativeButton(negativeText, onNegativeClickListener)
				.show();
		return alertDialog;
	}

	/** 含有标题、内容、图标、两个按钮的对话框 **/
	protected AlertDialog showAlertDialog(String title, String message,
			int icon, String positiveText,
			DialogInterface.OnClickListener onPositiveClickListener,
			String negativeText,
			DialogInterface.OnClickListener onNegativeClickListener) {
		AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle(title)
				.setMessage(message).setIcon(icon)
				.setPositiveButton(positiveText, onPositiveClickListener)
				.setNegativeButton(negativeText, onNegativeClickListener)
				.show();
		return alertDialog;
	}

	/** 带有右进右出动画的退出 **/
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}

	/** 默认退出 **/
	protected void defaultFinish() {
		super.finish();
	}

	protected void startActivityForResultSafely(Intent intent, int requestCode) {
		try {
			startActivityForResult(intent, requestCode);
		} catch (ActivityNotFoundException e) {
			Toast.makeText(this, R.string.activity_not_found,
					Toast.LENGTH_SHORT).show();
		} catch (SecurityException e) {
			Toast.makeText(this, R.string.activity_not_found,
					Toast.LENGTH_SHORT).show();
			Log.e(TAG,"Launcher does not have the permission to launch "
							+ intent
							+ ". Make sure to create a MAIN intent-filter for the corresponding activity "
							+ "or use the exported attribute for this activity.",e);
		}
	}
	
	public String getResourceString(int resId){
		return getResources().getString(resId);
	}
}
