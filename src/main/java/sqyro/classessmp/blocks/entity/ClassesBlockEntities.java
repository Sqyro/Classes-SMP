package sqyro.classessmp.blocks.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.entity.BlockEntityType;
import sqyro.classessmp.ClassesSMP;
import sqyro.classessmp.blocks.ClassesBlocks;

public class ClassesBlockEntities {
    public static final BlockEntityType<CaseBlockEntity> CASE_BLOCK_ENTITY = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "case_block_entity"),
            FabricBlockEntityTypeBuilder.create(CaseBlockEntity::new, ClassesBlocks.CASE_BLOCK).build()
    );

    public static void register() {
    }
}
