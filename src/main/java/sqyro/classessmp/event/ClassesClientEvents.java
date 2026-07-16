package sqyro.classessmp.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import sqyro.classessmp.client.ClientPlayerData;

public class ClassesClientEvents {
    public static void registerEvents() {
        registerClientTickEvent();
        registerPlayerDisconnectEvent();
    }

    private static void registerClientTickEvent() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            ClientPlayerData.tick();
        });
    }

    private static void registerPlayerDisconnectEvent() {
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            ClientPlayerData.clear();
        });
    }
}
