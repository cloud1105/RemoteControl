package com.foxconn.remote.control.activity;

import java.util.ArrayList;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.foxconn.remote.control.R;
import com.foxconn.remote.control.adapter.RoomFragmentPagerAdapter;
import com.foxconn.remote.control.model.Room;
import com.foxconn.remote.control.utils.db.RoomDbAdapter;
import com.foxconn.remote.control.widget.CustomPagerIndicator;
import com.foxconn.remote.control.widget.CustomViewPager;

/**
 *  轉發器頁面
 * @author KrisLight
 */
public class HomeActivity extends SherlockNavigationActivity {
	
	private static final String TAG = HomeActivity.class.getSimpleName();
	//bundle var
	public static final String IR_LIST = "irList";
	//menu var
    private static final int MENU_ADD = Menu.FIRST;
	private static final int MENU_EDIT = Menu.FIRST + 1;
	private static final int MENU_DELETE = Menu.FIRST + 2;
	private static final int MENU_SAVE = Menu.FIRST + 3;
	private static final int MENU_LOAD = Menu.FIRST + 4;
	private static final int MENU_INFO = Menu.FIRST + 5;
	private static final int MENU_OTHER = Menu.FIRST + 6;
	
	// 存放電器的名字和圖標
	public ArrayList<Room> roomList;
	public ArrayList<String> roomNameList;
	private CustomViewPager mPager;
	private RoomFragmentPagerAdapter fragmentAdapter;
	private CustomPagerIndicator indicator;
	private RoomDbAdapter roomDbAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		initRoomData();
		findView();
		initData();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		getmTabHost().setCurrentTab(0);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		//從數據庫查詢轉發器重繪介面
		refreshRoomList();
	}

	/**
	 * 查询数据
	 */
	private void initRoomData() {
		if(roomDbAdapter == null)
		 roomDbAdapter = RoomDbAdapter.getInstance(this);
		roomList = (ArrayList<Room>) roomDbAdapter.getRoomList();
	}
	
	private void findView() {
		mPager = (CustomViewPager) findViewById(R.id.pager);
		indicator = (CustomPagerIndicator) findViewById(R.id.indicator);
	}
	
	private void initData(){
		fragmentAdapter = new RoomFragmentPagerAdapter(getSupportFragmentManager(),
				roomList,this,getBaseContext());
		mPager.setAdapter(fragmentAdapter);
		//設置ViewPager滑動是否生效
//		mPager.setPagingEnabled(false);
	    indicator.setViewPager(mPager);
	}

	private void refreshRoomList() {
		initRoomData();
		fragmentAdapter.setRoomlist(roomList);
		indicator.notifyDataSetChanged();
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	    SubMenu subMenuRoom = menu.addSubMenu("Overflow Item");
	        subMenuRoom.add(0, MENU_ADD, Menu.NONE, getResources().getString(R.string.add_ir));
	        subMenuRoom.add(0, MENU_EDIT, Menu.NONE, getResources().getString(R.string.edit_ir));
	        subMenuRoom.add(0, MENU_DELETE, Menu.NONE, getResources().getString(R.string.delete_ir));
	        subMenuRoom.add(0, MENU_SAVE, Menu.NONE, getResources().getString(R.string.save));
	        subMenuRoom.add(0, MENU_LOAD, Menu.NONE, getResources().getString(R.string.load));
	        subMenuRoom.add(0, MENU_INFO, Menu.NONE, getResources().getString(R.string.introduce));
	        subMenuRoom.add(0, MENU_OTHER, Menu.NONE, getResources().getString(R.string.band_suppport));
	        
	        MenuItem subMenuRoomItem = subMenuRoom.getItem();
	        subMenuRoomItem.setIcon(R.drawable.c_btn_setting);
	        subMenuRoomItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
	        
	        return super.onPrepareOptionsMenu(menu);
    }
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
		case MENU_ADD:
			  //新增下一个转发器
			  Intent addIntent  = new Intent(this,AddIrActivity.class);
			  if (addIntent.resolveActivity(getPackageManager()) != null) {
					startActivity(addIntent);
			  } else {
					Log.e(TAG, "there is no activity can handle this intent: "
							+ addIntent.getAction().toString());
			  }
			  break;
		case MENU_EDIT:
			//編輯轉發器
			  Intent editIntent  = new Intent(this,EditIrActivity.class);
			  if (editIntent.resolveActivity(getPackageManager()) != null) {
					startActivity(editIntent);
			  } else {
					Log.e(TAG, "there is no activity can handle this intent: "
							+ editIntent.getAction().toString());
			  }
			  break;
		case MENU_DELETE:
			//刪除轉發器
			  Intent deleteIntent = new Intent(this,DeleteIrActivity.class);
			  if (deleteIntent.resolveActivity(getPackageManager()) != null) {
					startActivity(deleteIntent);
			  } else {
					Log.e(TAG, "there is no activity can handle this intent: "
							+ deleteIntent.getAction().toString());
			  }
			break;
		case MENU_SAVE:
			break;
		case MENU_LOAD:
			break;
		case MENU_INFO:
			break;
		case MENU_OTHER:
			break;
		}
    	return super.onOptionsItemSelected(item);
    }
	

}