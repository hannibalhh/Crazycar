package crazycar.persistent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.openspaces.core.GigaSpace;

import com.gigaspaces.client.ChangeResult;
import com.gigaspaces.client.ChangeSet;
import com.j_spaces.core.client.SQLQuery;

import crazycar.logic.data.Car;
import crazycar.logic.data.Location;
import crazycar.logic.data.Network;
import crazycar.logic.data.Roxel;
import crazycar.logic.data.Snapshot;
import crazycar.persistent.spaces.CarSpace;
import crazycar.persistent.spaces.DirectionSpace;
import crazycar.persistent.spaces.LocationSpace;
import crazycar.persistent.spaces.RoxelSpace;

public class NetworkAccess {
	private static Logger log = Logger.getLogger(NetworkAccess.class);

	private final GigaSpace space = SpaceConfiguration.create().getInstance();
	
	public NetworkAccess(){
		log.debug("start");
	}
	
	public void cleanup(){
		SQLQuery<RoxelSpace> query = new SQLQuery<RoxelSpace>(RoxelSpace.class,"");
		space.takeMultiple(query);
	}
	
	public void save(Network n){
		for (Roxel r : n.getGrid()){
			space.write(RoxelSpace.valueOf(r));			
		}
	}
	
	public void write(Roxel r){
		space.write(RoxelSpace.valueOf(r));
	}
	
	public SQLQuery<RoxelSpace> roxelWithCarQuery(Roxel r){
		SQLQuery<RoxelSpace> query = new SQLQuery<RoxelSpace>(RoxelSpace.class,"location.row =" + r.getLocation().getRow() + " and location.column = " + r.getLocation().getColumn() + " and car.empty = true");
		return query;
	}
	
	public boolean roxelWithCar(Roxel r){
		SQLQuery<RoxelSpace> query = roxelWithCarQuery(r);
		ChangeResult<RoxelSpace> cr = space.change(query, new ChangeSet().set("car", CarSpace.valueOf(Car.ferrari)).set("direction", DirectionSpace.valueOf(r.getDirection())));
		return cr.getNumberOfChangedEntries() >= 1;
	}
	
	
	public Roxel releaseRoxel(Roxel r){
		SQLQuery<RoxelSpace> query = new SQLQuery<RoxelSpace>(RoxelSpace.class,"location.row =" + r.getLocation().getRow() + " and location.column = " + r.getLocation().getColumn());
		space.change(query, new ChangeSet().set("car", CarSpace.valueOf(Car.empty)));
		return r;
	}
	
	public SQLQuery<RoxelSpace> takeRandomRoxelQuery(){
		return new SQLQuery<RoxelSpace>(RoxelSpace.class,"car.empty = true and direction.direction != 'nodecide'");
	}
	
	public Roxel takeRandomRoxel(){
		SQLQuery<RoxelSpace> query = new SQLQuery<RoxelSpace>(RoxelSpace.class,"car.empty = true and direction.direction != 'nodecide'");
		return space.take(query).toRoxel();
	}
	
	public boolean take(Roxel r){
		return space.takeById(RoxelSpace.class,new Id(r)) != null;
	}
	
	public List<RoxelSpace> allRoxels(){
		SQLQuery<RoxelSpace> query = new SQLQuery<RoxelSpace>(RoxelSpace.class,"");
		return Arrays.asList(space.readMultiple(query));
	}
	
	public SQLQuery<RoxelSpace> snapshotSpaceQuery(){
		SQLQuery<RoxelSpace> query = new SQLQuery<RoxelSpace>(RoxelSpace.class,"car.empty = false");
		return query;
	}
	
	private List<RoxelSpace> snapshotSpace(){
		SQLQuery<RoxelSpace> query = new SQLQuery<RoxelSpace>(RoxelSpace.class,"car.empty = false");
		return Arrays.asList(space.readMultiple(query));
	}
	
	public Snapshot snapshot(){
		List<Roxel> l = new ArrayList<Roxel>();
		for(RoxelSpace r : snapshotSpace()){
			l.add(r.toRoxel());
		}
		return Snapshot.valueOf(l);
	}
	
	
}
