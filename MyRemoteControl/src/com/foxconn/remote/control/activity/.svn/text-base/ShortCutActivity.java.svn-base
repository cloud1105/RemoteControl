package com.foxconn.remote.control.activity;

import android.os.Bundle;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.foxconn.remote.control.R;

/**
 * 快捷介面
 * @author KrisLight
 *
 */
public class ShortCutActivity extends SherlockNavigationActivity {
    private static final int MENU_ADD = Menu.FIRST;
	private static final int MENU_EDIT = Menu.FIRST + 1;
	private static final int MENU_ADD_GROUP = Menu.FIRST + 2;
	private static final int MENU_SAVE = Menu.FIRST + 3;
	private static final int MENU_LOAD = Menu.FIRST + 4;
	private static final int MENU_INFO = Menu.FIRST + 5;
	private static final int MENU_OTHER = Menu.FIRST + 6;
	
	@Override
	protected void onStart() {
		super.onStart();
		getmTabHost().setCurrentTab(1);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_short_cut);
	}

	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	    	
	    	    SubMenu subMenuRoom = menu.addSubMenu("Overflow Item");
	    	    subMenuRoom.add(0, MENU_ADD, Menu.NONE, "新增快捷");
		        subMenuRoom.add(0, MENU_EDIT, Menu.NONE, "編輯快捷");
		        subMenuRoom.add(0, MENU_ADD_GROUP, Menu.NONE, "新增快捷組合");
		        subMenuRoom.add(0, MENU_SAVE, Menu.NONE, "備份設定");
		        subMenuRoom.add(0, MENU_LOAD, Menu.NONE, "載入備份設定");
		        subMenuRoom.add(0, MENU_INFO, Menu.NONE, "操作說明");
		        subMenuRoom.add(0, MENU_OTHER, Menu.NONE, "廠牌支援");
		        
		        MenuItem subMenuRoomItem = subMenuRoom.getItem();
		        subMenuRoomItem.setIcon(R.drawable.c_btn_setting);

		        subMenuRoomItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		        return super.onPrepareOptionsMenu(menu);
	    }
	    

}
