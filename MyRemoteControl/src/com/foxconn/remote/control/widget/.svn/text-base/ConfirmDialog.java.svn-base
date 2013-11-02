package com.foxconn.remote.control.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import com.foxconn.remote.control.R;

public class ConfirmDialog extends AlertDialog {

	private Button btnLeave,btnNotLeave;
	private android.view.View.OnClickListener onLeaveListener,onNotLeaveListener;
	
	public android.view.View.OnClickListener getOnLeaveListener() {
		return onLeaveListener;
	}

	public void setOnLeaveListener(android.view.View.OnClickListener onLeaveListener) {
		this.onLeaveListener = onLeaveListener;
	}

	public android.view.View.OnClickListener getOnNotLeaveListener() {
		return onNotLeaveListener;
	}


	public void setOnNotLeaveListener(
			android.view.View.OnClickListener onNotLeaveListener) {
		this.onNotLeaveListener = onNotLeaveListener;
	}


	public ConfirmDialog(Context context) {
		super(context);
	}
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.confirm_dialog);
		btnLeave = (Button) findViewById(R.id.btn_leave);
		btnNotLeave = (Button) findViewById(R.id.btn_not_leave);
		btnLeave.setOnClickListener(onLeaveListener);
		btnNotLeave.setOnClickListener(onNotLeaveListener);
	}
	
}
