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

    public static final EntityType<ElephantEntity> ELEPHANT = Registry.register(BuiltInRegistries.ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "elephant"), EntityType.Builder.of(ElephantEntity::new, MobCategory.CREATURE)
                    .sized(2.5F, 3.0F)
                    .build(ELEPHANT_KEY)
    );

    public static void register() {}
}