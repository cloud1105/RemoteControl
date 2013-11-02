package com.foxconn.remote.control.adapter;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import com.foxconn.remote.control.R;
import com.foxconn.remote.control.model.ManchineType;
import com.foxconn.remote.control.widget.InputDialog;

public class MachineTypeAdapter extends BaseAdapter
{
	    private List<ManchineType> list;
	    private Context mContext;
	    private LayoutInflater inflater;
	    private InputDialog inputDialog;
	    private EditText et;
	    
	    public int getSelectedIndex() {
			return selectedIndex;
		}

		//選擇的項目索引
	    int selectedIndex = 0;

	    public void setSelectedIndex(int index){
	        selectedIndex = index;
	    }
		
		public List<ManchineType> getList() {
			return list;
		}


		public void setList(List<ManchineType> list) {
			this.list = list;
		}

		public MachineTypeAdapter(Context context,List<ManchineType> list){
			  this.mContext = context;
			  this.inflater = LayoutInflater.from(mContext);
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
			final ManchineType mType = list.get(position);
			Type type = null;
			if(convertView==null){
				type = new Type();
				convertView = inflater.inflate(R.layout.type_list_item, null);
				type.typeName = (TextView) convertView.findViewById(R.id.tv_type);
				type.btnCheck = (RadioButton)convertView.findViewById(R.id.btn_check);
				type.etType = (EditText) convertView.findViewById(R.id.et_type);
				type.imgEdit = (ImageView) convertView.findViewById(R.id.img_type_edit);
				convertView.setTag(type);
			}else{
				type = (Type) convertView.getTag();
			}
			
			    type.typeName.setText(mType.getTypeDes());
			
			if(selectedIndex == position){
				type.btnCheck.setChecked(true);
			}
			else{
				type.btnCheck.setChecked(false);
			}
			
			return convertView;
		}
		
		public final class Type {
			public TextView typeName;  //设备名
			public RadioButton btnCheck;//單選按鈕
			public EditText etType;//自訂擴展
			public ImageView imgEdit;
		}
		
}
