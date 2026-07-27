package sqyro.classessmp.items;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.level.block.DispenserBlock;
import sqyro.classessmp.ClassesSMP;

import java.util.function.Function;

public class ClassesItems {
    public static final Item BLOODSWORD = registerItem("bloodsword", settings -> new BloodSwordItem(settings.sword(ToolMaterial.DIAMOND, 2.0F, -2.4F).rarity(Rarity.RARE)));
    public static final Item CLOWNSWORD = registerItem("clownsword", settings -> new ClownSwordItem(settings.sword(ToolMaterial.DIAMOND, 3.0F, -2.4F).rarity(Rarity.RARE)));
    public static final Item C4 = registerItem("c4", settings -> new C4Item(settings));

    private static Item registerItem(String Name, Function<Item.Properties, Item> Function) {
        Identifier ID = Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, Name);
        return Registry.register(BuiltInRegistries.ITEM, ID, Function.apply(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, ID))));
    }

    public static void register() {
        DispenserBlock.registerBehavior(C4, new C4DispenseBehaviour());
    }
}