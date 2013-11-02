package com.foxconn.remote.control.loader;

import java.util.List;
import com.foxconn.remote.control.model.Room;
import com.foxconn.remote.control.utils.db.RoomDbAdapter;
import android.content.Context;

public class RoomListLoader extends AbstractListLoader {
    private RoomDbAdapter dbAdapter;
	
	public RoomListLoader(Context context,RoomDbAdapter adapter) {
		super(context);
		this.dbAdapter = adapter;
	}

	@Override
	protected List<Room> buildList() {
		return dbAdapter.getRoomList();
	}

}
