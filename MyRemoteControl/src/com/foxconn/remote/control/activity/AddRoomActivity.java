package com.foxconn.remote.control.activity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import com.foxconn.remote.control.R;
import com.foxconn.remote.control.base.BaseActivity;

/**
 *  新增房間(初版用 現已廢棄)
 *  選擇圖標功能
 * 
 * @author KrisLight
 *
 */
public class AddRoomActivity extends BaseActivity {
	private ImageView ivRoomIcon;
	private Button btnNext, btnTakePhoto, btnPickImg;
	/* 用来标识请求照相功能的activity */
	private static final int CAMERA_WITH_DATA = 0x1001;
	/* 用来标识请求gallery的activity */
	private static final int PHOTO_PICKED_WITH_DATA = 0x1002;
	/* 拍照的照片存储位置 */
	private static final File PHOTO_DIR = new File(
			Environment.getExternalStorageDirectory() + "/DCIM/Camera");
	private File mCurrentPhotoFile;// 照相机拍照得到的图片
	private Uri iconUri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_room);
		findViewById();
		setListener();
		init();
	}

	@Override
	protected void findViewById() {
		ivRoomIcon = (ImageView) findViewById(R.id.iv_room_icon);
		btnPickImg = (Button) findViewById(R.id.btn_pick_img);
		btnTakePhoto = (Button) findViewById(R.id.btn_take_photo);
		btnNext = (Button) findViewById(R.id.btn_choose_ir);

	}

	@Override
	protected void setListener() {
		MyClickListner myListner = new MyClickListner();
		btnPickImg.setOnClickListener(myListner);
		btnTakePhoto.setOnClickListener(myListner);
		btnNext.setOnClickListener(myListner);
	}

	@Override
	protected void init() {

	}

	private class MyClickListner implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_pick_img:
				// 從圖庫選擇圖片
				doPickPhotoFromGallery();// 从相册中去获取
				break;
			case R.id.btn_take_photo:
				// 從照相機選擇圖片
				String status = Environment.getExternalStorageState();
				if (status.equals(Environment.MEDIA_MOUNTED)) {// 判断是否有SD卡
					doTakePhoto();// 用户点击了从照相机获取
				} else {
					showShortToast(getResourceString(R.string.no_sd_card));
				}
				break;
			case R.id.btn_choose_ir:
				// 下一步搜索轉發器
				startActivity(ConnectTestActivity.class);
				break;
			}
		}

	}

	/**
	 * 拍照获取图片
	 * 
	 */
	protected void doTakePhoto() {
		try {
			// Launch camera to take photo for selected contact
			PHOTO_DIR.mkdirs();// 创建照片的存储目录
			mCurrentPhotoFile = new File(PHOTO_DIR, getPhotoFileName());// 给新照的照片文件命名
			final Intent intent = getTakePickIntent(mCurrentPhotoFile);
			startActivityForResult(intent, CAMERA_WITH_DATA);
		} catch (ActivityNotFoundException e) {
			showShortToast("not found");
		}
	}

	public Intent getTakePickIntent(File f) {
		iconUri = Uri.fromFile(f);
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, iconUri);
		return intent;
	}

	/**
	 * 用当前时间给取得的图片命名
	 * 
	 */
	private String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date) + ".jpg";
	}

	// 请求Gallery程序
	protected void doPickPhotoFromGallery() {
		try {
			// Launch picker to choose photo for selected contact
			final Intent intent = getPhotoPickIntent();
			startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
		} catch (ActivityNotFoundException e) {
			showShortToast("not found");
		}
	}

	// 封装请求Gallery的intent
	public Intent getPhotoPickIntent() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setDataAndType(iconUri, "image/*");
		;
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 80);
		intent.putExtra("outputY", 80);
		intent.putExtra("return-data", true);
		return intent;
	}

	// 因为调用了Camera和Gally所以要判断他们各自的返回情况,他们启动时是这样的startActivityForResult
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK)
			return;
		switch (requestCode) {
		case PHOTO_PICKED_WITH_DATA: {// 调用Gallery返回的
			final Bitmap photo = data.getParcelableExtra("data");
			// 下面就是显示照片了
			// 缓存用户选择的图片
			ivRoomIcon.setImageBitmap(photo);
			break;
		}
		case CAMERA_WITH_DATA: {// 照相机程序返回的,再次调用图片剪辑程序去修剪图片
			doCropPhoto(mCurrentPhotoFile);
			break;
		}
		}
	}

	protected void doCropPhoto(File f) {
		try {
			// 启动gallery去剪辑这个照片
			final Intent intent = getCropImageIntent(Uri.fromFile(f));
			startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
		} catch (Exception e) {
			showShortToast("not found");
		}
	}

	/**
	 * Constructs an intent for image cropping. 调用图片剪辑程序
	 */
	public static Intent getCropImageIntent(Uri photoUri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(photoUri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 80);
		intent.putExtra("outputY", 80);
		intent.putExtra("return-data", true);
		return intent;
	}

}
