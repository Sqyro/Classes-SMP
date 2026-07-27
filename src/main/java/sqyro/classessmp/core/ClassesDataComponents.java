package sqyro.classessmp.core;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import sqyro.classessmp.ClassesSMP;
import sqyro.classessmp.items.KillCountingSwordData;

public class ClassesDataComponents {
    public static final DataComponentType<KillCountingSwordData> KILL_COUNTING_SWORD_DATA = Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "kill_counting_sword_data"), DataComponentType.<KillCountingSwordData>builder().persistent(KillCountingSwordData.CODEC).build());

    public static void register() {}
}