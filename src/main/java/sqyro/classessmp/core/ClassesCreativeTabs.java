package sqyro.classessmp.core;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import sqyro.classessmp.ClassesSMP;
import sqyro.classessmp.items.ClassesItems;

public class ClassesCreativeTabs {
    public static final CreativeModeTab CLASSES_TAB = FabricItemGroup.builder().icon(() -> new ItemStack(ClassesItems.BLOODSWORD))
            .title(Component.translatable("itemGroup.classessmp"))
            .displayItems((context, entries) -> {
                entries.accept(ClassesItems.BLOODSWORD);

            }).build();

    public static void register() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "classes"), CLASSES_TAB);
    }
}