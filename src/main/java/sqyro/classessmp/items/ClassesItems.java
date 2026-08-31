package sqyro.classessmp.items;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.block.DispenserBlock;
import sqyro.classessmp.ClassesSMP;
import sqyro.classessmp.entities.ClassesEntities;

import java.util.function.Function;

public class ClassesItems {
    public static final Item BLOODSWORD = registerItem("bloodsword", settings -> new BloodSwordItem(settings.sword(ToolMaterial.DIAMOND, 2.0F, -2.4F).rarity(Rarity.RARE)));
    public static final Item CLOWNSWORD = registerItem("clownsword", settings -> new ClownSwordItem(settings.sword(ToolMaterial.DIAMOND, 3.0F, -2.4F).rarity(Rarity.RARE)));
    public static final Item LUNARIS_SWORD = registerItem("lunaris_sword", settings -> new LunarisSwordItem(settings.sword(ToolMaterial.DIAMOND, 3.0F, -2.4F).rarity(Rarity.RARE)));
    public static final Item C4 = registerItem("c4", settings -> new C4Item(settings));

    public static final Item FEATHER_HELMET = registerItem("feather_helmet", new Item.Properties().humanoidArmor(ClassesArmorMaterials.FEATHER, ArmorType.HELMET));
    public static final Item FEATHER_CHESTPLATE = registerItem("feather_chestplate", new Item.Properties().humanoidArmor(ClassesArmorMaterials.FEATHER, ArmorType.CHESTPLATE));
    public static final Item FEATHER_LEGGINGS = registerItem("feather_leggings", new Item.Properties().humanoidArmor(ClassesArmorMaterials.FEATHER, ArmorType.LEGGINGS));
    public static final Item FEATHER_BOOTS = registerItem("feather_boots", new Item.Properties().humanoidArmor(ClassesArmorMaterials.FEATHER, ArmorType.BOOTS));

    public static final Item ELEPHANT_SPAWNER = registerItem("elephant_spawner", settings -> new SpawnerItem(settings, "sigeon_pex", ClassesEntities.ELEPHANT));
    public static final Item LASER_CHICKEN_SPAWNER = registerItem("laser_chicken_spawner", settings -> new SpawnerItem(settings, "sigeon_pex", ClassesEntities.LASER_CHICKEN));

    public static final Item CASE_KEY = registerItem("case_key", settings -> new Item(settings.rarity(Rarity.UNCOMMON)));
    public static final Item REMOVE_CLASS_ITEM = registerItem("remove_class_item", settings -> new RemoveClassItem(settings.rarity(Rarity.EPIC).fireResistant()));

    private static Item registerItem(String Name, Function<Item.Properties, Item> Function) {
        Identifier ID = Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, Name);
        return Registry.register(BuiltInRegistries.ITEM, ID, Function.apply(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, ID))));
    }

    private static Item registerItem(String name, Item.Properties properties) {
        Identifier ID = Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, name);
        return Registry.register(BuiltInRegistries.ITEM, ID, new Item(properties.setId(ResourceKey.create(Registries.ITEM, ID))));
    }

    public static final TagKey<Item> KNIVES = registerItemTag("knives");

    private static TagKey<Item> registerItemTag(String path) {
        return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, path));
    }

    public static void register() {
        DispenserBlock.registerBehavior(C4, new C4DispenseBehaviour());
    }
}