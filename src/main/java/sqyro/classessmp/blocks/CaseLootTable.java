package sqyro.classessmp.blocks;

import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import sqyro.classessmp.items.ClassesItems;

import java.util.List;

public class CaseLootTable {
    private static final List<LootEntry> ENTRIES = List.of(
            // BLUE - 80% total, 20% each
            new LootEntry(new ItemStack(Items.IRON_INGOT), 20.0),
            new LootEntry(new ItemStack(Items.GOLD_INGOT), 20.0),
            new LootEntry(new ItemStack(Items.EMERALD), 20.0),
            new LootEntry(new ItemStack(Items.COPPER_INGOT), 20.0),

            // PURPLE - 15.9% total, 5.3% each
            new LootEntry(new ItemStack(ClassesBlocks.BOSSMINER2000), 5.3),
            new LootEntry(new ItemStack(Items.DIAMOND), 5.3),
            new LootEntry(new ItemStack(ClassesItems.CASE_KEY), 5.3),

            // PINK - 3.2% total, 1.6% each
            new LootEntry(new ItemStack(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), 1.6),
            new LootEntry(new ItemStack(Items.NETHERITE_INGOT), 1.6),

            // RED - 0.63% total
            new LootEntry(new ItemStack(Items.ENCHANTED_GOLDEN_APPLE), 0.63),

            // GOLD - 0.26% total
            new LootEntry(new ItemStack(ClassesItems.REMOVE_CLASS_ITEM), 0.26)
    );

    private static final double TOTAL_WEIGHT = ENTRIES.stream().mapToDouble(LootEntry::weight).sum();

    public static ItemStack roll(RandomSource random) {
        double roll = random.nextDouble() * TOTAL_WEIGHT;

        for (LootEntry entry : ENTRIES) {
            roll -= entry.weight();

            if (roll < 0.0) {
                return entry.stack().copy();
            }
        }

        return ENTRIES.getLast().stack().copy();
    }

    private record LootEntry(ItemStack stack, double weight) {

    }
}
