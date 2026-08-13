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

public class TheVessel extends PlayerClass {
    public static final Identifier DAMAGE_MODIFIER_ID = Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "vessel_damage");
    public static final Identifier HEALTH_MODIFIER_ID = Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "vessel_health");

    public TheVessel(ServerPlayer Player) {
        super(Player);
    }

    @Override
    public String getID() {
        return "the_vessel";
    }

    @Override
    public void onTick() {
        float healthPercentage = Player.getHealth() / Player.getMaxHealth();

        if (healthPercentage <= 0.30F) {
            Player.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 40, 1, false, false));
        } else {
            Player.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 40, 0, false, false));
        }

        AttributeInstance health = Player.getAttribute(Attributes.MAX_HEALTH);

        if (health != null && health.getModifier(HEALTH_MODIFIER_ID) == null) {
            health.addPermanentModifier(new AttributeModifier(HEALTH_MODIFIER_ID, 20.0, AttributeModifier.Operation.ADD_VALUE));
        }

        AttributeInstance damage = Player.getAttribute(Attributes.ATTACK_DAMAGE);

        if (damage != null) {
            boolean belowHalf = Player.getHealth() <= Player.getMaxHealth() / 2.0F;

            if (belowHalf) {
                if (damage.getModifier(DAMAGE_MODIFIER_ID) == null) {
                    damage.addTransientModifier(new AttributeModifier(DAMAGE_MODIFIER_ID, 6.0, AttributeModifier.Operation.ADD_VALUE));
                }
            } else {
                AttributeModifier modifier = damage.getModifier(DAMAGE_MODIFIER_ID);

                if (modifier != null) {
                    damage.removeModifier(modifier);
                }
            }
        }
    }

    @Override
    public void onRespawn() {

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