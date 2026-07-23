package sqyro.classessmp.event;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import sqyro.classessmp.ClassesSMP;
import sqyro.classessmp.command.ChoseClassCommand;
import sqyro.classessmp.command.RemoveClassCommand;
import sqyro.classessmp.core.ClassesDataComponents;
import sqyro.classessmp.core.PlayerClass;
import sqyro.classessmp.core.PlayerClassHolder;
import sqyro.classessmp.core.SavedData.PlayerClassSavedDataGetter;
import sqyro.classessmp.core.SavedData.PlayerClassSavedData;
import sqyro.classessmp.effect.ClassesEffects;
import sqyro.classessmp.items.BloodSwordData;
import sqyro.classessmp.items.BloodSwordItem;
import sqyro.classessmp.network.ClassesNetworking;
import sqyro.classessmp.particle.ClassesParticles;
import sqyro.classessmp.playerclasses.AncientWarden;
import sqyro.classessmp.playerclasses.Gambler;
import sqyro.classessmp.playerclasses.PlayerClasses;
import sqyro.classessmp.sounds.ClassesSounds;

public class ClassesEvents {
    public static int ABILITY_COOLDOWN_SYNC_INTERVAL = 100;

    public static void registerEvents() {
        registerServerTickEvent();
        registerPlayerJoinEvent();
        registerPlayerRespawnEvent();
        registerDeathEvent();
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
            if (holder.getPlayerClass() instanceof Gambler gambler) {
                ClassesNetworking.sendGamblerLevel(handler.getPlayer(), gambler.getBonusLevel());
            }

            if (holder.getPlayerClass() instanceof AncientWarden) {
                ClassesNetworking.sendNoiseMeter(handler.getPlayer(), PlayerClassSavedDataGetter.get(handler.getPlayer().level()).getNoiseMeter(handler.getPlayer().getUUID()));
            }

            ItemStack MainHandStack = handler.getPlayer().getMainHandItem();

            if (MainHandStack == ItemStack.EMPTY) {
                return;
            }

            if (MainHandStack.getItem() instanceof BloodSwordItem) {
                ClassesNetworking.sendBloodAmount(handler.getPlayer(), BloodSwordItem.getData(MainHandStack).getKillCount());
            }
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
            if (holder.getPlayerClass() instanceof Gambler gambler) {
                ClassesNetworking.sendGamblerLevel(newPlayer, gambler.getBonusLevel());
            }

            ClassesSMP.LOGGER.info("Restored class {} for {}", savedData.getClass(newPlayer.getUUID()), newPlayer.getName().getString());
        });
    }

    private static void registerDeathEvent() {
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {
            if (!(source.getEntity() instanceof ServerPlayer Player)) {
                return;
            }

            if (!(entity instanceof ServerPlayer)) {
                return;
            }

            PlayerClass playerClass = ((PlayerClassHolder) Player).getPlayerClass();

            if (playerClass instanceof Gambler gambler) {
                gambler.onKill();
            }

            if (source.getWeaponItem() == null) {
                return;
            }

            ItemStack Weapon = source.getWeaponItem();

            if (!(Weapon.getItem() instanceof BloodSwordItem)) {
                return;
            }

            if (!BloodSwordItem.canUse(Player, Weapon)) {
                return;
            }

            BloodSwordData Data = BloodSwordItem.getData(Weapon);

            if (Data.getKillCount() < BloodSwordData.MAX_DAMAGE) {
                BloodSwordData newData = Data.addKill(entity.getUUID());

                Weapon.set(ClassesDataComponents.BLOOD_SWORD_DATA, newData);
                ClassesNetworking.sendBloodAmount(Player, newData.getKillCount());

                if (newData.getBonusDamage() > Data.getBonusDamage()) {
                    Weapon.set(ClassesDataComponents.BLOOD_SWORD_DATA, newData);

                    ServerLevel Level = Player.level();

                    Level.sendParticles(ClassesParticles.BLOOD_SPLATTER_PARTICLE, entity.getX(), entity.getY() + 1.0, entity.getZ(), 80, 0.5, 0.8, 0.5, 0.1);
                    Level.playSound(null, Player.getX(), Player.getY(), Player.getZ(), ClassesSounds.BLOOD_SWORD_UPGRADE, SoundSource.PLAYERS, 1.0f, 1.5f);
                }
            }
        });
    }

    private static void registerCommandsEvent() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            ChoseClassCommand.register(dispatcher);
            RemoveClassCommand.register(dispatcher);
        });
    }
}