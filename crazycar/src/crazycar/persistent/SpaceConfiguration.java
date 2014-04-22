package crazycar.persistent;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openspaces.admin.Admin;
import org.openspaces.admin.AdminFactory;
import org.openspaces.admin.gsm.GridServiceManager;
import org.openspaces.admin.pu.ProcessingUnit;
import org.openspaces.admin.pu.ProcessingUnitAlreadyDeployedException;
import org.openspaces.admin.pu.ProcessingUnitDeployment;
import org.openspaces.admin.pu.elastic.config.ManualCapacityScaleConfigurer;
import org.openspaces.admin.space.ElasticSpaceDeployment;
import org.openspaces.admin.space.SpaceDeployment;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;
import org.openspaces.core.util.MemoryUnit;

public class SpaceConfiguration {

	private static final SpaceConfiguration space = new SpaceConfiguration();
	private static final Logger log = Logger.getLogger(SpaceConfiguration.class);

	private final String name = "myGrid";
	private final String standard = "jini://*/*/" + name;
	private final String processingunits = "/./" + name;

	private final UrlSpaceConfigurer configurer = new UrlSpaceConfigurer(processingunits);

	private final GigaSpace instance;

	private SpaceConfiguration() {
		GigaSpaceConfigurer g = new GigaSpaceConfigurer(configurer);
		instance = g.gigaSpace();
	}

	public static SpaceConfiguration create() {
		return space;
	}

	public UrlSpaceConfigurer getConfigurer() {
		return configurer;
	}

	public GigaSpace getInstance() {
		return instance;
	}

}
