package com.foxconn.remote.control.fragment;

import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import com.foxconn.remote.control.R;
import com.foxconn.remote.control.activity.MachineAddActivity;
import com.foxconn.remote.control.adapter.DslvListViewAdapter;
import com.foxconn.remote.control.base.BaseApplication;
import com.foxconn.remote.control.base.BaseFragment;
import com.foxconn.remote.control.loader.MachineListLoader;
import com.foxconn.remote.control.model.Machine;
import com.foxconn.remote.control.model.Room;
import com.foxconn.remote.control.utils.db.MachineDbAdapter;
import com.mobeta.android.dslv.DragSortListView;

/**
 * 電器UI主介面
 * @author KrisLight
 */
public final class DragListFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<List<?>>{
	 public final static String ROOM_ID = "roomId"; 
	 private Room room;
	 private DragSortListView mDslv;
	 private DslvListViewAdapter listAdapter;
	 private static BaseApplication application = BaseApplication.getInstance();
	 private MachineDbAdapter dbAdapter;
	 //房間名稱
	 private TextView ivRoomTitle;
	 //加號按鈕
     private ImageView ivAdd;
     
     private DragSortListView.DropListener onDrop = new DragSortListView.DropListener()
     {
         @Override
         public void drop(int from, int to)
         {
             if (from != to)
             {
                 Machine fromItem = (Machine)listAdapter.getItem(from);
                 listAdapter.remove(from);
            	 listAdapter.add(fromItem, to);
                 fromItem.setColumn(to);
                 Machine toItem = (Machine) listAdapter.getItem(to);
                 toItem.setColumn(from);
                 //更新順序
                 dbAdapter.updateMachine(fromItem,toItem);
         		 //加載數據源
                 listAdapter.notifyDataSetChanged();
                 
             }
         }
     };

     private DragSortListView.RemoveListener onRemove = new DragSortListView.RemoveListener()
     {
         @Override
         public void remove(int which)
         {
        	 
        	 showShortToast("Remove");
         }
     };


     
    //getter and setter
 	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}
	
	public DragListFragment(){}
     
	 public DragListFragment(BaseApplication application, Activity activity,
				Context context){
		 super(application,activity,context);
		 dbAdapter = MachineDbAdapter.getInstance(context);
	 }
	 
     public static DragListFragment newInstance(Room room,Activity activity,Context context) {
    	 DragListFragment fragment = new DragListFragment(application,activity,context);
 		 fragment.room = room;
 		 return fragment;
 	 }

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//使用Loader從數據庫查詢電器列表Cursor
	}
     
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		inflater = LayoutInflater.from(getActivity());
		LinearLayout layout = (LinearLayout) inflater.inflate(
				R.layout.activity_list, container, false);
		mDslv = (DragSortListView)layout.findViewById(R.id.item_list);
		ivRoomTitle = (TextView) layout.findViewById(R.id.imvTitle);
		ivAdd = (ImageView) layout.findViewById(R.id.imvAdd);
		ivRoomTitle.setText(room.getRoomName());
		return layout;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
 	    ivAdd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//新增電器介面
				Bundle b = new Bundle();
				b.putLong(ROOM_ID, room.getId());
				startActivity(MachineAddActivity.class,b);
			}
		});
 	    
 	    //先放空數據源
		listAdapter = new DslvListViewAdapter(mContext, null);
		mDslv.setAdapter(listAdapter);
		mDslv.setDropListener(onDrop);
		mDslv.setRemoveListener(onRemove);
		mDslv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//顯示遙控器介面
			}
		});
		//加載數據源
		getLoaderManager().initLoader(0, null, this);
		
	}

	@Override
	public Loader<List<?>> onCreateLoader(int arg0, Bundle arg1) {
		return new MachineListLoader(mContext, dbAdapter, room);
	}

	@Override
	public void onLoadFinished(Loader<List<?>> arg0, List<?> list) {
		listAdapter.setMachineList(list);
		listAdapter.notifyDataSetChanged();
	}

	@Override
	public void onLoaderReset(Loader<List<?>> arg0) {
		mDslv.setAdapter(null);
	}





}
