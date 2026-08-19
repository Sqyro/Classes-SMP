package sqyro.classessmp.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import sqyro.classessmp.network.*;
import sqyro.classessmp.sounds.SansClientSounds;

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

        ClientPlayNetworking.registerGlobalReceiver(RootSyncPacket.ID, (payload, context) -> {
            context.client().execute(() -> {
                RootingClientCache.setRooted(payload.entityID(), payload.Rooted());
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

        ClientPlayNetworking.registerGlobalReceiver(NoiseMeterPacket.ID, (payload, context) -> {
            context.client().execute(() -> {
                ClientPlayerData.setNoiseMeter(payload.Value());
            });
        });
        ClientPlayNetworking.registerGlobalReceiver(ButItRefusedSoundPacket.TYPE, (payload, context) -> {
            context.client().execute(() -> {
                Minecraft minecraft = context.client();

                if (minecraft.level == null) {
                    return;
                }

                Player player = minecraft.level.getPlayerByUUID(payload.playerUUID());

                if (player == null) {
                    return;
                }

                if (payload.playing()) {
                    SansClientSounds.startButItRefused(player);
                } else {
                    SansClientSounds.stopButItRefused(player);
                }
            });
        });
    }
}