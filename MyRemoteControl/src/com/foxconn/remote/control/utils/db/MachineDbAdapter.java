package com.foxconn.remote.control.utils.db;

import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.foxconn.remote.control.base.BaseApplication;
import com.foxconn.remote.control.dao.DaoSession;
import com.foxconn.remote.control.dao.MachineDao;
import com.foxconn.remote.control.model.Machine;
import com.foxconn.remote.control.model.Room;

public class MachineDbAdapter {
	// TAG Variable
	private final static String TAG = "MachineDbAdapter";
	private static MachineDbAdapter instance;
	private static Context mContext;
	private static DaoSession mDaoSession;
	private static MachineDao machineDao;
	
	private MachineDbAdapter(){}
	
	public static MachineDbAdapter getInstance(Context context){
		if (instance == null) {
			instance = new MachineDbAdapter();
			if (mContext == null)
			   mContext = context.getApplicationContext();
			   mDaoSession = BaseApplication.getDaoSession(context);
			   machineDao = mDaoSession.getMachineDao();
		}
		return instance;
	}
	
	/**
	 * 得到最大的Column值
	 * COLUMN是字段名
	 */
	public int getMaxCount(){
	  Cursor cursor = machineDao.getDatabase().rawQuery("select max(COLUMN) from "+MachineDao.TABLENAME, null);
	  int maxSort = 0;
	  if (cursor != null) {
	      try {
	          if (cursor.moveToFirst() && !cursor.isNull(0)) {
	              maxSort = cursor.getInt(0);
	          }
	      } finally {
	          cursor.close();
	      }
	  }
      return maxSort;
	}
	
	public List<Machine> loadMachines(Room room){
		return machineDao._queryRoom_MachineList(room.getId());
	}
	
	public Machine loadMachineById(long id){
		return machineDao.load(id);
	}
	
	public void saveMachine(Machine machine){
	     machineDao.insertOrReplace(machine);
	}
	
	public void updateMachine(Machine...machines){
		machineDao.updateInTx(machines);
	}
	
	public SQLiteDatabase getRawDatabase(){
		return machineDao.getDatabase();
	}
	
}
