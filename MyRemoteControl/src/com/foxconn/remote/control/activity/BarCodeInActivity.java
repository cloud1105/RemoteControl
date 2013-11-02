package com.foxconn.remote.control.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.actionbarsherlock.view.MenuItem;
import com.foxconn.remote.control.R;
import com.foxconn.remote.control.base.BaseActivity;
import com.foxconn.remote.control.zxing.CaptureActivity;
import com.foxconn.remote.control.zxing.decoding.CaptureActivityHandler;

public class BarCodeInActivity extends BaseActivity {
	
	private final static int SCAN_BARCODE_REQUEST_CODE = 0x1234556;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bar_code_in);
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
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	//按鈕點擊事件
	public void scan(View view){
		Intent intent = new Intent(this, CaptureActivity.class);
		startActivityForResult(intent, SCAN_BARCODE_REQUEST_CODE);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if(requestCode == SCAN_BARCODE_REQUEST_CODE && resultCode == RESULT_OK){
			String upcCodeResult = intent.getStringExtra(CaptureActivityHandler.RESULT_CODE);
			showShortToast(upcCodeResult);
			
		}
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
	  switch (item.getItemId()) {
	  case android.R.id.home:
		finish();
		return true;
	  }
	  return false;
	}


}
