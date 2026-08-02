package sqyro.classessmp.playerclasses;

import net.minecraft.server.level.ServerPlayer;
import sqyro.classessmp.core.PlayerClass;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class PlayerClasses {
    public static final Map<String, Function<ServerPlayer, PlayerClass>> CLASSES = new HashMap<>();

    public static void register() {
        CLASSES.put("snow_scorpio", SnowScorpio::new);
        CLASSES.put("gambler", Gambler::new);
        CLASSES.put("blood_sword", BloodSword::new);
        CLASSES.put("pickpocket", Pickpocket::new);
        CLASSES.put("thunderbolt", Thunderbolt::new);
        CLASSES.put("terrorist", Terrorist::new);
        CLASSES.put("ancient_warden", AncientWarden::new);
        CLASSES.put("clown", Clown::new);
        CLASSES.put("builder", Builder::new);
        CLASSES.put("the_vessel", TheVessel::new);
        CLASSES.put("sigeon_pex", SigeonPex::new);
    }

    public static PlayerClass create(String ID, ServerPlayer Player) {
        Function<ServerPlayer, PlayerClass> Builder = CLASSES.get(ID);

        if (Builder == null) {
            return null;
        }

        return Builder.apply(Player);
    }

    public static Collection<String> getIDs() {
        return CLASSES.keySet();
    }
}