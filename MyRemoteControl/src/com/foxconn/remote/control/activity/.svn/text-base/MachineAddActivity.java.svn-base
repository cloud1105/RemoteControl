package com.foxconn.remote.control.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.foxconn.remote.control.R;
import com.foxconn.remote.control.base.BaseActivity;
import com.foxconn.remote.control.fragment.DragListFragment;
import com.foxconn.remote.control.model.Machine;
import com.foxconn.remote.control.utils.db.MachineDbAdapter;
import com.foxconn.remote.control.utils.image.ImageUtils;

public class MachineAddActivity extends BaseActivity implements OnClickListener{

	private final static String TAG = MachineAddActivity.class.getSimpleName();
	//request code
	public final static int TYPE_REQUEST_CODE = 0x123;
	public final static int ICON_REQUEST_CODE = 0x234;
    private final static int ACTIVITY_RESULT_CAMERA_WITH_DATA = 0x1234;
	private final static int ACTIVITY_RESULT_CROPIMAGE_WITH_DATA = 0x1235;
	private long room_id;
	private File picFile;
	private Uri photoUri;
	private ImageView ivChooseHeaderIcon;
    private EditText etMachineName,etMachineType,etMachineBrand,etMachineSn;
    private TextView tvBarcodeScan;
    private MachineDbAdapter dbAdapter;
    private Bitmap storeBitmap;
	
