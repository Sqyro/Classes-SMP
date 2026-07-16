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
import sqyro.classessmp.core.SavedData.PlayerClassSavedDataGetter;
import sqyro.classessmp.core.SavedData.PlayerClassSavedData;
import sqyro.classessmp.effect.ClassesEffects;
import sqyro.classessmp.network.ClassesNetworking;
import sqyro.classessmp.playerclasses.PlayerClasses;

public class ClassesEvents {
    public static int ABILITY_COOLDOWN_SYNC_INTERVAL = 100;

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
                if (Player.hasEffect(ClassesEffects.FREEZING)) {
                    Player.setDeltaMovement(0, 0, 0);
                    Player.setJumping(false);
                    Player.fallDistance = 0;
                    Player.hurtMarked = true;
                }

                PlayerClass playerClass = ((PlayerClassHolder) Player).getPlayerClass();

                if (playerClass != null) {
                    playerClass.onTick();
                }

                if (Player.tickCount % ABILITY_COOLDOWN_SYNC_INTERVAL == 0) {
                    ClassesNetworking.sendCooldownSync(Player);
                }
            }
        });
    }


    private static void registerPlayerJoinEvent() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            String classID = PlayerClassSavedDataGetter.get(handler.getPlayer().level().getServer().overworld()).getClass(handler.getPlayer().getUUID());

            PlayerClassHolder holder = (PlayerClassHolder) handler.getPlayer();
            holder.setSavedClassID(classID);
            PlayerClass playerClass = PlayerClasses.create(holder.getSavedClassID(), handler.getPlayer());

            if (playerClass != null) {
                playerClass.loadCooldowns(PlayerClassSavedDataGetter.get(handler.getPlayer().level()).getCooldowns(handler.getPlayer().getUUID()));
                holder.setPlayerClass(playerClass);
            }

            ClassesNetworking.sendClassSync(handler.getPlayer());
            ClassesNetworking.sendCooldownSync(handler.getPlayer());
        });
    }

    private static void registerPlayerRespawnEvent() {
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            PlayerClassSavedData savedData = PlayerClassSavedDataGetter.get(newPlayer.level().getServer().overworld());

            PlayerClassHolder holder = (PlayerClassHolder) newPlayer;

            holder.setSavedClassID(savedData.getClass(newPlayer.getUUID()));

            PlayerClass playerClass = PlayerClasses.create(savedData.getClass(newPlayer.getUUID()), newPlayer);

            if (playerClass != null) {
                playerClass.onRespawn();
                holder.setPlayerClass(playerClass);
                savedData.clearCooldowns(newPlayer.getUUID());
            }

            ClassesNetworking.sendFreezeSync(newPlayer.level(), newPlayer, false);
            ClassesNetworking.sendClassSync(newPlayer);
            ClassesNetworking.sendCooldownSync(newPlayer);

            ClassesSMP.LOGGER.info("Restored class {} for {}", savedData.getClass(newPlayer.getUUID()), newPlayer.getName().getString());
        });
    }

    private static void registerPlayerAttackEvent() {
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (!(player instanceof ServerPlayer serverPlayer)) {
                return InteractionResult.PASS;
            }

            PlayerClass playerClass = ((PlayerClassHolder)serverPlayer).getPlayerClass();

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