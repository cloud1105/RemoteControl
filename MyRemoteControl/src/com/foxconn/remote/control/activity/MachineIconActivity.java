package com.foxconn.remote.control.activity;

import java.io.IOException;
import java.io.InputStream;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import com.foxconn.remote.control.R;
import com.foxconn.remote.control.adapter.IconAdapter;
import com.foxconn.remote.control.base.BaseActivity;
import com.foxconn.remote.control.utils.image.DrawableHelper;

public class MachineIconActivity extends BaseActivity {
	public final static String ACTION = "crop";
	public final static String ICON_DRAWABLE = "iconDrawable";
	private GridView mGridViewIcon;
	private IconAdapter iconAdapter;
	private Button btnConfirm,btnCancel,btnCamera,btnGallery;
	private Intent i;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_machine_icon);
		i = getIntent();
		findViewById();
		setListener();
		init();
	}

	@Override
	protected void findViewById() {
		mGridViewIcon = finder.find(R.id.gridview_icon);
		btnCamera = finder.find(R.id.btn_camera_choose);
		btnGallery = finder.find(R.id.btn_crop_choose);
		btnCancel = finder.find(R.id.btn_cancel);
		btnConfirm = finder.find(R.id.btn_confirm);
		
	}

	@Override
	protected void setListener() {
		btnCamera.setOnClickListener(BtnClickListener);
		btnGallery.setOnClickListener(BtnClickListener);
		btnCancel.setOnClickListener(BtnClickListener);
		btnConfirm.setOnClickListener(BtnClickListener);
	}

	@Override
	protected void init() {
		iconAdapter = new IconAdapter(this,DrawableHelper.MACHINE_ICON_SET);
		mGridViewIcon.setAdapter(iconAdapter);
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
	}
	
	private OnClickListener BtnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_camera_choose:
				i.putExtra(ACTION, "camera");
				setResult(RESULT_CANCELED,i);
				break;
			case R.id.btn_crop_choose:
				i.putExtra(ACTION, "crop");
				setResult(RESULT_CANCELED,i);
				break;
			case R.id.btn_cancel:
				//取消
				break;
			case R.id.btn_confirm:
				//確認
				int selectPoz = iconAdapter.getSelectedPosition();
				String path = (String) iconAdapter.getItem(selectPoz);
				InputStream is = null;
				try {
					is = getAssets().open(path);
				} catch (IOException e) {
					e.printStackTrace();
				}
				i.putExtra(ICON_DRAWABLE, BitmapFactory.decodeStream(is));
				setResult(RESULT_OK, i);
				break;
			}
			finish();
		}
	};

}
