package crazycar;

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

	public static Crazycar start() {
		networkAccess.cleanup();
		Crazycar c = startGUI();
		bus.post(network);
		networkAccess.save(network);
		log.debug(networkAccess.snapshot());
		return c;
	}

	public static EventBus bus() {
		return bus;
	}

	public static void main(String[] args) {
		start();
	}
}