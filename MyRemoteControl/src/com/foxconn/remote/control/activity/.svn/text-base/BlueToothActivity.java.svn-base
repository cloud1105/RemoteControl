package com.foxconn.remote.control.activity;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;
import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.foxconn.remote.control.R;
import com.foxconn.remote.control.adapter.MyBtListAdapter;
import com.foxconn.remote.control.base.BaseActivity;
import com.foxconn.remote.control.model.Room;
import com.foxconn.remote.control.utils.IrUtil;
import com.foxconn.remote.control.utils.RequestCodes;
import com.foxconn.remote.control.utils.db.RoomDbAdapter;
import com.foxconn.remote.control.utils.image.DrawableHelper;

/**
 *  藍牙搜索
 * @author KrisLight
 */
@SuppressLint("HandlerLeak")
public class BlueToothActivity extends BaseActivity {
	private static final String TAG = BlueToothActivity.class.getSimpleName();
	public static final String SELECT_BLUETOOTH_DEVICE = "selDevice";
	public static final int SEND_KEY_TO_IR = 1; //發送金鑰
	public static final String CON_RESULT = "result";
	public static final String GOLD_KEY = "key"; 
	/* 取得默认的蓝牙适配器 */
	private BluetoothAdapter mBluetoothAdapter;
	// 搜索提示進度條
	private ProgressDialog mProgressDialog;
	private MyBtListAdapter mBlueToothListAdapter;
	// 設備列表
	private List<BluetoothDevice> list;
	// UI
	private ListView bluetoothList;
	protected TextView tvTitle;
	private Button btnReStartSearch,btnCancel;
	// 選擇的藍牙設備
	private BluetoothDevice selDevice;
	//連接進度條
	private ProgressDialog connProgress;
	private AlertDialog alertToShow;
    
	public Handler handler = new Handler(){
		public void handleMessage(Message msg) {
		   	switch (msg.what) {
			case SEND_KEY_TO_IR:
				if (connProgress != null) {
					connProgress.dismiss();
					connProgress = null;
				}
				//得到連接結構
				boolean connResult = msg.getData().getBoolean(CON_RESULT);
				String key = msg.getData().getString(GOLD_KEY);
				if(alertToShow!=null){
				  alertToShow.dismiss();
				}
				if (connResult) {
					//得到未連接的BlueTooth列表
					mApplication.getRestBlueToothList().remove(selDevice);
					// 連結成功轉入主轉發器頁面
					// 并保存轉發器到DB 后跳轉到主頁面
					try{
					 saveRoom(key);
					}catch (Exception e) {
						showLogError(TAG, "save failed");
						finish();
					}
					startActivityWithFlag(HomeActivity.class, FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_SINGLE_TOP);
				} else {
					// 失敗重新連結
					tvTitle.setText(getResourceString(R.string.connect_ir_fail));
				}
				break;
			}
		};
	};

