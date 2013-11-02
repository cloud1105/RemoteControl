package com.foxconn.remote.control.activity;

import com.foxconn.remote.control.R;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 選擇新增轉發器
 * @author KrisLight
 *
 */
public class AddIrActivity extends WifiActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		tvTitle.setText(getResourceString(R.string.choose_ir_add));
		btnCancel.setVisibility(View.VISIBLE);
		btnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();
		mWifiListAdapter.setList(mApplication.getRestWifiList());
	}

}
