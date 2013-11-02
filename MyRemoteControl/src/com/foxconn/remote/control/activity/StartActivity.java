package com.foxconn.remote.control.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.foxconn.remote.control.R;
import com.foxconn.remote.control.base.BaseActivity;
import com.foxconn.remote.control.utils.SharedPreferencesUtils;

/**
 *  歡迎頁面
 * 
 * @author KrisLight
 *
 */
@SuppressLint("HandlerLeak")
public class StartActivity extends BaseActivity {
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if(msg.what == 0){
			    startActivity(ConnectTestActivity.class);
			}else{
				startActivity(HomeActivity.class);
			}
			finish();
		};
	};

	@Override
	protected void onPause() {
		super.onPause();
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		init();
	}

	@Override
	protected void findViewById() {
	}

	@Override
	protected void setListener() {

	}

	@Override
	protected void init() {
		//判斷是否是第一次進入應用
		boolean isUsed = SharedPreferencesUtils.getUsedTag(this);
		/** 測試時設置直接進主頁面 **/
//		isUsed = true;
		Message whichPageMsg = new Message();
		if(isUsed){
		  whichPageMsg.what = 1;
		}else{
		  whichPageMsg.what = 0;
		}
		mHandler.sendMessageDelayed(whichPageMsg, 1000);
	}

}
