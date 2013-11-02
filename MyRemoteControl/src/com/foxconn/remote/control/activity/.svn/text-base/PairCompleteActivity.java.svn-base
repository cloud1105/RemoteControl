package com.foxconn.remote.control.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.foxconn.remote.control.R;
import com.foxconn.remote.control.base.BaseActivity;

/**
 *  配對介面 (初版用 已廢棄)
 * @author KrisLight
 *
 */
public class PairCompleteActivity extends BaseActivity {

	private Button btnAddAnother, btnAddComplete;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pair_complete);
		findViewById();
		setListener();
		init();
	}

	@Override
	protected void findViewById() {
		btnAddAnother = (Button) findViewById(R.id.btn_add_other_room);
		btnAddComplete = (Button) findViewById(R.id.btn_complete);
	}

	@Override
	protected void setListener() {

	}

	@Override
	protected void init() {

	}

	private class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_add_other_room:
				// 新增其他房間

				break;
			case R.id.btn_complete:
				// 新增完成,開始操作,顯示電器清單

				break;
			}
		}

	}
}
