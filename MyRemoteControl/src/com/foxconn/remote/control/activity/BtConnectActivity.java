package com.foxconn.remote.control.activity;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.foxconn.remote.control.R;
import com.foxconn.remote.control.base.BaseActivity;
import com.foxconn.remote.control.utils.blutooth.BluetoothConnectService;

/**
 *  藍牙通訊
 * 
 * @author KrisLight
 *
 */
public class BtConnectActivity extends BaseActivity {
	// Message types sent from the BluetoothChatService Handler
	public static final int MESSAGE_STATE_CHANGE = 1; // 狀態改變
	public static final int MESSAGE_READ = 2; // 讀取傳來的信息
	public static final int MESSAGE_WRITE = 3; // 發送信息
	public static final int MESSAGE_DEVICE_NAME = 4; // 連接某一設備
	public static final int MESSAGE_TOAST = 5; // the connection is lost or
												// failed

	// Key names received from the BluetoothChatService Handler
	public static final String DEVICE_NAME = "device_name";
	public static final String TOAST = "toast";

	// Intent request codes
	private static final int REQUEST_CONNECT_DEVICE = 1;

	// UI
	private TextView selDevice;
	private EditText goldKey;
	private Button btnVerify;

	// 正在連接顯示的進度條
	private ProgressDialog connectDialog;

	// BtService
	private BluetoothConnectService blueToothService;
	// device to be connect
	private BluetoothDevice device;
	// save the connect device name
	private String mConnectedDeviceName;
	// 存放接收轉發器返回的信息
	private String readMessage;

	// The Handler that gets information back from the BluetoothService
	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_STATE_CHANGE:
				switch (msg.arg1) {
				case BluetoothConnectService.STATE_CONNECTED:
					if (connectDialog != null) {
						connectDialog.dismiss();
					}
					// 已經連接 開始發送信息
					showLongToast(R.string.connected);
					break;
				case BluetoothConnectService.STATE_CONNECTING:
					// 正在連接
					showLongToast(R.string.connecting);
					showConnectProcessDialog();
					break;
				case BluetoothConnectService.STATE_NONE:
					// 未連接
					showShortToast(R.string.not_connect);
					break;
				}
				break;
			case MESSAGE_READ:
				byte[] readBuf = (byte[]) msg.obj;
				// 讀取到的信息
				readMessage = new String(readBuf, 0, msg.arg1);
				if ("sucess".equals(readMessage)) {
					showLongToast(R.string.connect_success);
					// 連接成功,配對完成
					startActivity(PairCompleteActivity.class);
				} else {
					// 連接失敗
					showLongToast(R.string.connect_failed);
				}
				break;
			case MESSAGE_WRITE:
				showLongToast(R.string.write_info);
				break;
			case MESSAGE_TOAST:
				// 連接丟失或連接失敗
				showShortToast(msg.getData().getString(TOAST));
				break;
			}
		}
	};

	protected void onDestroy() {
		super.onDestroy();
		// Stop the Bluetooth services
		if (blueToothService != null)
			blueToothService.stop();
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_device_connect);
		findViewById();
		setListener();
		init();
	}

	@Override
	protected void findViewById() {
		selDevice = (TextView) findViewById(R.id.tv_sel_device);
		goldKey = (EditText) findViewById(R.id.et_gold_key);
		btnVerify = (Button) findViewById(R.id.btn_connect);
	}

	@Override
	protected void setListener() {
		// 輸入金鑰配對
		btnVerify.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// //goldKey
				// String key = goldKey.getText().toString();
				// //配對

				// 這裡直接模擬藍牙配對傳Id接收反饋信息
				blueToothService.connect(device);
				String mac = device.getAddress();
				//發送信息
				String messageToIr = "id=" + mac;
				byte[] msg = messageToIr.getBytes();
				blueToothService.write(msg);
			}
		});
	}

	@Override
	protected void init() {
		blueToothService = new BluetoothConnectService(this, mHandler);
		Intent intent = getIntent();
		if (intent != null) {
			Bundle b = intent.getExtras();
			device = getDeviceFromBundle(b);
			mConnectedDeviceName = device.getName();
			selDevice.setText(mConnectedDeviceName);
		} else {
			selDevice.setText("null");
		}
		blueToothService.start();
	}

	private BluetoothDevice getDeviceFromBundle(Bundle b) {
		return b.getParcelable(BlueToothActivity.SELECT_BLUETOOTH_DEVICE);
	}

	private void showConnectProcessDialog() {
		connectDialog = new ProgressDialog(BtConnectActivity.this);
		connectDialog.setTitle(getResourceString(R.string.wait));
		connectDialog.setMessage(getResourceString(R.string.connecting));
		connectDialog.show();
	}
}
