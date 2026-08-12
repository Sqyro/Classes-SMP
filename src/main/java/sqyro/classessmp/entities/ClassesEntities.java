package sqyro.classessmp.entities;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import sqyro.classessmp.ClassesSMP;

public class ClassesEntities {
    private static final ResourceKey<EntityType<?>> ELEPHANT_KEY = ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "elephant"));
    private static final ResourceKey<EntityType<?>> LASER_CHICKEN_KEY = ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "laser_chicken"));

    public static final EntityType<ElephantEntity> ELEPHANT = Registry.register(BuiltInRegistries.ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "elephant"), EntityType.Builder.of(ElephantEntity::new, MobCategory.CREATURE)
                    .sized(2.5F, 3.0F)
                    .build(ELEPHANT_KEY)
    );
    public static final EntityType<LaserChickenEntity> LASER_CHICKEN = Registry.register(BuiltInRegistries.ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "laser_chicken"), EntityType.Builder.of(LaserChickenEntity::new, MobCategory.CREATURE)
                    .sized(0.5F, 1.0F)
                    .build(LASER_CHICKEN_KEY)
    );

    public static void register() {}
}