	private void saveRoom(String key) {
		RoomDbAdapter adapter = RoomDbAdapter.getInstance(getBaseContext());
		Room item = new Room();
		item.setRoomName(selDevice.getName());
		item.setMac(selDevice.getAddress());
		item.setGoldKey(key);
		item.setImgPath(DrawableHelper.DEFAULT_ROOM_IMG_PATH);
		adapter.saveRoom(item);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		// 若未打開藍牙,則發送請求打開藍牙
		if (!mBluetoothAdapter.isEnabled()) {
			Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResultSafely(enableIntent,RequestCodes.REQUEST_ENABLE_BT);
		} else {
			beginDiscovery();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mBluetoothAdapter != null) {
			mBluetoothAdapter.cancelDiscovery();
		}
		this.unregisterReceiver(mReceiver);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_device_list);
		findViewById();
		setListener();
		init();

	}

	// 接收打開藍牙返回的結果
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == RequestCodes.REQUEST_ENABLE_BT) {
			if (resultCode == RESULT_OK) {
				beginDiscovery();
			} else {
				showShortToast(R.string.open_bt_failed);
				finish();
			}
		}
	}

	/** 搜索 **/
	public void beginDiscovery() {
		if (null == mProgressDialog) {
			mProgressDialog = new ProgressDialog(this);
			mProgressDialog.setMessage(getResourceString(R.string.start_search));
			//正式設置成false
//			mProgressDialog.setCancelable(false);
			//測試設置成true
			mProgressDialog.setCancelable(true);
		}
		mProgressDialog.show();
		if (mBluetoothAdapter.isDiscovering()) {
			mBluetoothAdapter.cancelDiscovery();
		}
		mBluetoothAdapter.startDiscovery();
	}

	@Override
	protected void findViewById() {
		bluetoothList = finder.find(R.id.bt_list);
		btnReStartSearch = finder.find(R.id.btn_re_start_search);
		tvTitle = finder.find(R.id.tv_search_ir_title);
		btnCancel = finder.find(R.id.btn_search_cancel);
		btnCancel.setVisibility(View.GONE);
	}

	@Override
	protected void setListener() {
		
		// 未找到結果,重新開始搜索
		btnReStartSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!mBluetoothAdapter.isEnabled()) {
					Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
					startActivityForResultSafely(enableIntent,RequestCodes.REQUEST_ENABLE_BT);
				} else {
					beginDiscovery();
				}
			}
		});
		
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) {
			showShortToast(R.string.not_support_bt);
			finish();
		}
		list = new ArrayList<BluetoothDevice>();
		mBlueToothListAdapter = new MyBtListAdapter(this, list);
		bluetoothList.setAdapter(mBlueToothListAdapter);
		bluetoothList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parentView, View v,
					int position, long id) {
				selDevice = (BluetoothDevice) mBlueToothListAdapter
						.getItem(position);
				showConnectDialog();
			}
		});
	}

	@Override
	protected void init() {
		IntentFilter intentFilter = new IntentFilter();
		// 設置監聽兩種廣播Action
		intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
		intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		this.registerReceiver(mReceiver, intentFilter);
	}

	
	// The BroadcastReceiver that listens for discovered devices 
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				if (!list.contains(device)) {
					mBlueToothListAdapter.getList().add(device);
					mApplication.getRestBlueToothList().add(device);
					mBlueToothListAdapter.notifyDataSetChanged();
				}
			} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
					.equals(action)) {
				if (mProgressDialog != null) {
					mProgressDialog.dismiss();
				}
			}
		}
	};
	
	private void showConnectDialog() {
		//连接的转发器的名字
		String irName = selDevice.getName();
		
		final AlertDialog.Builder alert = new AlertDialog.Builder(
				BlueToothActivity.this);
		LayoutInflater factory = LayoutInflater.from(BlueToothActivity.this);
		final View connectEntryView = factory.inflate(
				R.layout.alert_key_layout, null);
		
		final TextView deviceTitle = (TextView) connectEntryView
				.findViewById(R.id.tv_ir_title);
		deviceTitle.setText(irName);
		
		final Button btnCancel = (Button) connectEntryView.findViewById(R.id.btn_cancel);
		final Button btnConnect = (Button) connectEntryView.findViewById(R.id.btn_connect);
		final EditText inputBox = (EditText) connectEntryView.findViewById(R.id.et_key);
		
		alert.setView(connectEntryView);
		alertToShow = alert.create();
		alertToShow.show();
		
        btnCancel.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					alertToShow.dismiss();
				}
		});
        
        btnConnect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (connProgress == null) {
					connProgress = new ProgressDialog(
							BlueToothActivity.this);
					connProgress.setMessage(selDevice.getName()
							+ "\n"+getResourceString(R.string.connecting));
					connProgress.setCancelable(false);
					connProgress.show();
				}
				new Thread(new Runnable() {
					// 用輸入的金鑰去連結
					@Override
					public void run() {
						String key = inputBox.getText().toString();
						boolean connResult = IrUtil.connnetToIrWithBlueTooth(key);
						Message msg = handler.obtainMessage(SEND_KEY_TO_IR);
						Bundle data = new Bundle();
						data.putBoolean(CON_RESULT, connResult);
						data.putString(GOLD_KEY, key);
						msg.setData(data);
						handler.sendMessage(msg);
					}
				}).start();
			}
		});
	}

}
