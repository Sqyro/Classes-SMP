package sqyro.classessmp.event;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import sqyro.classessmp.ClassesSMP;
import sqyro.classessmp.command.ChoseClassCommand;
import sqyro.classessmp.core.PlayerClass;
import sqyro.classessmp.core.PlayerClassHolder;
import sqyro.classessmp.core.SavedData.ModSavedData;
import sqyro.classessmp.core.SavedData.PlayerClassSavedData;
import sqyro.classessmp.playerclasses.PlayerClasses;

public class Events {
    public static void registerEvents() {
        registerServerTickEvent();
        registerPlayerJoinEvent();
        registerPlayerRespawnEvent();
        registerPlayerAttackEvent();
        registerCommandsEvent();
    }

    private static void registerServerTickEvent() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayer Player : server.getPlayerList().getPlayers()) {
                PlayerClass playerClass = ((PlayerClassHolder) Player).getPlayerClass();

                if (playerClass != null) {
                    playerClass.onTick();
                }
            }
        });
    }


    private static void registerPlayerJoinEvent() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            String classID = ModSavedData.get(handler.getPlayer().level().getServer().overworld()).getClass(handler.getPlayer().getUUID());

            PlayerClassHolder holder = (PlayerClassHolder) handler.getPlayer();

            holder.setSavedClassID(classID);

            PlayerClass playerClass = PlayerClasses.create(holder.getSavedClassID(), handler.getPlayer());

            if (playerClass != null) {
                holder.setPlayerClass(playerClass);
            }
        });
    }

    private static void registerPlayerRespawnEvent() {
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            PlayerClassSavedData savedData = ModSavedData.get(newPlayer.level().getServer().overworld());

            PlayerClassHolder holder = (PlayerClassHolder) newPlayer;

            holder.setSavedClassID(savedData.getClass(newPlayer.getUUID()));

            PlayerClass playerClass = PlayerClasses.create(savedData.getClass(newPlayer.getUUID()), newPlayer);

            if (playerClass != null) {
                holder.setPlayerClass(playerClass);
            }

            ClassesSMP.LOGGER.info("Restored class {} for {}", savedData.getClass(newPlayer.getUUID()), newPlayer.getName().getString());
        });
    }

    private static void registerPlayerAttackEvent() {
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (!(player instanceof ServerPlayer serverPlayer)) {
                return InteractionResult.PASS;
            }

            PlayerClass playerClass =
                    ((PlayerClassHolder)serverPlayer).getPlayerClass();

            if (playerClass != null) {
                playerClass.onAttack(entity);
            }

            return InteractionResult.PASS;
        });
    }

    private static void registerCommandsEvent() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            ChoseClassCommand.register(dispatcher);
        });
    }
}