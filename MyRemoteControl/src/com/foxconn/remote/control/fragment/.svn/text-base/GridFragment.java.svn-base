package com.foxconn.remote.control.fragment;

import java.util.ArrayList;
import java.util.List;
import org.askerov.dynamicgid.DynamicGridView;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.foxconn.remote.control.R;
import com.foxconn.remote.control.activity.HomeActivity;
import com.foxconn.remote.control.adapter.DynamicGridViewAdapter;
import com.foxconn.remote.control.model.Machine;
import com.foxconn.remote.control.model.Room;

/**
 *  Pager中的Fragment
 * 
 * @author KrisLight
 *
 */
public final class GridFragment extends Fragment {
	private Room room;
	private DynamicGridViewAdapter adapter;
	private DynamicGridView gridView;
	//實際需修改為Cursors
	private List<Machine> list;
	private TextView ivRoomTitle;
	private ImageView ivEdit;
	// 是否在编辑
	private static boolean editFlag = false;

	public GridFragment() {
	}

	public static GridFragment newInstance(Room room, Context context) {
		GridFragment fragment = new GridFragment();
		fragment.room = room;
		return fragment;
	}

	
	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initData();
		adapter = new DynamicGridViewAdapter(getActivity(), list, 4);
	}

	private void initData() {
		// 模擬從數據庫查數據得到Cursor
		list = new ArrayList<Machine>();
//		EquipmentItem eitem1 = new EquipmentItem("電視", R.drawable.blue);
//		list.add(eitem1);
		// 加號
//		addLastPlusView();
	}

	/**
	 *  添加電器面板最後的加號
	 */
//	private void addLastPlusView() {
//		EquipmentItem addItem = new EquipmentItem("none",
//				R.drawable.ic_launcher);
//		list.add(addItem);
//	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		inflater = LayoutInflater.from(getActivity());
		LinearLayout layout = (LinearLayout) inflater.inflate(
				R.layout.activity_grid, container, false);
		gridView = (DynamicGridView) layout.findViewById(R.id.dynamic_grid);
		ivRoomTitle = (TextView) layout.findViewById(R.id.imvTitle);
		ivEdit = (ImageView) layout.findViewById(R.id.imvEdit);
		ivRoomTitle.setText(room.getRoomName());
		return layout;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		gridView.setAdapter(adapter);
		gridView.setLongClickable(false);
		gridView.setOnDropListener(new DynamicGridView.OnDropListener() {
			@Override
			public void onActionDrop() {
				// 停止拖拽
				gridView.stopEditMode();
			}
		});
		ivEdit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!editFlag) {
					startRoomAndEquipmentEditMode();
				} else {
					stopRoomAndEquipmentEditMode();
				}
				editFlag = !editFlag;
			}

		});

		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 判斷如果點擊的是加號則新加一個Item 否則提示單擊
				Machine selItem = (Machine) adapter
						.getItem(position);
				// 非编辑模式下
				if (!adapter.isEditMode()) {
					if ("none".equals(selItem.getMachineName())) {
						addNewEquipment(position);
						// 加號
						if (position == DynamicGridViewAdapter.MAX_NUM - 1) {
							// 已经填满
							adapter.setLastItemFlag(true);
						}
						adapter.notifyListDataSetChange();
					} else {
						Toast.makeText(getActivity(), selItem.getMachineName(),
								Toast.LENGTH_SHORT).show();
					}
				}
			}

		});
	}

	private void addNewEquipment(int position) {
		//模擬增加電器
//		Machine tempItem = new Machine("萬能的", R.drawable.blue);
//		adapter.getList().add(position, tempItem);
	}

	private void stopEquipmentEditMode() {
		// 離開編輯模式 完成編輯操作
		adapter.stopEditMode();
		gridView.setLongClickable(false);
		gridView.stopEditMode();
		ivEdit.setBackgroundResource(R.drawable.c_btn_edit);
	}

	private void startEquipmentEditMode() {
		adapter.startEditMode();
		ivEdit.setBackgroundResource(R.drawable.c_btn_ok);
		gridView.setLongClickable(true);
		gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 开启拖拽模式
				gridView.startEditMode();
				return false;
			}
		});
	}

	private void stopRoomAndEquipmentEditMode() {
//		((HomeActivity) getActivity()).stopRoomEditMode();
		stopEquipmentEditMode();
	}
	
	private void startRoomAndEquipmentEditMode() {
//		((HomeActivity) getActivity()).startRoomEditMode();
		startEquipmentEditMode();
	}
	
	public boolean onBackPress() {
		if (editFlag) {
			stopRoomAndEquipmentEditMode();
			editFlag = false;
			return false;
		} else {
			return true;
		}
	}
}