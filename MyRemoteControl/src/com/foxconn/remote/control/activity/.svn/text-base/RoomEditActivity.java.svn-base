package com.foxconn.remote.control.activity;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import com.foxconn.remote.control.R;
import com.foxconn.remote.control.adapter.IconAdapter;
import com.foxconn.remote.control.base.BaseActivity;
import com.foxconn.remote.control.model.Room;
import com.foxconn.remote.control.utils.db.RoomDbAdapter;
import com.foxconn.remote.control.utils.image.DrawableHelper;
import com.foxconn.remote.control.widget.ConfirmDialog;

/**
 *  編輯轉發器
 * @author KrisLight
 *
 */
public class RoomEditActivity extends BaseActivity {
	private GridView mGridViewIcon;
	private IconAdapter adapter;
	private EditText etRoomName, etIrKey;
	private Button btnConfirm;
	private Room item;
	private RoomDbAdapter dbAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(getResourceString(R.string.edit_ir));
		setContentView(R.layout.activity_room_edit);
		findViewById();
		init();
		setListener();
	}

	@Override
	protected void findViewById() {
		mGridViewIcon = finder.find(R.id.gridview_icon);
		etRoomName = finder.find(R.id.et_room_name);
		etIrKey = finder.find(R.id.et_ir_key);
		btnConfirm = finder.find(R.id.btn_confirm);
	}

	@Override
	protected void setListener() {
		mGridViewIcon.setAdapter(adapter);
		mGridViewIcon.setOnItemClickListener(new GridView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						IconAdapter iconAdapter = (IconAdapter) parent
								.getAdapter();
						iconAdapter.setSelectedItemPosition(position);
						iconAdapter.notifyDataSetChanged();
					}
		});
		
		btnConfirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String roomName = etRoomName.getText().toString();
				String irKey = etIrKey.getText().toString();
				int selectPos = ((IconAdapter)mGridViewIcon.getAdapter()).getSelectedPosition();
				String imgPath = DrawableHelper.ICON_SET[selectPos];
				if (TextUtils.isEmpty(roomName)) {
					etRoomName.setError(getResourceString(R.string.name_not_empty));
				} else if (TextUtils.isEmpty(irKey)) {
					etIrKey.setError(getResourceString(R.string.key_not_empty));
				} else {
					item.setRoomName(roomName);
					item.setGoldKey(etIrKey.getText().toString());
					item.setImgPath(imgPath);
					// 保存修改的item信息到DB
					dbAdapter.updateRoom(item);
					finish();
					startActivityWithFlag(HomeActivity.class, 
							FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_SINGLE_TOP);
				}
			}
		});
	}

	@Override
	protected void init() {
		// 模擬存取傳過來的轉發器名字和對應的金鑰,放入TextView显示
		Intent it = getIntent();
		if(it!=null){
			item = (Room)getSerializableExtra(EditIrActivity.ROOM_ITEM_EDIT);
			showShortToast("傳過來的Id"+item.getId());
		}else{
			showShortToast("item 是空的");
		}
		dbAdapter = RoomDbAdapter.getInstance(this);
		etRoomName.setText(item.getRoomName());
		etIrKey.setText(item.getGoldKey());
		int position = 0;
		for(int i =0;i<DrawableHelper.ICON_SET.length;i++){
			if(item.getImgPath().trim().equals(DrawableHelper.ICON_SET[i].trim())){
				position = i;
			}
		}
		adapter = new IconAdapter(this,DrawableHelper.ICON_SET);
		adapter.setSelectedItemPosition(position);
	}
	
	@Override
	public void onBackPressed() {
		final ConfirmDialog dialog = new ConfirmDialog(this);
		dialog.setOnLeaveListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				finish();
				startActivityWithFlag(HomeActivity.class, 
						FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_SINGLE_TOP);
			}
		});
		dialog.setOnNotLeaveListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}

}
