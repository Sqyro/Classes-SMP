package sqyro.classessmp;

import net.fabricmc.api.ClientModInitializer;
import sqyro.classessmp.client.*;
import sqyro.classessmp.event.ClassesClientEvents;
import sqyro.classessmp.particle.ParticleFactory;

public class ClassesSMPClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientClasses.register();

        ClassesKeyBinds.register();
        ClassesKeyBinds.registerEvents();

        FreezeOverlay.register();
        ClassesHud.register();

        ParticleFactory.register();
        AbilityIndicatorHud.register();

        ClassesClientNetworking.register();
        ClassesClientEvents.registerEvents();
    }
}