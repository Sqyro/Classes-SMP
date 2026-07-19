package sqyro.classessmp.playerclasses;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import sqyro.classessmp.ClassesSMP;
import sqyro.classessmp.core.ClassesDataComponents;
import sqyro.classessmp.core.PlayerClass;
import sqyro.classessmp.items.BloodSwordData;
import sqyro.classessmp.items.BloodSwordItem;
import sqyro.classessmp.network.ClassesNetworking;
import sqyro.classessmp.particle.ClassesParticles;
import sqyro.classessmp.sounds.ClassesSounds;

public class BloodSword extends PlayerClass {
    public static final String LIFE_STEAL_ID = "life_steal";
    public static final int LIFE_STEAL_COOLDOWN = 100;
    public static final int LIFE_STEAL_HEAL = 15;
    public static final int LIFE_STEAL_FOOD = 10;
    public static final int LIFE_STEAL_SATURATION = 5;

    public BloodSword(ServerPlayer Player) {
        super(Player);
    }

    @Override
    public String getID() {
        return "blood_sword";
    }

    @Override
    public void onTick() {
        this.tickCooldowns();
    }

    @Override
    public void onRespawn() {
        getCooldowns().clear();
    }

    @Override
    public void onKeybind1() {
        if (isOnCooldown(LIFE_STEAL_ID)) {
            ClassesSMP.LOGGER.info("{} of class: {} tried to activate Life Steal, but it was on cooldown: {}", Player.getName().getString(), this.getID(), this.getCooldownTicks(LIFE_STEAL_ID));
            return;
        }

        ItemStack ItemStackInHand = Player.getItemInHand(InteractionHand.MAIN_HAND);

        if (ItemStackInHand == ItemStack.EMPTY) {
            failAbilityBecauseOfHeldItem();
            return;
        }

        if (ItemStackInHand.getItem() instanceof BloodSwordItem) {
            BloodSwordData bloodSwordData = BloodSwordItem.getData(ItemStackInHand);
            BloodSwordData newData = bloodSwordData.removeKill();
            if (newData == null) {
                ClassesSMP.LOGGER.info("{} of class: {} tried to activate Life Steal, but has no blood", Player.getName().getString(), this.getID());
                return;
            }

            ClassesSMP.LOGGER.info("{} of class: {} activated Life Steal", Player.getName().getString(), this.getID());
            setCooldown(LIFE_STEAL_ID, LIFE_STEAL_COOLDOWN);
            ItemStackInHand.set(ClassesDataComponents.BLOOD_SWORD_DATA, newData);

            Player.containerMenu.broadcastChanges();

            ItemStackInHand.set(ClassesDataComponents.BLOOD_SWORD_DATA, newData);
            ClassesNetworking.sendBloodAmount(Player, newData.getKillCount());

            Player.heal(LIFE_STEAL_HEAL);
            Player.getFoodData().eat(LIFE_STEAL_FOOD, LIFE_STEAL_SATURATION);
            Player.level().playSound(null, Player.getX(), Player.getY(), Player.getZ(), ClassesSounds.BLOOD_SWORD_CONSUME, SoundSource.PLAYERS);
            Player.level().sendParticles(ClassesParticles.BLOOD_SPLATTER_PARTICLE, Player.getX(), Player.getY(), Player.getZ(), 16, 0.3, 0.5, 0.3, 0.05);
            Player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 0));
        } else {
            failAbilityBecauseOfHeldItem();
        }

    }

    private void failAbilityBecauseOfHeldItem() {
        ClassesSMP.LOGGER.info("{} of class: {} tried to activate Life Steal, failed because he's not holding the sword", Player.getName().getString(), this.getID());
    }

    @Override
    public void onKeybind2() {

    }

    @Override
    public void onKeybind3() {
        
    }
}