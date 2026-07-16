package sqyro.classessmp.effect;

import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import sqyro.classessmp.particle.ClassesParticles;
import sqyro.classessmp.sounds.ClassesSounds;

public class FreezingEffect extends MobEffect {
    public FreezingEffect() {
        super(MobEffectCategory.HARMFUL, 0x8FD8FF);
    }

    @Override
    public void onEffectStarted(LivingEntity Entity, int Amplifier) {
        if (Entity.level() instanceof ServerLevel Level) {
            Vec3 Pos = Entity.position().add(0, Entity.getBbHeight() / 2, 0);

            Level.playSound(null, Pos.x, Pos.y, Pos.z, ClassesSounds.ICE_PRISON_HIT, SoundSource.PLAYERS, 1.0f, 1.0f);

            Level.sendParticles(ClassesParticles.ICE_PARTICLE, Pos.x, Pos.y, Pos.z, 30, 0.4, 0.6, 0.4, 0.05);
        }

        super.onEffectStarted(Entity, Amplifier);
    }
}