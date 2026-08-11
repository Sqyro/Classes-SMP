package sqyro.classessmp.sounds;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import sqyro.classessmp.ClassesSMP;

public class ClassesSounds {
    public static final SoundEvent ICE_PRISON_HIT = register("ice_prison_hit");
    public static final SoundEvent ICE_PULL = register("ice_pull");

    public static final SoundEvent GAMBLER_LOOSE = register("gambler_loose");
    public static final SoundEvent GAMBLER_JACKPOT = register("gambler_jackpot");

    public static final SoundEvent BLOOD_SWORD_UPGRADE = register("blood_sword_upgrade");
    public static final SoundEvent BLOOD_SWORD_CONSUME = register("blood_sword_consume");
    public static final SoundEvent BLOOD_SWORD_RAGE = register("blood_sword_rage");

    public static final SoundEvent THUNDERSTORM = register("thunderstorm");
    public static final SoundEvent LIGHTNING_DASH = register("lightning_dash");
    public static final SoundEvent CHAIN_LIGHTNING = register("chain_lightning");
    public static final SoundEvent SHOCKED_IMPACT = register("shocked_impact");

    public static final SoundEvent CLOWN_KILL = register("clown_kill");

    public static final SoundEvent BOSSMINER2000_SQUISH = register("bossminer2000_squish");

    private static SoundEvent register(String SoundName) {
        Identifier ID = Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, SoundName);

        return Registry.register(BuiltInRegistries.SOUND_EVENT, ID, SoundEvent.createVariableRangeEvent(ID));
    }

    public static void register() {}
}