package com.foxconn.remote.control.adapter;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.foxconn.remote.control.model.Room;

public class RoomListViewAdapter extends BaseAdapter {
    private List<Room> roomList;
    private Context mContext;
	
	public RoomListViewAdapter(Context context,List<Room> list){
		this.roomList = list;
		this.mContext = context;
	}
    
	@Override
	public int getCount() {
		return roomList.size();
	}

	@Override
	public Object getItem(int position) {
		return roomList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return roomList.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1, null);
		}
		 TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
		 tv.setText(roomList.get(position).getRoomName());
		return convertView;
	}

}
