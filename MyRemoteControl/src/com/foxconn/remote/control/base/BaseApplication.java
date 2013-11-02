package com.foxconn.remote.control.base;

import java.util.ArrayList;
import java.util.List;
import android.app.Application;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.net.wifi.ScanResult;
import com.bugsense.trace.BugSenseHandler;
import com.foxconn.remote.control.R;
import com.foxconn.remote.control.dao.DaoMaster;
import com.foxconn.remote.control.dao.DaoMaster.OpenHelper;
import com.foxconn.remote.control.dao.DaoSession;
import com.foxconn.remote.control.dao.ManchineTypeDao;
import com.foxconn.remote.control.model.Machine;
import com.foxconn.remote.control.model.ManchineType;
import com.foxconn.remote.control.model.Room;
import com.foxconn.remote.control.utils.Constants;

/**
 * @author KrisLight
 *
 */
public class BaseApplication extends Application {
	/**
	 * Application实例
	 */
	public static final boolean DEBUG = true;
	private static BaseApplication application;
	private static DaoMaster mDaoMaster;
	private static DaoSession mDaoSession;
	
	
	//cache
	private ArrayList<Room> roomList;
	private ArrayList<Machine> equipmentList;
	
	//緩存查詢出來但未連接的wifiList
	private List<ScanResult> restWifiList;
	//緩存查詢出來但未連接的BlueDeviceList
	private List<BluetoothDevice> restBlueToothList;

	
	public List<BluetoothDevice> getRestBlueToothList() {
		if(restBlueToothList == null){
		  restBlueToothList = new ArrayList<BluetoothDevice>();
		}
		return restBlueToothList;
	}

	public void setRestBlueToothList(List<BluetoothDevice> restBlueToothList) {
		this.restBlueToothList = restBlueToothList;
	}

	public ArrayList<Machine> getEquipmentList() {
		return equipmentList;
	}

	public void setEquipmentList(ArrayList<Machine> equipmentList) {
		this.equipmentList = equipmentList;
	}

	public List<ScanResult> getRestWifiList() {
		return restWifiList;
	}

	public void setRestWifiList(List<ScanResult> wifiList) {
		this.restWifiList = wifiList;
	}

	public ArrayList<Room> getRoomList() {
		return roomList;
	}

	public void setRoomList(ArrayList<Room> roomList) {
		this.roomList = roomList;
	}

	/**
	 * 获取Application实例
	 * 
	 * @return
	 */
	public static BaseApplication getInstance() {
		return application;
	}

	
	/**
	 * 得到DaoMaster
	 * 
	 * @param context
	 * @return
	 */
	public static DaoMaster getDaoMaster(Context context) {
		if (mDaoMaster == null) {
			OpenHelper helper = new DaoMaster.DevOpenHelper(context, Constants.DB_NAME, null);
			mDaoMaster = new DaoMaster(helper.getWritableDatabase());
		}
		return mDaoMaster;
	}

	/**
	 * 得到DaoSession
	 * 
	 * @param context
	 * @return
	 */
	public static DaoSession getDaoSession(Context context) {
		if (mDaoSession == null) {
			if (mDaoMaster == null) {
				mDaoMaster = getDaoMaster(context);
			}
			mDaoSession = mDaoMaster.newSession();
		}
		return mDaoSession;
	}
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		/**
		 * http://stackoverflow.com/questions/18053888/
		 * android-bugsense-gives-exception-getaddrinfo-bugsense-appspot-com-return-erro
		 */
		BugSenseHandler.I_WANT_TO_DEBUG = true;
		//init BugSense
		BugSenseHandler.initAndStartSession(this, Constants.BUG_SENSE_API_KEY);
		
		if (null == application) {
			application = this;
		}
		if(!DEBUG){
		 //處理未捕獲異常
		 CrashHandler crashHandler = CrashHandler.getInstance();
		 crashHandler.init(getApplicationContext());
		}
	}
	
}
