package sqyro.classessmp;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import sqyro.classessmp.client.*;
import sqyro.classessmp.client.GUI.ClassesMenuTypes;
import sqyro.classessmp.client.GUI.screen.CaseScreen;
import sqyro.classessmp.entities.ClassesEntities;
import sqyro.classessmp.entities.rendering.*;
import sqyro.classessmp.event.ClassesClientEvents;
import sqyro.classessmp.network.cases.CaseClientNetworking;
import sqyro.classessmp.network.cases.CaseNetworking;
import sqyro.classessmp.particle.ClassesParticles;

public class ClassesSMPClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientClasses.register();

        ClassesKeyBinds.register();
        ClassesKeyBinds.registerEvents();

        FreezeOverlay.register();
        ClassesHud.register();
        ClientSounds.register();
        ClassesParticles.register();

        EntityModelLayerRegistry.registerModelLayer(ElephantModel.ELEPHANT, ElephantModel::getTexturedModelData);
        EntityRendererRegistry.register(ClassesEntities.ELEPHANT, ElephantRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(LaserChickenModel.LASER_CHICKEN, LaserChickenModel::getTexturedModelData);
        EntityRendererRegistry.register(ClassesEntities.LASER_CHICKEN, LaserChickenRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(CardProjectileModel.CARD, CardProjectileModel::getTexturedModelData);
        EntityRendererRegistry.register(ClassesEntities.CARD_PROJECTILE, CardProjectileRenderer::new);

        AbilityIndicatorHud.register();

        ClassesClientEvents.registerEvents();

        ClassesClientNetworking.register();
        CaseClientNetworking.registerClient();

        MenuScreens.register(ClassesMenuTypes.CASE_MENU, CaseScreen::new);
    }
}