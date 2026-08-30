package sqyro.classessmp.playerclasses;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import sqyro.classessmp.core.PlayerClass;

public class Lunaris extends PlayerClass {
    public Lunaris(ServerPlayer Player) {
        super(Player);
    }

    @Override
    public String getID() {
        return "lunaris";
    }

    @Override
    public void onTick() {
        float healthPercentage = Player.getHealth() / Player.getMaxHealth();

        if (healthPercentage >= 0.80F) {
            Player.addEffect(new MobEffectInstance(MobEffects.SPEED, 40, 2, false, false));
            Player.addEffect(new MobEffectInstance(MobEffects.STRENGTH, 40, 2, false, false));
        } else {
            Player.addEffect(new MobEffectInstance(MobEffects.SPEED, 40, 1, false, false));
            Player.addEffect(new MobEffectInstance(MobEffects.STRENGTH, 40, 1, false, false));
        }
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
