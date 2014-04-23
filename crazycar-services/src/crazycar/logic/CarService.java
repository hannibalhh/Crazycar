package crazycar.logic;

import java.util.logging.Logger;

import org.openspaces.core.GigaSpace;
import org.openspaces.events.EventDriven;
import org.openspaces.events.EventTemplate;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.notify.Notify;
import org.openspaces.events.notify.NotifyBatch;
import org.openspaces.events.notify.NotifyType;

import com.j_spaces.core.client.SQLQuery;

import crazycar.logic.data.Roxel;
import crazycar.persistent.NetworkAccess;
import crazycar.persistent.spaces.RoxelSpace;

@EventDriven 
@Notify
@NotifyType(write = true, update = true)
@NotifyBatch(size = 10, time = 500, passArrayAsIs = true)
public class CarService {
	
  Logger log = Logger.getLogger(this.getClass().getName());

	private Roxel roxel = new NetworkAccess().takeRandomRoxel();
	
	// init mit einer random location
	public CarService(){
		NetworkAccess n = new NetworkAccess();
		roxel = n.takeRandomRoxel();
		//TODO direction not allowed to be blocked
		n.write(roxel.toCar());
		log.info("init " + roxel);
	}
	
	@EventTemplate
	public SQLQuery<RoxelSpace> nextRoxelTemplate(){
		Roxel r = roxel.nextRoxel();
		log.info("next roxel template" + r);		
		return NetworkAccess.roxelWithCarQuery(r);
	}
	
	@SpaceDataEvent
	public void stepEvent(RoxelSpace rspace, GigaSpace giga){
		log.info("event with " + rspace);
		new NetworkAccess(giga).roxelWithCar(roxel.nextRoxel());
		Roxel r = rspace.toRoxel();
		new NetworkAccess(giga).releaseRoxel(roxel);
//		sleep(1000);
		roxel = r;
	}
	
	void sleep(int i){
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// step 
	// drive to next location	
	/*  TODO: 
	 * - Grab free next Area  
	 * - Grab exclusive new-writing Tuple 
	 * - write old location <NORTH/EAST/SOUTH/WEST/TODECIDE> (as before, every roxel hast ONE direction)
	 * - write new location BLOCKED
	 * - write new Location in FIFO
	 * - release exclusive new-writing Tuple  
	 */
}
