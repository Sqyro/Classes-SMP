package sqyro.classessmp;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sqyro.classessmp.event.Events;
import sqyro.classessmp.playerclasses.PlayerClasses;

public class ClassesSMP implements ModInitializer {
	public static final String MOD_ID = "classes-smp";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		PlayerClasses.register();

		Events.registerEvents();

		LOGGER.info("Hello Fabric world!");
	}
}