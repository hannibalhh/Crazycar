package crazycar.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.GameGrid;
import ch.aplu.jgamegrid.Location;

import com.google.common.eventbus.Subscribe;

import crazycar.gui.actors.CarActor;
import crazycar.gui.actors.Crossroads;
import crazycar.gui.actors.NoRoad;
import crazycar.gui.actors.RoadNS;
import crazycar.gui.actors.RoadWE;
import crazycar.logic.data.Direction;
import crazycar.logic.data.Network;
import crazycar.logic.data.Roxel;
import crazycar.logic.data.Snapshot;

public class CrazycarGUI extends GameGrid {

	private static final Logger log = Logger.getLogger(CrazycarGUI.class);

	// imagesize of cells in px
	public final static int imagesize = 25;
	private List<CarActor> cars = new ArrayList<CarActor>();
	private List<CarActor> tempcars = new ArrayList<CarActor>();

	private static final long serialVersionUID = 7378323006789259694L;

	public void addActor(Actor a) {
		// only one actor in one grid
		removeActorsAt(a.getLocation());
		super.addActor(a, a.getLocation());
	}

	public CrazycarGUI() {
		super(50, 18, imagesize, java.awt.Color.black);
		setTitle("Crazycar");
		doRun();
	}

	private Actor newCarAndSave(Roxel r) {
		CarActor c = new CarActor();
		addActor(c, CarActor.convertLocation(r.getLocation()));
		c.changeRoxel(r);
		tempcars.add(c);
		return c;
	}

	@Subscribe
	public void moveListener(Snapshot s) {
		log.debug("snapshot: " + s.getRoxels());
		Iterator<CarActor> it = cars.iterator();
		// initialized cars and size not equals
		if (cars.size() == 0 && cars.size() != s.getRoxels().size()) {
			log.error("Snapshot: roxels not quals cars size: cars:" + cars.size() + " roxels:" + s.getRoxels());
		}
		for (Roxel r : s.getRoxels()) {
			if (it.hasNext()) {
				it.next().changeRoxel(r);
			} else {
				addActor(newCarAndSave(r));
			}
		}
		cars.addAll(tempcars);
		tempcars.clear();
	}

	@Subscribe
	public void initListener(Network e) {
		System.out.println(e);
		setNbHorzCells(e.getSize().getColumn());
		setNbVertCells(e.getSize().getRow());
		for (Roxel l : e.getGrid()) {
			if (l.getDirection().equals(Direction.south) || l.getDirection().equals(Direction.north))
				addActor(new RoadNS(), new Location(l.getLocation().getColumn(), l.getLocation().getRow()));
			else if (l.getDirection().equals(Direction.east) || l.getDirection().equals(Direction.west))
				addActor(new RoadWE(), new Location(l.getLocation().getColumn(), l.getLocation().getRow()));
			else
				addActor(new Crossroads(), new Location(l.getLocation().getColumn(), l.getLocation().getRow()));
		}
		for (Location l : getEmptyLocations()) {
			addActor(new NoRoad(), l);
		}
		show();
		setVisible(true);
	}

	public static String imagefolder() {
		return new File("").getAbsolutePath() + File.separator + "images" + File.separator + imagesize + File.separator;
	}
}