package sqyro.classessmp.particle;

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

    public static void register() {}
}
