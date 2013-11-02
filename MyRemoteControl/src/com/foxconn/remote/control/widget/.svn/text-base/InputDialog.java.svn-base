package com.foxconn.remote.control.widget;

import com.foxconn.remote.control.R;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class InputDialog extends AlertDialog {
	
	private Button btnCancel,btnDelete,btnConfirm;
	private EditText etContent;
	private android.view.View.OnClickListener onCancelListener,onDeleteListener,onConfirmListener;
	private String mContent;

	public android.view.View.OnClickListener getOnCancelListener() {
		return onCancelListener;
	}

	public void setOnCancelListener(
			android.view.View.OnClickListener onCancelListener) {
		this.onCancelListener = onCancelListener;
	}

	public android.view.View.OnClickListener getOnDeleteListener() {
		return onDeleteListener;
	}

	public void setOnDeleteListener(
			android.view.View.OnClickListener onDeleteListener) {
		this.onDeleteListener = onDeleteListener;
	}

	public android.view.View.OnClickListener getOnConfirmListener() {
		return onConfirmListener;
	}

	public void setOnConfirmListener(
			android.view.View.OnClickListener onConfirmListener) {
		this.onConfirmListener = onConfirmListener;
	}

	public InputDialog(Context context) {
		super(context);
	}
	
	public InputDialog(Context context,String content) {
		super(context);
		this.mContent = content;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.input_dialog);
		btnCancel = (Button) findViewById(R.id.btn_cancel);
		btnDelete = (Button) findViewById(R.id.btn_delete);
		btnConfirm = (Button) findViewById(R.id.btn_confirm);
		etContent = (EditText) findViewById(R.id.content);
		if(mContent!=null){
			etContent.setText(mContent);
		}
		btnCancel.setOnClickListener(onCancelListener);
		btnDelete.setOnClickListener(onDeleteListener);
		btnConfirm.setOnClickListener(onConfirmListener);
	}

}
