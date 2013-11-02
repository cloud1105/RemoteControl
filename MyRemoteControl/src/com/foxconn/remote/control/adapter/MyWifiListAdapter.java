package com.foxconn.remote.control.adapter;

import java.util.List;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;
import com.foxconn.remote.control.R;

/**
 *  Wifi設備適配器
 * 
 * @author KrisLight
 *
 */
public class MyWifiListAdapter extends BaseAdapter {
	private List<ScanResult> list;
	private Context mContext;
	private LayoutInflater inflater;

	public MyWifiListAdapter(Context context, List<ScanResult> list) {
		this.mContext = context;
		this.inflater = LayoutInflater.from(mContext);
		this.list = list;
	}

	public List<ScanResult> getList() {
		return list;
	}

	public void setList(List<ScanResult> list) {
		this.list = list;
	}

	@Override
	public int getCount() {
		if(list !=null){
		 return list.size();
		}else{
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ScanResult result = list.get(position);
		Device device = null;
		if (convertView == null) {
			device = new Device();
			convertView = inflater.inflate(R.layout.device_list_item, null);
			device.mDeviceNameTV = (TextView) convertView
					.findViewById(R.id.tv_device);
			convertView.setTag(device);
		} else {
			device = (Device) convertView.getTag();
		}
		device.mDeviceNameTV.setText(result.SSID + "\n" + result.BSSID);
		return convertView;
	}

	public final class Device {
		public TextView mDeviceNameTV; // 设备名
	}
}
