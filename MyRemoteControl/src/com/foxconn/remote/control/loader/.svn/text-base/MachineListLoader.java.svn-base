package com.foxconn.remote.control.loader;

import java.util.List;
import com.foxconn.remote.control.model.Machine;
import com.foxconn.remote.control.model.Room;
import com.foxconn.remote.control.utils.db.MachineDbAdapter;
import android.content.Context;

public class MachineListLoader extends AbstractListLoader {
	private MachineDbAdapter dbAdapter;
	private Room mRoom;
	
	public MachineListLoader(Context context,MachineDbAdapter adapter,Room room) {
		super(context);
		this.dbAdapter = adapter;
		this.mRoom = room;
	}

	@Override
	protected List<Machine> buildList() {
		return dbAdapter.loadMachines(mRoom);
	}

}
