package sqyro.classessmp;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sqyro.classessmp.effect.ClassesEffects;
import sqyro.classessmp.event.ClassesEvents;
import sqyro.classessmp.network.ClassesNetworking;
import sqyro.classessmp.particle.ClassesParticles;
import sqyro.classessmp.playerclasses.PlayerClasses;
import sqyro.classessmp.sounds.ClassesSounds;

public class ClassesSMP implements ModInitializer {
	public static final String MOD_ID = "classes-smp";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		PlayerClasses.register();
		ClassesEffects.register();
		ClassesSounds.register();
		ClassesParticles.register();

		ClassesEvents.registerEvents();
		ClassesNetworking.registerServer();

		LOGGER.info("Hello Fabric world!");
	}
}