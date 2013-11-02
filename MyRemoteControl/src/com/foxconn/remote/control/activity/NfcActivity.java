package com.foxconn.remote.control.activity;

import java.nio.charset.Charset;
import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.TextView;
import android.widget.Toast;

import com.foxconn.remote.control.R;

/**
 * NFC搜索
 * 继承并实现接口CreateNdefMessageCallback方法createNdefMessage
 * (初版用 已廢棄 未來NFC需要再用)
 * 
 * @author KrisLight
 */

public class NfcActivity extends Activity 
//implements CreateNdefMessageCallback 
{
	private NfcAdapter mNfcAdapter;
	private TextView textView;
	private static final int MESSAGE_SENT = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nfc);
		textView = (TextView) findViewById(R.id.textView);
		// 检测是否有NFC适配器
//		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		if (mNfcAdapter == null) {
			Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG)
					.show();
			finish();
			return;
		}
		// Register callback
		// 注册回调函数
//		mNfcAdapter.setNdefPushMessageCallback(this, this);
	}

//	@Override
//	public NdefMessage createNdefMessage(NfcEvent event) {
//		String text = ("Beam me up, Android!\n\n" + "Beam Time: " + System
//				.currentTimeMillis());
//		// 回调函数，构造NdefMessage格式
//		NdefMessage msg = new NdefMessage(new NdefRecord[] { createMimeRecord(
//				"application/com.example.android.beam", text.getBytes())
//		/**
//		 * The Android Application Record (AAR) is commented out. When a device
//		 * receives a push with an AAR in it, the application specified in the
//		 * AAR is guaranteed to run. The AAR overrides the tag dispatch system.
//		 * You can add it back in to guarantee that this activity starts when
//		 * receiving a beamed message. For now, this code uses the tag dispatch
//		 * system.
//		 */
//		// ,NdefRecord.createApplicationRecord("com.example.android.beam")
//				});
//		return msg;
//	}

	@Override
	public void onResume() {
		super.onResume();
		// Check to see that the Activity started due to an Android Beam
		// 得到是否检测到ACTION_NDEF_DISCOVERED触发
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
//			processIntent(getIntent());
		}
	}

	// 重载Activity类方法处理当新Intent到来事件
	@Override
	public void onNewIntent(Intent intent) {
		// onResume gets called after this to handle the intent
		setIntent(intent);
	}

	/**
	 * Parses the NDEF Message from the intent and prints to the TextView
	 */
	// 关键处理函数，处理扫描到的NdefMessage
//	void processIntent(Intent intent) {
//		textView = (TextView) findViewById(R.id.textView);
//		Parcelable[] rawMsgs = intent
//				.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
//		// only one message sent during the beam
//		NdefMessage msg = (NdefMessage) rawMsgs[0];
//		// record 0 contains the MIME type, record 1 is the AAR, if present
//		textView.setText(new String(msg.getRecords()[0].getPayload()));
//	}
//
//	/**
//	 * Creates a custom MIME type encapsulated in an NDEF record
//	 */
//	public NdefRecord createMimeRecord(String mimeType, byte[] payload) {
//		byte[] mimeBytes = mimeType.getBytes(Charset.forName("US-ASCII"));
//		NdefRecord mimeRecord = new NdefRecord(NdefRecord.TNF_MIME_MEDIA,
//				mimeBytes, new byte[0], payload);
//		return mimeRecord;
//	}

}
