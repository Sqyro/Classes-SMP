package sqyro.classessmp.items;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import sqyro.classessmp.ClassesSMP;
import sqyro.classessmp.blocks.ClassesBlocks;

public class ClassesCreativeTabs {
    public static final CreativeModeTab CLASSES_TAB = FabricItemGroup.builder().icon(() -> new ItemStack(ClassesItems.BLOODSWORD))
            .title(Component.translatable("itemGroup.classessmp"))
            .displayItems((context, entries) -> {
                entries.accept(ClassesItems.BLOODSWORD);
                entries.accept(ClassesItems.CLOWNSWORD);
                entries.accept(ClassesItems.LUNARIS_SWORD);
                entries.accept(ClassesItems.C4);
                entries.accept(ClassesBlocks.WHITE_KEYPAD);
                entries.accept(ClassesBlocks.BOSSMINER2000);
                entries.accept(ClassesItems.FEATHER_HELMET);
                entries.accept(ClassesItems.FEATHER_CHESTPLATE);
                entries.accept(ClassesItems.FEATHER_LEGGINGS);
                entries.accept(ClassesItems.FEATHER_BOOTS);
                entries.accept(ClassesItems.ELEPHANT_SPAWNER);
                entries.accept(ClassesItems.LASER_CHICKEN_SPAWNER);
                entries.accept(ClassesBlocks.CASE_BLOCK);
                entries.accept(ClassesItems.CASE_KEY);
                entries.accept(ClassesItems.REMOVE_CLASS_ITEM);

            }).build();

    public static void register() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "classes"), CLASSES_TAB);
    }
}