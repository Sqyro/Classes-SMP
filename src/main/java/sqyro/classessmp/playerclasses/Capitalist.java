package sqyro.classessmp.playerclasses;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import sqyro.classessmp.ClassesSMP;
import sqyro.classessmp.core.PlayerClass;

public class Capitalist extends PlayerClass {
    private static final String SACRIFICE_ID = "sacrifice";
    public static final int SACRIFICE_COOLDOWN = 20;

    public Capitalist(ServerPlayer Player) {
        super(Player);
    }

    @Override
    public String getID() {
        return "capitalist";
    }

    @Override
    public void onTick() {
        Player.addEffect(new MobEffectInstance(MobEffects.HERO_OF_THE_VILLAGE, 20, 4, false, false));
    }

    @Override
    public void onRespawn() {

    }

    @Override
    public void onKill(Entity Target) {

    }

    @Override
    public void onKeybind1() {
        if (isOnCooldown(SACRIFICE_ID)) {
            ClassesSMP.LOGGER.info("{} of class: {} tried to activate Sacrifice, but it was on cooldown: {}", Player.getName().getString(), this.getID(), this.getCooldownTicks(SACRIFICE_ID));
            return;
        }

        ClassesSMP.LOGGER.info("{} of class {} activated Sacrifice", Player.getName().getString(), this.getID());
        setCooldown(SACRIFICE_ID, SACRIFICE_COOLDOWN);

        Item itemInHand = Player.getItemInHand(InteractionHand.MAIN_HAND).getItem();

        if (getEffectForItem(itemInHand) != null) {
            Player.addEffect(getEffectForItem(itemInHand));
        }
    }

    private MobEffectInstance getEffectForItem(Item itemInHand) {
        MobEffectInstance ItemEffect = null;

        if (itemInHand == Items.COPPER_INGOT) {
            ItemEffect = new MobEffectInstance(MobEffects.INSTANT_HEALTH, 1, 0, false, false);
        } else if (itemInHand == Items.IRON_INGOT) {
            ItemEffect = new MobEffectInstance(MobEffects.SPEED, 200, 1, false, false);
        } else if (itemInHand == Items.GOLD_INGOT) {
            ItemEffect = new MobEffectInstance(MobEffects.ABSORPTION, 2400, 0, false, false);
        } else if (itemInHand == Items.EMERALD) {
            ItemEffect = new MobEffectInstance(MobEffects.STRENGTH, 200, 1, false, false);
        } else if (itemInHand == Items.DIAMOND) {
            ItemEffect = new MobEffectInstance(MobEffects.RESISTANCE, 80, 2, false, false);
        } else if (itemInHand == Items.NETHERITE_INGOT) {
            ItemEffect = new MobEffectInstance(MobEffects.RESISTANCE, 80, 4, false, false);
        }

        return ItemEffect;
    }

    @Override
    public void onKeybind2() {

    }

    @Override
    public void onKeybind3() {

    }

    @Override
    public void beginAttack(Entity Target) {

    }

    @Override
    public void endAttack() {

    }
}
