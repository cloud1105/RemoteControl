package com.foxconn.remote.control.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.foxconn.remote.control.fragment.DragListFragment;
import com.foxconn.remote.control.model.Room;

/**
 *  ViewPager中的電器List列表適配器
 * 
 * @author KrisLight
 *
 */
public class RoomFragmentPagerAdapter extends FragmentStatePagerAdapter implements
		CustomItemProvider {
	private List<Room> roomlist;
	private Context mContext;
	private Activity activity;

	public RoomFragmentPagerAdapter(FragmentManager fm, List<Room> list,Activity activity,
			Context context) {
		super(fm);
		this.roomlist = list;
		this.mContext = context;
		this.activity = activity;
	}

	public List<Room> getRoomlist() {
		return roomlist;
	}

	public void setRoomlist(List<Room> roomlist) {
		this.roomlist = roomlist;
		notifyDataSetChanged();
	}

	@Override
	public Fragment getItem(int position) {
		// 新建一個DragListFragment
		return DragListFragment.newInstance(roomlist.get(position),activity,mContext);
	}

	@Override
	public int getCount() {
		return roomlist.size();
	}

	/**
	 * 要想notifyDataSetChanged生效,需手動實現此方法 并根據條件返回POSITION_NONE based on
	 * http://stackoverflow
	 * .com/questions/10849552/android-viewpager-cant-update-
	 * dynamically/10852046#10852046
	 */
	@Override
	public int getItemPosition(Object item) {
		return POSITION_NONE;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return roomlist.get(position).getRoomName();
	}

	@Override
	public Room getRoomItem(int position) {
		return roomlist.get(position);
	}

}