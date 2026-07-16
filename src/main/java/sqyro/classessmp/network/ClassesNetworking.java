package sqyro.classessmp.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import sqyro.classessmp.core.PlayerClass;
import sqyro.classessmp.core.PlayerClassHolder;

public class ClassesNetworking {
    public static void registerServer() {
        PayloadTypeRegistry.playC2S().register(Keybind1Packet.TYPE, Keybind1Packet.STREAM_CODEC);
        PayloadTypeRegistry.playC2S().register(Keybind2Packet.TYPE, Keybind2Packet.STREAM_CODEC);

        PayloadTypeRegistry.playS2C().register(FreezeSyncPacket.ID, FreezeSyncPacket.CODEC);

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
}
