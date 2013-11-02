package com.foxconn.remote.control.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.foxconn.remote.control.R;
import com.foxconn.remote.control.base.BaseActivity;

/**
 * (初版用 已廢棄)
 * @author KrisLight
 *
 */
public class MainActivity extends BaseActivity {

	private Button btnSearchIr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById();
		setListener();
		init();
	}

	@Override
	protected void findViewById() {
		btnSearchIr = (Button) findViewById(R.id.btn_search_ir);
	}

	@Override
	protected void setListener() {
		btnSearchIr.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// startActivity(AddRoomActivity.class);
				startActivity(ConnectTestActivity.class);
			}
		});
	}

	@Override
	protected void init() {

	}

}
