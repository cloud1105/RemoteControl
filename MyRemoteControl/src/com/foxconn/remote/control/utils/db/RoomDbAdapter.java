package com.foxconn.remote.control.utils.db;

import java.util.List;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.foxconn.remote.control.base.BaseApplication;
import com.foxconn.remote.control.dao.DaoSession;
import com.foxconn.remote.control.dao.MachineDao;
import com.foxconn.remote.control.dao.RoomDao;
import com.foxconn.remote.control.model.Room;

public class RoomDbAdapter {
	// TAG Variable
	private final static String TAG = "RoomDbAdapter";
	private static RoomDbAdapter instance;
	private static Context mContext;
	private static DaoSession mDaoSession;
	private static RoomDao roomDao;
	private static MachineDao machineDao;
	
	private RoomDbAdapter(){}
	
	public static RoomDbAdapter getInstance(Context context){
		if (instance == null) {
			instance = new RoomDbAdapter();
			 if (mContext == null){
			   mContext = context.getApplicationContext();
			 }
			   mDaoSession = BaseApplication.getDaoSession(mContext);
			   roomDao = mDaoSession.getRoomDao();
			   machineDao = mDaoSession.getMachineDao();
		}
		return instance;
	}
	
	public List<Room> getRoomList(){
		return roomDao.loadAll();
	}
	
	public Room loadRoomById(long id){
		return roomDao.load(id);
	}
	
	public void saveRoom(Room room){
		 roomDao.insertOrReplaceInTx(room);
	}
	
	public void updateRoom(Room room){
		roomDao.updateInTx(room);
	}
	
	public void deleteRoomsById(final List<Long> roomIds){
		Runnable deleteThread = new Runnable() {
			@Override
			public void run() {
				for(long id : roomIds){
					Room room = roomDao.load(id);
					machineDao.deleteInTx(room.getMachineList());
				}
				roomDao.deleteByKeyInTx(roomIds);
			}
		};
		mDaoSession.runInTx(deleteThread);
	}
	
	public void deleteRooms(final Room... rooms){
	}
	
	public List<Room> rawQuery(String where,String... selectionArg){
		return roomDao.queryRaw(where, selectionArg);
	}
	
	public SQLiteDatabase getRawDatabase(){
		return roomDao.getDatabase();
	}
	
}
