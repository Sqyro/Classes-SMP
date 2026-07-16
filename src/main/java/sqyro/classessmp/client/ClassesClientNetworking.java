package sqyro.classessmp.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import sqyro.classessmp.network.FreezeSyncPacket;

public class ClassesClientNetworking {
    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(FreezeSyncPacket.ID, (payload, context) -> {
            context.client().execute(() -> {
                FreezeClientCache.setFrozen(payload.entityID(), payload.Frozen());
            });
        });
    }
}
