package com.foxconn.remote.control.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.foxconn.remote.control.R;
import com.foxconn.remote.control.adapter.RoomListViewAdapter;
import com.foxconn.remote.control.base.BaseActivity;
import com.foxconn.remote.control.model.Room;
import com.foxconn.remote.control.utils.db.RoomDbAdapter;

/**
 * 選擇編輯的轉發器頁面
 * @author KrisLight
 */
public class EditIrActivity extends BaseActivity{

	public final static String ROOM_ITEM_EDIT = "editRoomItem";
    private ListView lv;	
    private ArrayList<Room> list;
    private RoomDbAdapter dbAdapter;
    private RoomListViewAdapter listAdapter;
    
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_ir);
		findViewById();
		setListener();
		init();
	}
	
	@Override
	protected void findViewById() {
		lv = finder.find(R.id.ir_list);
	}

	@Override
	protected void setListener() {
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//模擬選擇的轉發器
				Room selRoom = list.get(position);
				Bundle bundle = new Bundle();
				bundle.putSerializable(ROOM_ITEM_EDIT, selRoom);
                startActivity(RoomEditActivity.class, bundle);
			}
		});
	}

	@Override
	protected void init() {
		//查詢房間名的列表
		dbAdapter = RoomDbAdapter.getInstance(this);
		list = (ArrayList<Room>) dbAdapter.getRoomList();
		listAdapter = new RoomListViewAdapter(this, list);
		lv.setAdapter(listAdapter);
	}

}
