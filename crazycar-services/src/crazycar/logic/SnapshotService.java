package crazycar.logic;

import java.util.logging.Logger;

import org.openspaces.core.GigaSpace;
import org.openspaces.events.EventDriven;
import org.openspaces.events.EventTemplate;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.notify.Notify;
import org.openspaces.events.notify.NotifyType;

import com.j_spaces.core.client.SQLQuery;

import crazycar.Crazycar;
import crazycar.persistent.NetworkAccess;
import crazycar.persistent.spaces.RoxelSpace;

@EventDriven 
@Notify()
@NotifyType(write = true, update = true)
public class SnapshotService {
	
	Crazycar crazycar = Crazycar.start();
	
  Logger log = Logger.getLogger(this.getClass().getName());
	
	@EventTemplate
	public SQLQuery<RoxelSpace> nextRoxelTemplate(){	
		return NetworkAccess.snapshotSpaceQuery();
	}

	@SpaceDataEvent
	public void snapshot(RoxelSpace space, GigaSpace giga) {	
		log.info("snapshot " +  space);
		Crazycar.bus.post(new NetworkAccess(giga).snapshot());
		sleep(20);
	}


	void sleep(int i){
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