    @Override
    protected void onResume() {
	   super.onResume();
	   Intent i = getIntent();
	   if(i!=null && i.getStringExtra(MachineTypeActivity.MACHINE_TYPE)!=null){
		  String type = i.getStringExtra(MachineTypeActivity.MACHINE_TYPE);
		  System.out.println("Type"+type);
		  etMachineType.setText(type);
	   }
    }
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_machine_add);
		findViewById();
		setListener();
		init();
	}

	@Override
	protected void findViewById() {
		ivChooseHeaderIcon = finder.find(R.id.iv_machine_icon);
		etMachineName = finder.find(R.id.et_machine_name);
		etMachineType = finder.find(R.id.et_machine_type);
		etMachineBrand = finder.find(R.id.et_machine_brand);
		etMachineSn = finder.find(R.id.et_machine_sn);
		tvBarcodeScan = finder.find(R.id.tv_barcode_scan);
	}

	@Override
	protected void setListener() {
		ivChooseHeaderIcon.setOnClickListener(this);
		etMachineType.setOnClickListener(this);
		tvBarcodeScan.setOnClickListener(this);
	}

	@Override
	protected void init() {
		Intent addIntent = getIntent();
		if(addIntent != null && addIntent.getExtras()!=null){
			room_id = addIntent.getExtras().getLong(DragListFragment.ROOM_ID);
		}else{
			Log.e(TAG, "傳過來的ROOM ID為空");
		}
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		dbAdapter = MachineDbAdapter.getInstance(this);
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
	        menu.add(0,0,0,"Save")
	            .setIcon(R.drawable.c_btn_ok)
	            .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS|MenuItem.SHOW_AS_ACTION_WITH_TEXT);
	        return true;
	}
	
	/* (non-Javadoc)
	 * @see com.actionbarsherlock.app.SherlockActivity#onMenuItemSelected(int, com.actionbarsherlock.view.MenuItem)
	 */
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		  switch (item.getItemId()) {
		  case android.R.id.home:
			finish();
			return true;
		  case 0:
			String machineName = etMachineName.getText().toString();
			String machineType = etMachineType.getText().toString();
			String machineBrand = etMachineBrand.getText().toString();
			String machineSn = etMachineSn.getText().toString();
			if(TextUtils.isEmpty(machineName)){
				etMachineName.setError("名字不能為空");
				return true;
			}else if(TextUtils.isEmpty(machineType)){
				etMachineType.setError("類型不能為空");
				return true;
			}else if(TextUtils.isEmpty(machineBrand)){
				etMachineBrand.setError("品牌不能為空");
				return true;
			}else if(TextUtils.isEmpty(machineSn)){
				etMachineSn.setError("序號不能為空");
				return true;
			}else if(ivChooseHeaderIcon.getDrawable()==null){
				showShortToast("圖片不能為空,請設置圖片后再保存");
				return true;
			}
			Machine machineToAdd = new Machine();
			machineToAdd.setRoomId(room_id);
			machineToAdd.setMachineName(machineName);
			machineToAdd.setMachineType(machineType);
			machineToAdd.setMachineBrand(machineBrand);
			machineToAdd.setSerialNo(machineSn);
			machineToAdd.setImg(ImageUtils.Bitmap2Bytes(storeBitmap));
			//開關狀態
			machineToAdd.setStatus(false);
			machineToAdd.setColumn(dbAdapter.getMaxCount()+1);
			dbAdapter.saveMachine(machineToAdd);
			startActivity(HomeActivity.class);
			return true;
		  }
		  return false;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_machine_icon:
			Intent iconIntent = new Intent(MachineAddActivity.this, MachineIconActivity.class);
			startActivityForResult(iconIntent,ICON_REQUEST_CODE);
			break;
		case R.id.et_machine_type:
			Intent typeIntent = new Intent(MachineAddActivity.this, MachineTypeActivity.class);
			startActivityForResult(typeIntent,TYPE_REQUEST_CODE);
			break;
		case R.id.tv_barcode_scan:
			//掃描二維碼 
			startActivity(BarCodeInActivity.class);
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK ){
		  switch (requestCode) {
			case TYPE_REQUEST_CODE://選擇類型
				 etMachineType.setText(data.getStringExtra(MachineTypeActivity.MACHINE_TYPE));
				break;
			case ICON_REQUEST_CODE://選擇圖標
				Bitmap iconBit = (Bitmap)data.getParcelableExtra(MachineIconActivity.ICON_DRAWABLE);
				storeBitmap = iconBit;
				ivChooseHeaderIcon.setImageBitmap(iconBit);
				break;
			case ACTIVITY_RESULT_CAMERA_WITH_DATA: //拍照
				try {
					cropImageUriByTakePhoto();
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case ACTIVITY_RESULT_CROPIMAGE_WITH_DATA://圖庫選擇
					try {
						if (photoUri != null) {
							Bitmap bitmap = decodeUriAsBitmap(photoUri);
							storeBitmap = bitmap;
							ivChooseHeaderIcon.setImageBitmap(bitmap);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				break;
			}
		}else if(resultCode == RESULT_CANCELED){
		  if(requestCode == ICON_REQUEST_CODE && data != null){
			    String action = data.getStringExtra(MachineIconActivity.ACTION);
			    if("camera".equals(action)){
			      //拍照
				  String status = Environment.getExternalStorageState();
			   	  if (status.equals(Environment.MEDIA_MOUNTED)) {
					doTakePhoto();
				  }
			    }else if("crop".equals(action)){
			    	//從圖庫選擇
					doCropPhoto();
			    }
		  }
		}
		
	}
	
	public void doTakePhoto() {
		try {
			File uploadFileDir = new File(Environment.getExternalStorageDirectory(), "/upload");
			Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			if (!uploadFileDir.exists()) {
				uploadFileDir.mkdirs();
			}
			picFile = new File(uploadFileDir, "upload.jpeg");
			if (!picFile.exists()) {
				picFile.createNewFile();
			}
			photoUri = Uri.fromFile(picFile);
			cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
			startActivityForResult(cameraIntent, ACTIVITY_RESULT_CAMERA_WITH_DATA);
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void doCropPhoto() {
		try {
			File pictureFileDir = new File(Environment.getExternalStorageDirectory(), "/upload");
			if (!pictureFileDir.exists()) {
				pictureFileDir.mkdirs();
			}
			picFile = new File(pictureFileDir, "upload.jpeg");
			if (!picFile.exists()) {
				picFile.createNewFile();
			}
			photoUri = Uri.fromFile(picFile);
			final Intent intent = getCropImageIntent();
			startActivityForResult(intent, ACTIVITY_RESULT_CROPIMAGE_WITH_DATA);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Intent getCropImageIntent() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setType("image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 600);
		intent.putExtra("outputY", 600);
		intent.putExtra("noFaceDetection", true);
		intent.putExtra("scale", true);
		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		return intent;
	}
	
	private void cropImageUriByTakePhoto() {
		 Intent intent = new Intent("com.android.camera.action.CROP");
		 intent.setDataAndType(photoUri, "image/*");
		 intent.putExtra("crop", "true");
		 intent.putExtra("aspectX", 1);
		 intent.putExtra("aspectY", 1);
		 intent.putExtra("outputX", 600);
		 intent.putExtra("outputY", 600);
		 intent.putExtra("scale", true);
		 intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
		 intent.putExtra("return-data", false);
		 intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		 intent.putExtra("noFaceDetection", true); // no face detection
		 startActivityForResult(intent, ACTIVITY_RESULT_CROPIMAGE_WITH_DATA);
	}
	
	private Bitmap decodeUriAsBitmap(Uri uri) {
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		//彈出是否保存資料對話框
	}
	
}
