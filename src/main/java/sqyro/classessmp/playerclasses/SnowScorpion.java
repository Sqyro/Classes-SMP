package sqyro.classessmp.playerclasses;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import sqyro.classessmp.ClassesSMP;
import sqyro.classessmp.core.PlayerClass;
import sqyro.classessmp.effect.ClassesEffects;

public class SnowScorpion extends PlayerClass {
    public SnowScorpion(ServerPlayer Player) {
        super(Player);
    }

    @Override
    public String getID() {
        return "snow_scorpion";
    }

    @Override
    public void onTick() {

    }

    @Override
    public void onAttack(Entity Target) {

    }

    @Override
    public void onKeybind1() {
        ClassesSMP.LOGGER.info("{} of class: {} activated Ice Pull", Player.getName().getString(), this.getID());
    }

    @Override
    public void onKeybind2() {
        Player.addEffect(new MobEffectInstance(ClassesEffects.FREEZING, 100, 0));
        ClassesSMP.LOGGER.info("{} of class: {} activated Ice Prison", Player.getName().getString(), this.getID());
    }
}
