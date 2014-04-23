package crazycar.persistent;

import org.apache.log4j.Logger;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;

public class SpaceConfiguration {

	private static final SpaceConfiguration space = new SpaceConfiguration();
	private static final Logger log = Logger.getLogger(SpaceConfiguration.class);

	private final String name = "crazycarSpace";
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
