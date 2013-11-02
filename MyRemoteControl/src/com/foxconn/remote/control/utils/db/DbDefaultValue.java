package com.foxconn.remote.control.utils.db;


import com.foxconn.remote.control.R;
import com.foxconn.remote.control.dao.ManchineTypeDao;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


public class DbDefaultValue {
	
	// Insert default MachineType
    static public void insertDefaultSystemParameter(Context ctxContext, SQLiteDatabase db) {
		ContentValues insert_values = new ContentValues();
		String[] types = ctxContext.getResources().getStringArray(R.array.machine_type);
		for(String str:types){
		  insert_values.clear();
		  insert_values.put("type_des", str);
		  db.insert(ManchineTypeDao.TABLENAME, null, insert_values);
		}
    }
}
