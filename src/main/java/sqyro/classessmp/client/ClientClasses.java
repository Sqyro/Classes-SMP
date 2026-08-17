package sqyro.classessmp.client;

import net.minecraft.resources.Identifier;
import sqyro.classessmp.ClassesSMP;
import sqyro.classessmp.playerclasses.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientClasses {
    private static final Map<String, ClientClassData> CLASSES = new HashMap<>();

    public static void register() {
        CLASSES.put("snow_scorpio", new ClientClassData(List.of(
                new ClientAbility("ice_pull",
                        Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/ability_cooldowns/ice_pull_bg.png"),
                        Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/ability_cooldowns/ice_pull_color.png"),
                        SnowScorpio.ICE_PULL_COOLDOWN
                ),
                new ClientAbility("ice_prison",
                        Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/ability_cooldowns/ice_prison_bg.png"),
                        Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/ability_cooldowns/ice_prison_color.png"),
                        SnowScorpio.ICE_PRISON_COOLDOWN
                )))
        );
        CLASSES.put("gambler", new ClientClassData(List.of()));
        CLASSES.put("blood_sword", new ClientClassData(List.of(
                new ClientAbility("life_steal",
                        Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/ability_cooldowns/life_steal_bg.png"),
                        Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/ability_cooldowns/life_steal_color.png"),
                        BloodSword.LIFE_STEAL_COOLDOWN
                )))
        );
        CLASSES.put("pickpocket", new ClientClassData(List.of(
                new ClientAbility("steal",
                        Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/ability_cooldowns/steal_bg.png"),
                        Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/ability_cooldowns/steal_color.png"),
                        Pickpocket.STEAL_COOLDOWN
                )))
        );
        CLASSES.put("thunderbolt", new ClientClassData(List.of(
                new ClientAbility("thunderstorm",
                        Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/ability_cooldowns/thunderstorm_bg.png"),
                        Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/ability_cooldowns/thunderstorm_color.png"),
                        Thunderbolt.THUNDERSTORM_COOLDOWN
                ),
                new ClientAbility("lightning_dash",
                        Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/ability_cooldowns/lightning_dash_bg.png"),
                        Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/ability_cooldowns/lightning_dash_color.png"),
                        Thunderbolt.LIGHTNING_DASH_COOLDOWN
                ),
                new ClientAbility("chain_lightning",
                        Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/ability_cooldowns/chain_lightning_bg.png"),
                        Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/ability_cooldowns/chain_lightning_color.png"),
                        Thunderbolt.CHAIN_LIGHTNING_COOLDOWN
                )))
        );
        CLASSES.put("terrorist", new ClientClassData(List.of()));
        CLASSES.put("ancient_warden", new ClientClassData(List.of(
                new ClientAbility("noise_meter",
                        Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/ability_cooldowns/noise_meter_bg.png"),
                        Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/ability_cooldowns/noise_meter_color.png"),
                        AncientWarden.NOISE_METER_COOLDOWN
                ),
                new ClientAbility("blinding",
                        Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/ability_cooldowns/blinding_bg.png"),
                        Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/ability_cooldowns/blinding_color.png"),
                        AncientWarden.BLINDING_COOLDOWN
                ),
                new ClientAbility("sonic_boom",
                        Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/ability_cooldowns/sonic_boom_bg.png"),
                        Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/ability_cooldowns/sonic_boom_color.png"),
                        AncientWarden.SONIC_BOOM_COOLDOWN
                )))
        );
        CLASSES.put("clown", new ClientClassData(List.of(
                new ClientAbility("circus_cage",
                        Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/ability_cooldowns/circus_cage_bg.png"),
                        Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/ability_cooldowns/circus_cage_color.png"),
                        Clown.CIRCUS_CAGE_COOLDOWN
                ),
                new ClientAbility("tri_card_attack",
                        Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/ability_cooldowns/tri_card_attack_bg.png"),
                        Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/ability_cooldowns/tri_card_attack_color.png"),
                        Clown.TRI_CARD_ATTACK_COOLDOWN
                ),
                new ClientAbility("circus_finale",
                        Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/ability_cooldowns/circus_finale_bg.png"),
                        Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/ability_cooldowns/circus_finale_color.png"),
                        Clown.CIRCUS_FINALE_COOLDOWN
                )))
        );
        CLASSES.put("builder", new ClientClassData(List.of()));
        CLASSES.put("the_vessel", new ClientClassData(List.of()));
        CLASSES.put("sigeon_pex", new ClientClassData(List.of(
                new ClientAbility("sigeon_rooting",
                        Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/ability_cooldowns/sigeon_rooting_bg.png"),
                        Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/ability_cooldowns/sigeon_rooting_color.png"),
                        SigeonPex.SIGEON_ROOTING_COOLDOWN
                )
        )));
        CLASSES.put("death_jester", new ClientClassData(List.of(
                new ClientAbility("carnival_frenzy",
                        Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/ability_cooldowns/carnival_frenzy_bg.png"),
                        Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/ability_cooldowns/carnival_frenzy_color.png"),
                        DeathJester.CARNIVAL_FRENZY_COOLDOWN
                )
        )));
        CLASSES.put("sans", new ClientClassData(List.of()));
    }

    public static ClientClassData get(String ID) {
        return CLASSES.get(ID);
    }
}
