package com.foxconn.remote.control.activity;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
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
import android.widget.Toast;

import com.foxconn.remote.control.R;
import com.foxconn.remote.control.adapter.MyWifiListAdapter;
import com.foxconn.remote.control.base.BaseActivity;
import com.foxconn.remote.control.model.Room;
import com.foxconn.remote.control.utils.IrUtil;
import com.foxconn.remote.control.utils.db.RoomDbAdapter;
import com.foxconn.remote.control.utils.image.DrawableHelper;
import com.foxconn.remote.control.utils.wifi.WifiUtil;

/**
 *  Wifi搜素
 * @author KrisLight
 */
public class WifiActivity extends BaseActivity {
	public static final String TAG = WifiActivity.class.getSimpleName();
	public static final String SELECT_WIFI_DEVICE = "selDevice";
	public static final String PAIED_ROOM_ITEM = "RoomItem";
	public static final String CON_RESULT = "result";
	public static final String GOLD_KEY = "key"; 
	//handler msg
	public static final int SCAN_RESULT = 1;// 搜索到wifi返回结果
	public static final int CONNECT_RESULT = 2;// 连接上wifi热点
	public static final int SEND_KEY_TO_IR = 3; //發送金鑰
	protected WifiUtil util;
	// 設備列表
	protected List<ScanResult> list;
	// UI
	protected TextView tvTitle;
	protected ListView wifiList;
	// adapter
	protected MyWifiListAdapter mWifiListAdapter;
	protected Button btnReStartSearch,btnCancel;
	// 選擇的WIFI設備
	private ScanResult selDevice;
	//搜索進度條
	private ProgressDialog searchProgress;
	//連接進度條
	private ProgressDialog connProgress;
	private AlertDialog alertToShow;

	public Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SCAN_RESULT:// 搜索到可连接wifi
				if (searchProgress != null) {
					searchProgress.dismiss();
				}
				if(mApplication.getRestWifiList() == null){
				  mApplication.setRestWifiList(util.getWifiList());
				}
				mWifiListAdapter.setList(util.getWifiList());
				mWifiListAdapter.notifyDataSetChanged();
				break;
			case CONNECT_RESULT://连接上wifi热点
				mWifiListAdapter.notifyDataSetChanged();
				break;
			case SEND_KEY_TO_IR://用金鑰連接
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
					//得到未連接的wifi列表
					mApplication.getRestWifiList().remove(selDevice);
					// 連結成功轉入主轉發器頁面
					// 并保存轉發器到DB 后跳轉到主頁面
					saveRoom(key);
					startActivityWithFlag(HomeActivity.class, FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_SINGLE_TOP);
				} else {
					// 失敗重新連結
					tvTitle.setText(getResourceString(R.string.connect_ir_fail));
				}
				break;
			}
		}
	};
	
	private void saveRoom(String key) {
		RoomDbAdapter adapter = RoomDbAdapter.getInstance(getBaseContext());
		Room item = new Room();
		item.setRoomName(selDevice.SSID);
		item.setGoldKey(key);
		item.setMac(selDevice.BSSID);
		item.setImgPath(DrawableHelper.DEFAULT_ROOM_IMG_PATH);
		adapter.saveRoom(item);
	}

	protected void onStart() {
		super.onStart();
		beginDiscovery();
	};

	@Override
	protected void onResume() {
		super.onResume();
		showLogDebug(TAG, "onResume invoke");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.unregisterReceiver(mReceiver);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_device_list);
		findViewById();
		setListener();
		init();
	}

	@Override
	protected void findViewById() {
		tvTitle = finder.find(R.id.tv_search_ir_title);
		wifiList = finder.find(R.id.bt_list);
		btnReStartSearch = finder.find(R.id.btn_re_start_search);
		btnCancel = finder.find(R.id.btn_search_cancel);
		btnCancel.setVisibility(View.GONE);
	}

	@Override
	protected void setListener() {
		
		list = new ArrayList<ScanResult>();
		mWifiListAdapter = new MyWifiListAdapter(this, list);
		wifiList.setAdapter(mWifiListAdapter);
		
		// 未找到結果,重新開始搜索
		btnReStartSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				beginDiscovery();
			}
		});
		
		wifiList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parentView, View v,
					int position, long id) {
				selDevice = (ScanResult) mWifiListAdapter.getItem(position);
				showConnectDialog();
			}
		});
		
	}

	@Override
	protected void init() {
		util = new WifiUtil(WifiActivity.this);
		/**
		 * 設置ListView可選的話選中的item會變黑
		 * based on
		 * http://stackoverflow.com/questions/9437797/how-to-prevent-listview-items-higlighting-in-black
		 */
//		wifiList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		// 注册WIFI扫描监听器
		IntentFilter intentFilter = new IntentFilter();
		// 設置監聽兩種廣播Action
		intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
		intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
		intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
		this.registerReceiver(mReceiver, intentFilter);
	}

	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		public void onReceive(Context paramContext, Intent paramIntent) {
			if (paramIntent.getAction().equals("android.net.wifi.SCAN_RESULTS")) {
				scanResultsAvailable();
			} else if (paramIntent.getAction().equals(
					"android.net.wifi.WIFI_STATE_CHANGED")) {
				wifiStatusNotification();
			} else if (paramIntent.getAction().equals(
					"android.net.wifi.STATE_CHANGE")) {
				handleConnectChange();
			}

		}

	};
	
	private void showConnectDialog() {
		//连接的转发器的名字
		String irName = selDevice.SSID;
		
		final AlertDialog.Builder alert = new AlertDialog.Builder(
				WifiActivity.this);
		LayoutInflater factory = LayoutInflater.from(WifiActivity.this);
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
							WifiActivity.this);
					connProgress.setMessage(selDevice.SSID
							+ "\n"+getResourceString(R.string.connecting));
					connProgress.setCancelable(false);
					connProgress.show();
				}
				new Thread(new Runnable() {
					// 用輸入的金鑰去連結
					@Override
					public void run() {
						String key = inputBox.getText().toString();
						boolean connResult = IrUtil.connnetToIrWithWifi(key);
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

	/** 搜索WIFI熱點 **/
	public void beginDiscovery() {
		//搜索進度條
		util.openWifi();
		util.clearWifiList();
		if (null == searchProgress) {
			searchProgress = new ProgressDialog(this);
			searchProgress.setMessage(getResourceString(R.string.search_ir));
			searchProgress.setCancelable(false);
		}
		searchProgress.show();
		util.startScan();
	}
	

	/** 监听广播，如果连接上一个热点，执行更新界面 **/
	public void handleConnectChange() {
		Message msg = handler.obtainMessage(CONNECT_RESULT);
		handler.sendMessage(msg);
	}

	/** 监听广播，搜索到各个wifi **/
	public void scanResultsAvailable() {
		Message msg = handler.obtainMessage(SCAN_RESULT);
		handler.sendMessage(msg);
	}

	/** 监听打开或关闭wifi广播 **/
	public void wifiStatusNotification() {
		switch (util.checkState()) {
		case WifiManager.WIFI_STATE_DISABLED:
			Toast.makeText(getApplicationContext(), getResourceString(R.string.wifi_disconnect),
					Toast.LENGTH_SHORT).show();
			finish();
			break;
		case WifiManager.WIFI_STATE_ENABLING:
			Toast.makeText(getApplicationContext(), getResourceString(R.string.wifi_connecting),
					Toast.LENGTH_SHORT).show();
			break;
		}
	}
	
}
