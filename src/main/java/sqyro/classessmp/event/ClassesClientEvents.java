package sqyro.classessmp.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import sqyro.classessmp.client.ClientPlayerData;
import sqyro.classessmp.sounds.BloodSound;

public class ClassesClientEvents {
    private static float currentBloodVolume = 0;

    public static void registerEvents() {
        registerClientTickEvent();
        registerPlayerDisconnectEvent();
    }

    private static void registerClientTickEvent() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            ClientPlayerData.tick();

            float Intensity = ClientPlayerData.getBloodIntensity();
            currentBloodVolume += (Intensity - currentBloodVolume) * 0.05f;

            if (currentBloodVolume > 0.01f) {
                if (BloodSound.INSTANCE == null || !client.getSoundManager().isActive(BloodSound.INSTANCE)) {
                    BloodSound.INSTANCE = new BloodSound();
                    client.getSoundManager().play(BloodSound.INSTANCE);
                }

                BloodSound.INSTANCE.setVolume(currentBloodVolume);
            }
        });
    }

    private static void registerPlayerDisconnectEvent() {
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            ClientPlayerData.clear();
        });
    }
}
