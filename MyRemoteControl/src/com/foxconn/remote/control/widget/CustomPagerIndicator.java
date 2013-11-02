/*
 * Copyright (C) 2011 Patrik Akerfeldt
 * Copyright (C) 2011 Jake Wharton
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.foxconn.remote.control.widget;

import java.io.IOException;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.foxconn.remote.control.R;
import com.foxconn.remote.control.adapter.CustomItemProvider;
import com.foxconn.remote.control.adapter.RoomFragmentPagerAdapter;
import com.foxconn.remote.control.model.Room;
import com.foxconn.remote.control.utils.image.ImageUtils;

/**
 *  自定義的PageIndicator 
 *  實現indicator為ImageView 
 */

public class CustomPagerIndicator extends HorizontalScrollView implements
		PageIndicator {
	Runnable mTabSelector;

	// 點擊事件設置當前Tab
	private OnClickListener mTabClickListener = new OnClickListener() {
		public void onClick(View view) {
			TabView tabView = (TabView) view;
		    //設置ViewPager頁
			mViewPager.setCurrentItem(tabView.getIndex());
		}
	};

	// Indicator佈局
	private LinearLayout mTabLayout;
	private ViewPager mViewPager;
	private ViewPager.OnPageChangeListener mListener;
	private LayoutInflater mInflater;
	int mMaxTabWidth;
	private int mSelectedTabIndex;

	public CustomPagerIndicator(Context context) {
		this(context, null);
	}

	public CustomPagerIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 取消滾動Bar條
		setHorizontalScrollBarEnabled(false);
		mInflater = LayoutInflater.from(context);
		mTabLayout = new LinearLayout(getContext());
		addView(mTabLayout, new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.MATCH_PARENT));
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		final boolean lockedExpanded = widthMode == MeasureSpec.EXACTLY;
		/**
		 * 是否伸展HorizontalScrollView的內容來填滿其中的子組件
		 * 当你想让一个高度值不足scrollview的子控件fillparent的时候
		 * 单独的定义android:layout_height="fill_parent"是不起作用的,必须加上fillviewport属性
		 * 当子控件的高度值大于scrollview的高度时，这个标签就没有任何意义了
		 */
		setFillViewport(lockedExpanded);
		mMaxTabWidth = (int) (MeasureSpec.getSize(widthMeasureSpec) * 0.35f);
		// 舊的測量寬度
		final int oldWidth = getMeasuredWidth();
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// 新的測量寬度
		final int newWidth = getMeasuredWidth();
		if (lockedExpanded && oldWidth != newWidth) {
			// 重定位
			setCurrentItem(mSelectedTabIndex);
		}
	}

	private void animateToTab(final int position) {
		final View tabView = mTabLayout.getChildAt(position);
		if (mTabSelector != null) {
			removeCallbacks(mTabSelector);
		}
		mTabSelector = new Runnable() {
			public void run() {
				// 計算滑動距離
				final int scrollPos = tabView.getLeft()
						- (getWidth() - tabView.getWidth()) / 2;
				smoothScrollTo(scrollPos, 0);
				mTabSelector = null;
			}
		};
		post(mTabSelector);
	}

	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		if (mTabSelector != null) {
			// Re-post the selector we saved
			post(mTabSelector);
		}
	}

	@Override
	public void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		if (mTabSelector != null) {
			removeCallbacks(mTabSelector);
		}
	}

	private void addTab(Room item, int index) {
		// Workaround for not being able to pass a defStyle on pre-3.0
		// pre-3.0 變通方案
		final TabView tabView = (TabView) mInflater.inflate(
				R.layout.table_item, null);
		tabView.init(this, mViewPager, item, index);
		tabView.setFocusable(true);
		tabView.setOnClickListener(mTabClickListener);
		
		mTabLayout.addView(tabView, new LinearLayout.LayoutParams(0,
				LayoutParams.MATCH_PARENT, 1));
	}


	@Override
	public void onPageScrollStateChanged(int arg0) {
		if (mListener != null) {
			mListener.onPageScrollStateChanged(arg0);
		}
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		if (mListener != null) {
			mListener.onPageScrolled(arg0, arg1, arg2);
		}
	}

	@Override
	public void onPageSelected(int arg0) {
		setCurrentItem(arg0);
		if (mListener != null) {
			mListener.onPageSelected(arg0);
		}
	}

	@Override
	public void setViewPager(ViewPager view) {
		final PagerAdapter adapter = view.getAdapter();
		if (adapter == null) {
			throw new IllegalStateException(
					"ViewPager does not have adapter instance.");
		}
		if (!(adapter instanceof CustomItemProvider)) {
			throw new IllegalStateException(
					"ViewPager adapter must implement CustomItemProvider to be used with CustomPagerIndicator.");
		}
		mViewPager = view;
		view.setOnPageChangeListener(this);
		notifyDataSetChanged();
	}

	public void notifyDataSetChanged() {
		mTabLayout.removeAllViews();
		CustomItemProvider adapter = (CustomItemProvider) mViewPager
				.getAdapter();
		final int count = ((RoomFragmentPagerAdapter) adapter).getRoomlist().size();
		for (int i = 0; i < count; i++) {
			addTab(adapter.getRoomItem(i), i);
		}
		
		if (mSelectedTabIndex > count) {
			mSelectedTabIndex = count - 1;
		}
		setCurrentItem(mSelectedTabIndex);
		requestLayout();
	}

	@Override
	public void setViewPager(ViewPager view, int initialPosition) {
		setViewPager(view);
		setCurrentItem(initialPosition);
	}

	@Override
	public void setCurrentItem(int item) {
		if (mViewPager == null) {
			throw new IllegalStateException("ViewPager has not been bound.");
		}
		mSelectedTabIndex = item;
		final int tabCount = mTabLayout.getChildCount();
		for (int i = 0; i < tabCount; i++) {
			final View child = mTabLayout.getChildAt(i);
			final boolean isSelected = (i == item);
			child.setSelected(isSelected);
			if (isSelected) {
				animateToTab(item);
			}
		}
	}

	@Override
	public void setOnPageChangeListener(OnPageChangeListener listener) {
		mListener = listener;
	}

	// 自定义的TabView
	public static class TabView extends LinearLayout {
		private CustomPagerIndicator mParent;
		private int mIndex;
		// 與Tab綁定的RoomItem
		private Room tabRoomItem;

		public Room getTabRoomItem() {
			return tabRoomItem;
		}

		public TabView(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		public void init(CustomPagerIndicator parent, ViewPager pager,
				Room roomItem, int index) {
			mParent = parent;
			mIndex = index;
			tabRoomItem = roomItem;
			TextView textView = (TextView) findViewById(R.id.tv_room_name);
			textView.setText(tabRoomItem.getRoomName());
			ImageView imageView = (ImageView) findViewById(R.id.iv_room_icon);
			Bitmap bit = null;
			try {
				bit = ImageUtils.getBitmap(tabRoomItem.getImgPath(),getContext());
			} catch (IOException e) {
 				e.printStackTrace();
 				//文件不存在或已刪除
			}
			imageView.setImageBitmap(bit);
		}

		@Override
		public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
			// Re-measure if we went beyond our maximum size.
			if (mParent.mMaxTabWidth > 0
					&& getMeasuredWidth() > mParent.mMaxTabWidth) {
				super.onMeasure(MeasureSpec.makeMeasureSpec(
						mParent.mMaxTabWidth, MeasureSpec.EXACTLY),
						heightMeasureSpec);
			}
		}

		public int getIndex() {
			return mIndex;
		}
	}
}