package sqyro.classessmp.client;

import net.minecraft.resources.Identifier;
import sqyro.classessmp.ClassesSMP;
import sqyro.classessmp.playerclasses.SnowScorpion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientClasses {
    private static final Map<String, ClientClassData> CLASSES = new HashMap<>();

    public static void register() {
        CLASSES.put("snow_scorpion", new ClientClassData(List.of(
                new ClientAbility("ice_pull",
                        Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/ability_cooldowns/ice_pull_bg.png"),
                        Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/ability_cooldowns/ice_pull_color.png"),
                        SnowScorpion.ICE_PULL_COOLDOWN
                ),
                new ClientAbility("ice_prison",
                        Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/ability_cooldowns/ice_prison_bg.png"),
                        Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/ability_cooldowns/ice_prison_color.png"),
                        SnowScorpion.ICE_PRISON_COOLDOWN
                )))
        );
        CLASSES.put("gambler", new ClientClassData(List.of()));
    }

    public static ClientClassData get(String ID) {
        return CLASSES.get(ID);
    }
}
