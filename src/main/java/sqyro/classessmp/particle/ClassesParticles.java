package sqyro.classessmp.particle;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import sqyro.classessmp.ClassesSMP;

public class ClassesParticles {
    public static final SimpleParticleType ICE_PARTICLE = Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "ice_particle"), FabricParticleTypes.simple());
    public static final SimpleParticleType ICE_STORM_PARTICLE = Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "ice_storm_particle"), FabricParticleTypes.simple());
    public static final SimpleParticleType DICE_PARTICLE = Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "dice_particle"), FabricParticleTypes.simple());
    public static final SimpleParticleType BLOOD_SPLATTER_PARTICLE = Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "blood_splatter_particle"), FabricParticleTypes.simple());
    public static final SimpleParticleType LIGHTNING_PARTICLE = Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "lightning_particle"), FabricParticleTypes.simple());

    public static void register() {

    }

    public static void registerClient() {
        ParticleFactoryRegistry.getInstance().register(ICE_PARTICLE, IceParticle.Provider::new);
        ParticleFactoryRegistry.getInstance().register(ICE_STORM_PARTICLE, IceStormParticle.Provider::new);
        ParticleFactoryRegistry.getInstance().register(DICE_PARTICLE, DiceParticle.Provider::new);
        ParticleFactoryRegistry.getInstance().register(BLOOD_SPLATTER_PARTICLE, BloodSplatterParticle.Provider::new);
        ParticleFactoryRegistry.getInstance().register(LIGHTNING_PARTICLE, LightningParticle.Provider::new);
    }
}
