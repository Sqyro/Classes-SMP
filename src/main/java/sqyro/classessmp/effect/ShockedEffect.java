package sqyro.classessmp.effect;

import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;
import sqyro.classessmp.ClassesSMP;
import sqyro.classessmp.particle.ClassesParticles;
import sqyro.classessmp.sounds.ClassesSounds;

public class ShockedEffect extends MobEffect {
    public static final float SHOCKED_SPEED_MULTIPLIER = -0.5f;

    public ShockedEffect() {
        super(MobEffectCategory.HARMFUL, 0x000FFF);
        addAttributeModifier(Attributes.MOVEMENT_SPEED, Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "shocked_speed"), SHOCKED_SPEED_MULTIPLIER, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    @Override
    public void onEffectStarted(LivingEntity Entity, int Amplifier) {
        if (Entity.level() instanceof ServerLevel Level) {
            Vec3 Pos = Entity.position().add(0, Entity.getBbHeight() / 2, 0);

            Level.playSound(null, Pos.x, Pos.y, Pos.z, ClassesSounds.SHOCKED_IMPACT, SoundSource.PLAYERS);
            Level.sendParticles(ClassesParticles.LIGHTNING_PARTICLE, Pos.x, Pos.y, Pos.z, 30, 0.4, 0.6, 0.4, 0.05);
        }

        super.onEffectStarted(Entity, Amplifier);
    }
}