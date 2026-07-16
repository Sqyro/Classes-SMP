package sqyro.classessmp;

import net.fabricmc.api.ClientModInitializer;
import sqyro.classessmp.client.ClassesClientNetworking;
import sqyro.classessmp.client.ClassesKeyBinds;
import sqyro.classessmp.client.FreezeOverlay;
import sqyro.classessmp.particle.ParticleFactory;

public class ClassesSMPClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClassesKeyBinds.register();
        ClassesKeyBinds.registerEvents();

        FreezeOverlay.register();

        ParticleFactory.register();

        ClassesClientNetworking.register();
    }
}