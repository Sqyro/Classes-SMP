package sqyro.classessmp.playerclasses;

import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import sqyro.classessmp.ClassesSMP;
import sqyro.classessmp.core.PlayerClass;

public class DeathJester extends PlayerClass {
    public static final Identifier SPEED_MODIFIER_ID = Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "death_jester_speed");

    public static final int KILL_EFFECT_COOLDOWN = 200;

    public static final int SPEED_HIT_TIMER = 80;

    public static final String CARNIVAL_FRENZY_ID = "carnival_frenzy";
    public static final int CARNIVAL_FRENZY_COOLDOWN = 1200;
    public static final int CARNIVAL_FRENZY_DURATION = 140;

    private int combo;
    private int hit_timer;

    public DeathJester(ServerPlayer Player) {
        super(Player);
    }

    @Override
    public String getID() {
        return "death_jester";
    }

    @Override
    public void onTick() {
        hit_timer++;

        AttributeInstance speed = Player.getAttribute(Attributes.MOVEMENT_SPEED);
        AttributeModifier modifier = speed.getModifier(SPEED_MODIFIER_ID);

        if (modifier != null) {
            speed.removeModifier(modifier);
        }

        if (hit_timer >= SPEED_HIT_TIMER) {
            combo = 0;
        }

        if (combo > 0) {
            if (speed != null && speed.getModifier(SPEED_MODIFIER_ID) == null) {
                speed.addPermanentModifier(new AttributeModifier(SPEED_MODIFIER_ID, Math.clamp( 0.01 * combo, 0, 0.09), AttributeModifier.Operation.ADD_VALUE));
            }
        }
    }

    @Override
    public void onRespawn() {

    }

    @Override
    public void onKill(Entity Target) {
        Player.addEffect(new MobEffectInstance(MobEffects.STRENGTH, KILL_EFFECT_COOLDOWN, 1));
        Player.addEffect(new MobEffectInstance(MobEffects.SPEED, KILL_EFFECT_COOLDOWN, 1));
    }

    @Override
    public void onKeybind1() {
        if (isOnCooldown(CARNIVAL_FRENZY_ID)) {
            ClassesSMP.LOGGER.info("{} of class: {} tried to activate Carnival Frenzy, but it was on cooldown: {}", Player.getName().getString(), this.getID(), this.getCooldownTicks(CARNIVAL_FRENZY_ID));
            return;
        }

        ClassesSMP.LOGGER.info("{} of class {} activated Carnival Frenzy", Player.getName().getString(), this.getID());
        setCooldown(CARNIVAL_FRENZY_ID, CARNIVAL_FRENZY_COOLDOWN);

        Player.addEffect(new MobEffectInstance(MobEffects.STRENGTH, CARNIVAL_FRENZY_DURATION, 2));
        Player.addEffect(new MobEffectInstance(MobEffects.SPEED, CARNIVAL_FRENZY_DURATION, 1));
        Player.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, CARNIVAL_FRENZY_DURATION, 0));
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
        combo++;
        hit_timer = 0;
    }
}
