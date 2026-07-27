package sqyro.classessmp;

import net.fabricmc.api.ClientModInitializer;
import sqyro.classessmp.client.*;
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

        AbilityIndicatorHud.register();

        ClassesClientNetworking.register();
        ClassesClientEvents.registerEvents();
    }
}