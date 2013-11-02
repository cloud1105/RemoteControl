package com.foxconn.remote.control.adapter;

import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.foxconn.remote.control.R;
import com.foxconn.remote.control.utils.image.DrawableHelper;
import com.foxconn.remote.control.utils.image.ImageUtils;

/**
 *  帶選擇圖標的GridView適配器 類似Gallery選擇圖片效果
 *  
 * @author KrisLight
 *
 */
public class IconAdapter extends BaseAdapter {

	// 標記選擇的圖標position
	private int mSelectedPosition;
	private String[] mThumbPaths;
	private int mIconSize;
	private Context mContext;

	public IconAdapter(Context context,String[] icons) {
		mThumbPaths = icons;
		mIconSize = context.getResources().getDimensionPixelSize(
				R.dimen.default_icon_size);
		mContext = context;
	}

	@Override
	public int getCount() {
		return mThumbPaths.length;
	}

	@Override
	public Object getItem(int position) {
		return mThumbPaths[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) {
			imageView = new ImageView(mContext);
			imageView.setLayoutParams(new GridView.LayoutParams(mIconSize,
					mIconSize));
		} else {
			imageView = (ImageView) convertView;
		}
		Bitmap background = null;
		try {
			background = ImageUtils.getBitmap(mThumbPaths[position], mContext);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (mSelectedPosition == position) {
			imageView.setImageDrawable(mergeDrawableLayers(new BitmapDrawable(background),
					R.drawable.ic_note_selected_mark));
		} else {
			imageView.setImageBitmap(background);
		}

		return imageView;
	}

	public void setSelectedItemPosition(int position) {
		mSelectedPosition = position;
		notifyDataSetChanged();
	}

	public int getSelectedPosition() {
		return mSelectedPosition;
	}

	/**
	 * 實現圖片重疊
	 * 
	 * @param background
	 *            背景圖片resId
	 * @param overlay
	 *            前景圖片resId
	 * @author: KrisLight
	 */
	private Drawable mergeDrawableLayers(BitmapDrawable background, int overlay) {
		Drawable[] drawableLayers = new Drawable[2];

		drawableLayers[0] = background;
		drawableLayers[1] = mContext.getResources().getDrawable(overlay);

		return new LayerDrawable(drawableLayers);
	}

}
