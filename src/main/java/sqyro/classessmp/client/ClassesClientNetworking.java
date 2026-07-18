package sqyro.classessmp.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import sqyro.classessmp.network.*;

public class ClassesClientNetworking {
    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(ClassSyncPacket.ID, (payload, context) -> {
            context.client().execute(() -> {
                ClientPlayerData.setClass(payload.classId());
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(CooldownStartPacket.ID, (payload, context) -> {
            context.client().execute(() -> {
                ClientPlayerData.setCooldown(payload.abilityID(), payload.Ticks());
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(CooldownSyncPacket.ID, (payload, context) -> {
            context.client().execute(() -> {
                ClientPlayerData.replaceCooldowns(payload.cooldowns());
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(FreezeSyncPacket.ID, (payload, context) -> {
            context.client().execute(() -> {
                FreezeClientCache.setFrozen(payload.entityID(), payload.Frozen());
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(GamblerLevelSyncPacket.ID, (payload, context) -> {
            context.client().execute(() -> {
                ClientPlayerData.setGamblerLevel(payload.Level());
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(GamblerRollPacket.ID, (payload, context) -> {
            context.client().execute(() -> {
                ClientPlayerData.setGamblerRoll(payload.Roll());
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(BloodSyncPacket.ID, (payload, context) -> {
                context.client().execute(() -> {
                    ClientPlayerData.setBloodAmount(payload.Amount());
            });
        });
    }
}