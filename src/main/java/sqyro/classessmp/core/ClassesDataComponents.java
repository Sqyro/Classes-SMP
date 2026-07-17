package sqyro.classessmp.core;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import sqyro.classessmp.ClassesSMP;
import sqyro.classessmp.items.BloodSwordData;

public class ClassesDataComponents {
    public static final DataComponentType<BloodSwordData> BLOOD_SWORD_DATA = Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "blood_sword_data"), DataComponentType.<BloodSwordData>builder().persistent(BloodSwordData.CODEC).build());

    public static void register() {}
}