package crazycar;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.google.common.eventbus.EventBus;

import crazycar.gui.CrazycarGUI;
import crazycar.logic.data.Location;
import crazycar.logic.data.Network;
import crazycar.persistent.NetworkAccess;

public class Crazycar {

	private static final Logger log = Logger.getLogger(Crazycar.class);

	public static final EventBus bus = new EventBus();
	public final CrazycarGUI gui = new CrazycarGUI();
	public final static NetworkAccess networkAccess = new NetworkAccess();
	public final static Location size = Location.valueOf(15, 15);
	public final static int cars = 10;
	public final static Network network = Network
			.createSimple(size.getColumn());

	private Crazycar() {
		bus.register(gui);
	}

	public static Crazycar startGUI() {
		return new Crazycar();
	}

	public static void start() {
		networkAccess.cleanup();
		startGUI();
		bus.post(network);
		networkAccess.save(network);
		carInit();
		log.debug(networkAccess.snapshot());
	}

	private static void carInit() {
		for (int i = 0; i < cars; i += 1) {
//			new CarService();
		}
//		new SnapshotService();
  }

	public static EventBus bus() {
		return bus;
	}

	public static void main(String[] args) {
		start();
	}
}