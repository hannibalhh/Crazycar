package crazycar.logic;

import org.openspaces.core.GigaSpace;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.example.helloworld.common.Message;

import java.util.logging.Logger;


/**
 * The processor is passed interesting Objects from its associated PollingContainer
 * <p>The PollingContainer removes objects from the GigaSpace that match the criteria
 * specified for it.
 * <p>Once the Processor receives each Object, it modifies its state and returns it to
 * the PollingContainer which writes them back to the GigaSpace
 * <p/>
 * <p>The PollingContainer is configured in the pu.xml file of this project
 */
public class TrafficLightService {
    Logger log = Logger.getLogger(this.getClass().getName());

    /**
     * Process the given Message and return it to the caller.
     * This method is invoked using OpenSpaces Events when a matching event
     * occurs.
     */
    @SpaceDataEvent
    public Message processMessage(Message msg, GigaSpace giga) {
        log.info("CarService PROCESSING: " + msg);
        msg.setInfo2(msg.getInfo2() + "World !!");
        return msg;
    }

    public TrafficLightService() {
        log.info("Processor instantiated, waiting for messages feed...");
    }

}
