package com.foxconn.remote.control.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.foxconn.remote.control.R;
import com.foxconn.remote.control.base.BaseActivity;

/**
 *  選擇連接
 *  
 * @author KrisLight
 *
 */
public class ConnectTestActivity extends BaseActivity {

	private Button btnWifiSearch, btnBluetoothSearch;
	private OnClickListener searchListener = new BtnSearchClickListener();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_connect);
		findViewById();
		setListener();
	}

	@Override
	protected void findViewById() {
		btnWifiSearch = finder.find(R.id.btn_wifi_search);
		btnBluetoothSearch = finder.find(R.id.btn_bluetooth_search);
//		startNfc = finder.find(R.id.btn_nfc_search);
		
	}

	@Override
	protected void setListener() {
		btnWifiSearch.setOnClickListener(searchListener);
		btnBluetoothSearch.setOnClickListener(searchListener);
	}
	
	private class BtnSearchClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_wifi_search:
				startActivity(WifiActivity.class);
				break;
			case R.id.btn_bluetooth_search:
				startActivity(BlueToothActivity.class);
				break;
//			case R.id.btn_nfc_search:
//				startActivity(NfcActivity.class);
//				break;
			}
		}
	}

	@Override
	protected void init() {
		
	}

}
