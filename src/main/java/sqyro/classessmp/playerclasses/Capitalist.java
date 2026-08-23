package sqyro.classessmp.playerclasses;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
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
