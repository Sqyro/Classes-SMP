package sqyro.classessmp.items;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Util;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import sqyro.classessmp.ClassesSMP;

import java.util.EnumMap;

public class ClassesArmorMaterials {
    private static final ResourceKey<? extends Registry<EquipmentAsset>> EQUIPMENT_ASSET_REGISTRY = ResourceKey.createRegistryKey(Identifier.withDefaultNamespace("equipment_asset"));
    public static final ResourceKey<EquipmentAsset> FEATHER_ASSET = ResourceKey.create(EQUIPMENT_ASSET_REGISTRY, Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "feather"));

    public static final ArmorMaterial FEATHER = new ArmorMaterial(8,
            Util.make(new EnumMap<>(ArmorType.class), map -> {
                map.put(ArmorType.BOOTS, 1);
                map.put(ArmorType.LEGGINGS, 2);
                map.put(ArmorType.CHESTPLATE, 3);
                map.put(ArmorType.HELMET, 1);
                map.put(ArmorType.BODY, 3);
            }), 18, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "feather_repair")), FEATHER_ASSET);
}
