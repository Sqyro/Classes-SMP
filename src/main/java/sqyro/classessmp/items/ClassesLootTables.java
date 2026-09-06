package sqyro.classessmp.items;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import sqyro.classessmp.ClassesSMP;

public class ClassesLootTables {
    private static final Identifier VAULT = Identifier.withDefaultNamespace("chests/trial_chambers/reward");
    private static final Identifier OMINOUS_VAULT = Identifier.withDefaultNamespace("chests/trial_chambers/reward_ominous");

    public static void register() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (!source.isBuiltin()) {
                return;
            }


            if (!key.identifier().equals(VAULT)) {
                return;
            }

            ClassesSMP.LOGGER.info("Modifying trial chamber vault loot table: {}", key.identifier());

            LootPool pool = LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                    .add(LootItem.lootTableItem(ClassesItems.HEAVY_UPGRADE_SMITHING_TEMPLATE)
                    .setWeight(1).when(LootItemRandomChanceCondition.randomChance(0.05F))).build();

            tableBuilder.pool(pool);
        });
    }
}
