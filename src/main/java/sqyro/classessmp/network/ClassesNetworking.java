package sqyro.classessmp.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import sqyro.classessmp.core.PlayerClass;
import sqyro.classessmp.core.PlayerClassHolder;

import java.util.HashMap;
import java.util.Map;

public class ClassesNetworking {
    public static void registerServer() {
        PayloadTypeRegistry.playC2S().register(Keybind1Packet.ID, Keybind1Packet.CODEC);
        PayloadTypeRegistry.playC2S().register(Keybind2Packet.ID, Keybind2Packet.CODEC);
        PayloadTypeRegistry.playC2S().register(Keybind3Packet.ID, Keybind3Packet.CODEC);

        PayloadTypeRegistry.playS2C().register(FreezeSyncPacket.ID, FreezeSyncPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(RootSyncPacket.ID, RootSyncPacket.CODEC);

        PayloadTypeRegistry.playS2C().register(ClassSyncPacket.ID, ClassSyncPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(CooldownStartPacket.ID, CooldownStartPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(CooldownSyncPacket.ID, CooldownSyncPacket.CODEC);

        PayloadTypeRegistry.playS2C().register(GamblerLevelSyncPacket.ID, GamblerLevelSyncPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(GamblerRollPacket.ID, GamblerRollPacket.CODEC);

        PayloadTypeRegistry.playS2C().register(BloodSyncPacket.ID, BloodSyncPacket.CODEC);

        PayloadTypeRegistry.playS2C().register(NoiseMeterPacket.ID, NoiseMeterPacket.CODEC);

        PayloadTypeRegistry.playS2C().register(ButItRefusedSoundPacket.TYPE, ButItRefusedSoundPacket.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(Keybind1Packet.ID, (payload, context) -> {
            context.server().execute(() -> {
                ServerPlayer Player = context.player();
                PlayerClass playerClass = ((PlayerClassHolder) Player).getPlayerClass();

                if (playerClass != null) {
                    playerClass.onKeybind1();
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(Keybind2Packet.ID, (payload, context) -> {
            context.server().execute(() -> {
                ServerPlayer Player = context.player();
                PlayerClass playerClass = ((PlayerClassHolder) Player).getPlayerClass();

                if (playerClass != null) {
                    playerClass.onKeybind2();
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(Keybind3Packet.ID, (payload, context) -> {
            context.server().execute(() -> {
                ServerPlayer Player = context.player();
                PlayerClass playerClass = ((PlayerClassHolder) Player).getPlayerClass();

                if (playerClass != null) {
                    playerClass.onKeybind3();
                }
            });
        });
    }

    public static void sendFreezeSync(ServerLevel Level, LivingEntity Entity, boolean Frozen) {
        FreezeSyncPacket Packet = new FreezeSyncPacket(Entity.getId(), Frozen);

        for (ServerPlayer Player : Level.players()) {
            if (Player.distanceTo(Entity) < 128) {
                ServerPlayNetworking.send(Player, Packet);
            }
        }
    }

    public static void sendRootSync(ServerLevel Level, LivingEntity Entity, boolean Rooted) {
        RootSyncPacket Packet = new RootSyncPacket(Entity.getId(), Rooted);

        for (ServerPlayer Player : Level.players()) {
            if (Player.distanceTo(Entity) < 128) {
                ServerPlayNetworking.send(Player, Packet);
            }
        }
    }

    public static void sendClassSync(ServerPlayer Player) {
        PlayerClassHolder holder = (PlayerClassHolder) Player;
        ServerPlayNetworking.send(Player, new ClassSyncPacket(holder.getSavedClassID()));
    }

    public static void sendCooldownStart(ServerPlayer Player, String AbilityID, int Ticks) {
        ServerPlayNetworking.send(Player, new CooldownStartPacket(AbilityID, Ticks));
    }

    public static void sendCooldownSync(ServerPlayer Player) {
        PlayerClass playerClass = ((PlayerClassHolder) Player).getPlayerClass();

        if (playerClass == null) {
            return;
        }

        Map<String, Integer> Cooldowns = new HashMap<>();

        playerClass.getCooldowns().forEach((ability, endTime) -> {
            int Remaining = (int)Math.max(0, endTime - Player.level().getGameTime());
            if (Remaining > 0) {
                Cooldowns.put(ability, Remaining);
            }
        });

        ServerPlayNetworking.send(Player, new CooldownSyncPacket(Cooldowns));
    }

    public static void sendGamblerLevel(ServerPlayer Player, int Level) {
        ServerPlayNetworking.send(Player, new GamblerLevelSyncPacket(Level));
    }

    public static void sendGamblerRoll(ServerPlayer Player, int Roll) {
        ServerPlayNetworking.send(Player, new GamblerRollPacket(Roll));
    }

    public static void sendBloodAmount(ServerPlayer Player, int Amount) {
        ServerPlayNetworking.send(Player, new BloodSyncPacket(Amount));
    }

    public static void sendNoiseMeter(ServerPlayer Player, int Value) {
        ServerPlayNetworking.send(Player, new NoiseMeterPacket(Value));
    }

    public static void sendButItRefusedStart(ServerPlayer sans) {
        ButItRefusedSoundPacket packet =
                new ButItRefusedSoundPacket(sans.getUUID(), true);

        double radius = 32.0;

        for (ServerPlayer player : sans.level().players()) {
            if (player.distanceToSqr(sans) <= radius * radius) {
                ServerPlayNetworking.send(player, packet);
            }
        }
    }

    public static void sendButItRefusedStop(ServerPlayer sans) {
        ButItRefusedSoundPacket packet =
                new ButItRefusedSoundPacket(sans.getUUID(), false);

        double radius = 32.0;

        for (ServerPlayer player : sans.level().players()) {
            if (player.distanceToSqr(sans) <= radius * radius) {
                ServerPlayNetworking.send(player, packet);
            }
        }
    }
}