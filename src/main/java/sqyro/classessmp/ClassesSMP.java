package sqyro.classessmp;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;

import net.minecraft.resources.Identifier;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sqyro.classessmp.blocks.ClassesBlocks;
import sqyro.classessmp.items.ClassesCreativeTabs;
import sqyro.classessmp.core.ClassesDataComponents;
import sqyro.classessmp.effect.ClassesEffects;
import sqyro.classessmp.event.ClassesEvents;
import sqyro.classessmp.items.ClassesItems;
import sqyro.classessmp.network.ClassesNetworking;
import sqyro.classessmp.particle.ClassesParticles;
import sqyro.classessmp.playerclasses.PlayerClasses;
import sqyro.classessmp.sounds.ClassesSounds;

public class ClassesSMP implements ModInitializer {
	public static final String MOD_ID = "classes-smp";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final GameRule<Boolean> DISABLE_END = GameRuleBuilder.forBoolean(true).category(GameRuleCategory.MISC).buildAndRegister(Identifier.fromNamespaceAndPath(MOD_ID, "disable_end"));

	@Override
	public void onInitialize() {
		PlayerClasses.register();
		ClassesDataComponents.register();

		ClassesItems.register();
		ClassesBlocks.register();
		ClassesCreativeTabs.register();

		ClassesEffects.register();
		ClassesSounds.register();

		ClassesEvents.registerEvents();
		ClassesNetworking.registerServer();

		LOGGER.info("Hello Fabric world!");
	}
}