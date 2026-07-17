package sqyro.classessmp.particle;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

public class ParticleFactory {
    public static void register() {
        ParticleFactoryRegistry.getInstance().register(ClassesParticles.ICE_PARTICLE, IceParticle.Provider::new);
        ParticleFactoryRegistry.getInstance().register(ClassesParticles.ICE_STORM_PARTICLE, IceStormParticle.Provider::new);
        ParticleFactoryRegistry.getInstance().register(ClassesParticles.DICE_PARTICLE, DiceParticle.Provider::new);
    }
}