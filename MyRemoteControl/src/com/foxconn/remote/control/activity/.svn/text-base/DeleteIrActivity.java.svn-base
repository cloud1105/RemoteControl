package com.foxconn.remote.control.activity;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import com.foxconn.remote.control.R;
import com.foxconn.remote.control.base.BaseActivity;
import com.foxconn.remote.control.model.Room;
import com.foxconn.remote.control.utils.db.RoomDbAdapter;

/**
 * 
 * 刪除轉發器
 * 
 * @author KrisLight
 * 
 */
public class DeleteIrActivity extends BaseActivity {

	private ListView lv;
	private Button btnConfirmDelete, btnCancelDelete;
	private ArrayList<Room> list;
	private RoomDeleteAdapter adapter;
	private RoomDbAdapter dbAdapter;

	@Override
	protected void onResume() {
		super.onResume();
		initDate();
		initAdapter();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delete_ir);
		findViewById();
		initDate();
		setListener();
	}

	@Override
	protected void findViewById() {
		lv = finder.find(R.id.ir_list);
		btnConfirmDelete = finder.find(R.id.btn_confirm_delete);
		btnCancelDelete = finder.find(R.id.btn_cancel_delete);
	}

	protected void initDate() {
		dbAdapter = RoomDbAdapter.getInstance(this);
		list = (ArrayList<Room>) dbAdapter.getRoomList();
	}

	@Override
	protected void setListener() {
		adapter = new RoomDeleteAdapter(this, list);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {
				ViewHolder views = (ViewHolder) view.getTag();
				views.delCheckBox.toggle();
				adapter.selectedMap.put(position, views.delCheckBox.isChecked());
				adapter.notifyDataSetChanged();
				// 判断是否有记录被选中，以便设置删除按钮是否可用
				if (adapter.selectedMap.containsValue(true)) {
					btnConfirmDelete.setEnabled(true);
				} else {
					btnConfirmDelete.setEnabled(false);
				}

			}
		});

		btnConfirmDelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(DeleteIrActivity.this)
						.setTitle(getResourceString(R.string.delete_ir))
						.setMessage(getResourceString(R.string.confirm_to_delete))
						.setNegativeButton(android.R.string.cancel, null)
						.setPositiveButton(android.R.string.ok,
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										final ProgressDialog progressDialog = ProgressDialog
												.show(DeleteIrActivity.this,getResourceString(R.string.delete), getResourceString(R.string.deleting), true, false);
										final AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
											@Override
											protected Void doInBackground(
													Void... params) {
												// 删除记录事件处理
												List<Long> ids = adapter.getDelIdSet();
												dbAdapter.deleteRoomsById(ids);
												return null;
											}

											@Override
											protected void onPostExecute(
													Void result) {
												progressDialog.dismiss();
												finish();
												startActivityWithFlag(HomeActivity.class, 
														FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_SINGLE_TOP);
											}
										};
										progressDialog.show();
										task.execute();
									}
								}).setCancelable(true).create().show();
			}
		});

		btnCancelDelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	protected void init() {

	}

	// 刷新适配器
	public void initAdapter() {
		if (adapter == null) {
			adapter = new RoomDeleteAdapter(this, list);
			lv.setAdapter(adapter);
		} else {
			adapter.notifyDataSetChanged();
		}

	}

	private class RoomDeleteAdapter extends BaseAdapter {
		private List<Room> mList;
		// 保存每条记录是否被选中的状态
		Map<Integer, Boolean> selectedMap;
		// 保存被选中记录ID
		private List<Long> delIdSet;

		public List<Long> getDelIdSet() {
			return delIdSet;
		}

		public RoomDeleteAdapter(Context context, List<Room> list) {
			mList = list;
			selectedMap = new HashMap<Integer, Boolean>();
			delIdSet = new ArrayList<Long>();
			for (int i = 0; i < mList.size(); i++) {
				selectedMap.put(i, false);
			}
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holderViews = new ViewHolder();
			if (convertView == null) {
				convertView = LayoutInflater.from(DeleteIrActivity.this)
						.inflate(R.layout.delete_list_item, null);
				holderViews.nameView = (TextView) convertView
						.findViewById(R.id.name);
				holderViews.delCheckBox = (CheckBox) convertView
						.findViewById(R.id.btn_check);
				convertView.setTag(holderViews);
			}
			ViewHolder views = (ViewHolder) convertView.getTag();
			final String name = list.get(position).getRoomName();
			views.nameView.setText(name);
			views.delCheckBox.setChecked(selectedMap.get(position));
			// 保存记录Id
			if (selectedMap.get(position)) {
				delIdSet.add(list.get(position).getId());
			} else {
				delIdSet.remove(list.get(position).getId());
			}
			return convertView;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

	}

	private final class ViewHolder {
		TextView nameView;
		CheckBox delCheckBox;
	}
}
