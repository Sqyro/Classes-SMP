package sqyro.classessmp.effect;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;
import sqyro.classessmp.ClassesSMP;

public class ClassesEffects {
    public static final Holder<MobEffect> FREEZING = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "freezing"), new FreezingEffect());

    public static void register() {}
}