package com.foxconn.remote.control.adapter;

import java.util.List;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.foxconn.remote.control.R;

/**
 *  藍牙設備List適配器
 *  
 * @author KrisLight
 *
 */
public class MyBtListAdapter extends BaseAdapter {
	private List<BluetoothDevice> list;
	private Context mContext;
	private LayoutInflater inflater;

	public MyBtListAdapter(Context context, List<BluetoothDevice> list) {
		this.mContext = context;
		this.inflater = LayoutInflater.from(mContext);
		this.list = list;
	}

	public List<BluetoothDevice> getList() {
		return list;
	}

	public void setList(List<BluetoothDevice> list) {
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
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
		BluetoothDevice bluetoothDevice = list.get(position);
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
		try {
			device.mDeviceNameTV.setText(bluetoothDevice.getName() + "\n"
					+ bluetoothDevice.getAddress());
		} catch (Exception e) {
			// 無法得到設備名
			device.mDeviceNameTV.setText("null" + "\n"
					+ bluetoothDevice.getAddress());
		}
		return convertView;
	}

	public final class Device {
		public TextView mDeviceNameTV; // 设备名
	}
}
