package sqyro.classessmp;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import sqyro.classessmp.client.*;
import sqyro.classessmp.entities.ClassesEntities;
import sqyro.classessmp.entities.rendering.ElephantModel;
import sqyro.classessmp.entities.rendering.ElephantRenderer;
import sqyro.classessmp.event.ClassesClientEvents;
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

        EntityModelLayerRegistry.registerModelLayer(ElephantModel.ELEPHANT, ElephantModel::createBodyLayer);
        EntityRendererRegistry.register(ClassesEntities.ELEPHANT, ElephantRenderer::new);

        AbilityIndicatorHud.register();

        ClassesClientNetworking.register();
        ClassesClientEvents.registerEvents();
    }
}