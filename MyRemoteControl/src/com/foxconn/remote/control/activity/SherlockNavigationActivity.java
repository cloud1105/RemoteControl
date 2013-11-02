package com.foxconn.remote.control.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.foxconn.remote.control.R;
import com.foxconn.remote.control.utils.image.DrawableHelper;

public class SherlockNavigationActivity extends SherlockFragmentActivity {
	
	    private ActionBar mActionBar;
	    private TabHost mTabHost;
	    //tag標示
		private final static String TAB1 = "TAB1";
		private final static String TAB2 = "TAB2";
	    
	    public TabHost getmTabHost() {
			return mTabHost;
		}

		public void setmTabHost(TabHost mTabHost) {
			this.mTabHost = mTabHost;
		}

		@Override
	    protected void onCreate(Bundle savedInstanceState) {
	        setTheme(DrawableHelper.SHERLOCK_THEME); 
	        super.onCreate(savedInstanceState);
	        initActionBar();
	    }


		private void initActionBar() {
			mActionBar = getSupportActionBar();
			mActionBar.setDisplayShowHomeEnabled(false);
			mActionBar.setDisplayShowTitleEnabled(false);

			View v = View.inflate(getApplication(), R.layout.tab_layout, null);
			mTabHost = (TabHost) v.findViewById(android.R.id.tabhost);
			
			mTabHost.setup();
			mTabHost.addTab(mTabHost.newTabSpec(TAB1)
					.setIndicator(getTabView("轉發器")).setContent(R.id.tab1));
			mTabHost.addTab(mTabHost.newTabSpec(TAB2)
					.setIndicator(getTabView("快捷")).setContent(R.id.tab2));
			mTabHost.setOnTabChangedListener(mTabChangeListener);
			mActionBar.setCustomView(v);
			mActionBar.setDisplayShowCustomEnabled(true);
		}

	    
		private View getTabView(String tabText) {
			View view = View.inflate(this, R.layout.tab_child, null);
			view.setBackgroundResource(R.drawable.tab_indicator_ab_bwsportv3);
			TextView text = (TextView) view.findViewById(R.id.text);
			text.setText(tabText);
			return view;
		}

	    private TabHost.OnTabChangeListener mTabChangeListener = new TabHost.OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				if (TAB1.equals(tabId)) {
					Intent startHomeIntent = new Intent(SherlockNavigationActivity.this,HomeActivity.class);
					startHomeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP
					|Intent.FLAG_ACTIVITY_NO_ANIMATION);
					startActivity(startHomeIntent);
					
				} else if (TAB2.equals(tabId)) {
					Intent startShortCutIntent = new Intent(SherlockNavigationActivity.this,ShortCutActivity.class);
					startShortCutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP 
					|Intent.FLAG_ACTIVITY_NO_ANIMATION);
					startActivity(startShortCutIntent);
				}
			}
		};
		
		
}
