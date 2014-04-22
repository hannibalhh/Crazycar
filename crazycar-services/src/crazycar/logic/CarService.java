package crazycar.logic;

import java.util.logging.Logger;
import org.openspaces.events.EventDriven;
import org.openspaces.events.EventTemplate;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.notify.Notify;
import org.openspaces.events.notify.NotifyType;

import com.j_spaces.core.client.SQLQuery;

import crazycar.Crazycar;
import crazycar.logic.data.Direction;
import crazycar.logic.data.Location;
import crazycar.logic.data.Roxel;
import crazycar.persistent.spaces.RoxelSpace;

@EventDriven 
@Notify
@NotifyType(write = true, update = true)
public class CarService {
	
  Logger log = Logger.getLogger(this.getClass().getName());

	private Roxel roxel = Roxel.empty(Direction.east, Location.valueOf(1, 2)); // = Crazycar.networkAccess.takeRandomRoxel();
	
	// init mit einer random location
	public CarService(){
		//TODO direction not allowed to be blocked
//		Crazycar.networkAccess.write(roxel.toCar());
		log.info("init " + roxel);
	}
	
	@EventTemplate
	public SQLQuery<RoxelSpace> nextRoxelTemplate(){
		Roxel r = roxel.nextRoxel();
		log.info("next roxel template" + r);		
		return Crazycar.networkAccess.roxelWithCarQuery(r);
	}
	
	@SpaceDataEvent
	public void stepEvent(RoxelSpace rspace){
		log.info("event with " + rspace);
		Crazycar.networkAccess.roxelWithCar(roxel.nextRoxel());
		Roxel r = rspace.toRoxel();
		Crazycar.networkAccess.releaseRoxel(roxel);
		sleep(1000);
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
