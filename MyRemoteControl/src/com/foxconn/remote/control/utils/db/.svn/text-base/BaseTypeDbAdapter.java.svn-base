package com.foxconn.remote.control.utils.db;

import java.util.List;
import android.content.Context;
import com.foxconn.remote.control.base.BaseApplication;
import com.foxconn.remote.control.dao.DaoSession;
import com.foxconn.remote.control.dao.ManchineTypeDao;
import com.foxconn.remote.control.model.ManchineType;

public class BaseTypeDbAdapter {
	// TAG Variable
	private final static String TAG = "MachineDbAdapter";
	private static BaseTypeDbAdapter instance;
	private static Context mContext;
	private static DaoSession mDaoSession;
	private static ManchineTypeDao typeDao;
    private BaseTypeDbAdapter(){}
	
	public static BaseTypeDbAdapter getInstance(Context context){
		if (instance == null) {
			instance = new BaseTypeDbAdapter();
			if (mContext == null)
			   mContext = context.getApplicationContext();
			   mDaoSession = BaseApplication.getDaoSession(context);
			   typeDao = mDaoSession.getManchineTypeDao();
		}
		return instance;
	}
	
	public List<ManchineType> loadTypes(){
		return typeDao.loadAll();
	}
	
	public void save(ManchineType type){
		typeDao.insertOrReplaceInTx(type);
	}
	
	public void update(ManchineType type){
		typeDao.updateInTx(type);
	}
	
	public void delete(long id){
		typeDao.deleteByKeyInTx(id);
	}
}
