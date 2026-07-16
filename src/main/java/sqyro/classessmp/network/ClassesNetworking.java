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
        PayloadTypeRegistry.playC2S().register(Keybind1Packet.TYPE, Keybind1Packet.STREAM_CODEC);
        PayloadTypeRegistry.playC2S().register(Keybind2Packet.TYPE, Keybind2Packet.STREAM_CODEC);

        PayloadTypeRegistry.playS2C().register(FreezeSyncPacket.ID, FreezeSyncPacket.CODEC);

        PayloadTypeRegistry.playS2C().register(ClassSyncPacket.ID, ClassSyncPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(CooldownStartPacket.ID, CooldownStartPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(CooldownSyncPacket.ID, CooldownSyncPacket.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(Keybind1Packet.TYPE, (payload, context) -> {
            context.server().execute(() -> {
                ServerPlayer Player = context.player();
                PlayerClass playerClass = ((PlayerClassHolder) Player).getPlayerClass();

                if (playerClass != null) {
                    playerClass.onKeybind1();
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(Keybind2Packet.TYPE, (payload, context) -> {
            context.server().execute(() -> {
                ServerPlayer Player = context.player();
                PlayerClass playerClass = ((PlayerClassHolder) Player).getPlayerClass();

                if (playerClass != null) {
                    playerClass.onKeybind2();
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
}