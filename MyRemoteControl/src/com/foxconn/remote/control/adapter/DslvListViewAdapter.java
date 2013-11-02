package com.foxconn.remote.control.adapter;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.foxconn.remote.control.R;
import com.foxconn.remote.control.model.Machine;
import com.foxconn.remote.control.utils.image.ImageUtils;
import com.foxconn.remote.control.utils.view.ViewFinder;

public class DslvListViewAdapter extends BaseAdapter {
	private final static int SIZE_HEIGHT = 40;
	private final static int SIZE_WIDTH = 40;
    private List<?> machineList;
    private Context mContext;
    
    public DslvListViewAdapter(Context context,List<Machine> list){
    	this.mContext = context;
    	this.machineList = list;
    }
    
    public void remove(int position){
    	machineList.remove(position);
    }
    
    public void add(Machine machine,int to){
    	((List<Machine>)machineList).add(to,machine);
    }
    
	
	@Override
	public int getCount() {
		if(machineList==null){
			return 0;
		}else{
		  return machineList.size();
		}
	}

	public List<?> getMachineList() {
		return machineList;
	}

	public void setMachineList(List<?> machineList) {
		this.machineList = machineList;
	}

	@Override
	public Object getItem(int position) {
		return machineList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return ((Machine)getItem(position)).getId();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		PotatoViewHolder viewHolder = null;
		Machine machine = (Machine) machineList.get(position);
		if(convertView==null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.machine_item, null);
			viewHolder = new PotatoViewHolder(convertView);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (PotatoViewHolder) convertView.getTag();
		}
		viewHolder.build(machine.getMachineName(), machine.getImg(), 
				machine.getStatus(), machine.isRemoveFlag());
		viewHolder.removeIcon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
               Toast.makeText(mContext, "刪除第"+position+"個Machine", Toast.LENGTH_SHORT).show();					
			}
		});
        return convertView;
	}
	
	/**
	 * just a potato, 
	 * i'm hungry.
	 * @author KrisLight
	 *
	 */
	private class PotatoViewHolder{ 
		private ImageView image;
		private TextView titleText;
		private ImageView onOrOffImage;
		private ImageView removeIcon;
		
		private PotatoViewHolder(View view) {
			ViewFinder finder = new ViewFinder(view);
			image = finder.imageView(R.id.machine_icon);
			titleText = finder.textView(R.id.machine_name);
			onOrOffImage = finder.imageView(R.id.machine_status);
			removeIcon = finder.imageView(R.id.machine_remove_icon);
		}
		
		public void build(String title, byte[] img,boolean status,boolean removeFlag) {
			 titleText.setText(title);
			 Bitmap bit = ImageUtils.getBitmap(img,SIZE_WIDTH,SIZE_HEIGHT);
			 image.setImageBitmap(bit);
			 if(status){
				 onOrOffImage.setImageResource(android.R.drawable.star_big_on);
			 }else{
				 onOrOffImage.setImageResource(android.R.drawable.star_big_off);
			 }
			 if(removeFlag){
				 removeIcon.setVisibility(View.VISIBLE);
			 }else{
				 removeIcon.setVisibility(View.GONE);
			 }
				 
		}

	}

}
