package com.foxconn.remote.control.activity;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;

import com.foxconn.remote.control.R;
import com.foxconn.remote.control.adapter.MachineTypeAdapter;
import com.foxconn.remote.control.model.ManchineType;
import com.foxconn.remote.control.utils.db.BaseTypeDbAdapter;

public class MachineTypeActivity extends ListActivity {

	private MachineTypeAdapter adapter;
	private BaseTypeDbAdapter dbAdapter;
	private Button btnCancel,btnConfirm;
	public final static String MACHINE_TYPE =  "Type";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_machine_type);
		dbAdapter = BaseTypeDbAdapter.getInstance(this);
		List<ManchineType> list = dbAdapter.loadTypes();
		adapter = new MachineTypeAdapter(this, list);
		getListView().setAdapter(adapter);
		getListView().setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				adapter.setSelectedIndex(arg2);
				adapter.notifyDataSetChanged();
			}
		});
		
		btnCancel = (Button) findViewById(R.id.btn_choose_cancel);
		btnConfirm = (Button) findViewById(R.id.btn_choose_confirm);
		
		btnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		btnConfirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = getIntent();
				String type = ((ManchineType)adapter.getItem(adapter.getSelectedIndex())).getTypeDes();
				intent.putExtra(MACHINE_TYPE, type);
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		
	}

}
