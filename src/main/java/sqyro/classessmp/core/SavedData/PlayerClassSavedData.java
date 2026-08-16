package sqyro.classessmp.core.SavedData;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import sqyro.classessmp.ClassesSMP;
import sqyro.classessmp.network.ClassesNetworking;
import sqyro.classessmp.playerclasses.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerClassSavedData extends SavedData {
    private static final Codec<Map<String, String>> CLASS_CODEC = Codec.unboundedMap(Codec.STRING, Codec.STRING);
    private static final Codec<Map<String, Map<String, Long>>> COOLDOWN_CODEC = Codec.unboundedMap(Codec.STRING, Codec.unboundedMap(Codec.STRING, Codec.LONG));
    private static final Codec<Map<String, Integer>> GAMBLER_LEVEL_CODEC = Codec.unboundedMap(Codec.STRING, Codec.INT);
    private static final Codec<Map<String, Integer>> NOISE_METER_CODEC = Codec.unboundedMap(Codec.STRING, Codec.INT);

    private static final Codec<PlayerClassSavedData> CODEC = RecordCodecBuilder.create(instance -> instance.group(CLASS_CODEC.fieldOf("classes")
            .forGetter(data -> data.serializeClasses()), COOLDOWN_CODEC.fieldOf("cooldowns")
            .forGetter(data -> data.serializeCooldowns()), GAMBLER_LEVEL_CODEC.fieldOf("gambler_levels")
            .forGetter(PlayerClassSavedData::serializeGamblerLevels),
            NOISE_METER_CODEC.fieldOf("noise_meters").forGetter(PlayerClassSavedData::serializeNoiseMeters)
    ).apply(instance, PlayerClassSavedData::new));

    public static final SavedDataType<PlayerClassSavedData> TYPE = new SavedDataType<>("player_classes", PlayerClassSavedData::new, CODEC, null);

    private final Map<UUID, String> Classes = new HashMap<>();
    private final Map<UUID, Map<String, Long>> Cooldowns = new HashMap<>();

    private final Map<UUID, Integer> gamblerLevels = new HashMap<>();

    private final Map<UUID, Integer> noiseMeters = new HashMap<>();

    public PlayerClassSavedData() {
    }

    private PlayerClassSavedData(Map<String, String> classes, Map<String, Map<String, Long>> cooldowns, Map<String, Integer> gamblerLevels, Map<String, Integer> noiseMeters) {
        classes.forEach((uuID, ID) -> Classes.put(UUID.fromString(uuID), ID));
        cooldowns.forEach((uuID, Map) -> Cooldowns.put(UUID.fromString(uuID), new HashMap<>(Map)));
        gamblerLevels.forEach((uuid, level) -> this.gamblerLevels.put(UUID.fromString(uuid), level));
        noiseMeters.forEach((uuID, Noise) -> this.noiseMeters.put(UUID.fromString(uuID), Noise));
    }

    private Map<String, String> serializeClasses() {
        Map<String, String> Map = new HashMap<>();
        Classes.forEach((uuID, ID) -> Map.put(uuID.toString(), ID));

        return Map;
    }

    private Map<String, Map<String, Long>> serializeCooldowns() {
        Map<String, Map<String, Long>> Map = new HashMap<>();
        Cooldowns.forEach((uuID, cooldowns) -> Map.put(uuID.toString(), new HashMap<>(cooldowns)));

        return Map;
    }

    private Map<String, Integer> serializeGamblerLevels() {
        Map<String, Integer> Map = new HashMap<>();
        gamblerLevels.forEach((uuID, level) -> Map.put(uuID.toString(), level));

        return Map;
    }

    private Map<String, Integer> serializeNoiseMeters() {
        Map<String, Integer> Map = new HashMap<>();
        noiseMeters.forEach((uuID, Noise) -> Map.put(uuID.toString(), Noise));
        return Map;
    }

    public void setClass(UUID uuID, String ID) {
        Classes.put(uuID, ID);
        ClassesSMP.LOGGER.info("Saving {} as {}", uuID, ID);
        setDirty();
    }

    public String getClass(UUID uuID) {
        String ID = Classes.getOrDefault(uuID, "none");
        ClassesSMP.LOGGER.info("Loaded class {} to {}", uuID, ID);

        return ID;
    }

    public void removeClass(Player Player) {
        if (Player.getAbilities().mayfly && !Player.isCreative() && !Player.isSpectator()) {
            Player.getAbilities().mayfly = false;
            Player.getAbilities().flying = false;
            Player.onUpdateAbilities();
        }

        AttributeInstance damage = Player.getAttribute(Attributes.ATTACK_DAMAGE);
        AttributeInstance health = Player.getAttribute(Attributes.MAX_HEALTH);
        AttributeInstance movementSpeed = Player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (damage != null) {
            damage.removeModifier(TheVessel.DAMAGE_MODIFIER_ID);
        }
        if (health != null) {
            health.removeModifier(TheVessel.HEALTH_MODIFIER_ID);
        }
        if (health != null) {
            movementSpeed.removeModifier(Clown.MOVEMENT_SPEED_ID);
            movementSpeed.removeModifier(DeathJester.SPEED_MODIFIER_ID);
        }

        Player.getAbilities().setFlyingSpeed(Builder.DEFAULT_FLYING_SPEED);

        Classes.remove(Player.getUUID());
        setDirty();
    }

    public void setCooldowns(UUID uuID, Map<String, Long> cooldowns) {
        Cooldowns.put(uuID, new HashMap<>(cooldowns));
        setDirty();
    }

    public Map<String, Long> getCooldowns(UUID uuID) {
        return new HashMap<>(Cooldowns.getOrDefault(uuID, Map.of()));
    }

    public void clearCooldowns(UUID uuID) {
        Cooldowns.remove(uuID);
        setDirty();
    }

    public int getGamblerLevel(UUID uuID) {
        return gamblerLevels.getOrDefault(uuID, 0);
    }

    public void setGamblerLevel(UUID uuID, int Level) {
        gamblerLevels.put(uuID, Level);
        setDirty();
    }

    public int getNoiseMeter(UUID uuID) {
        return noiseMeters.getOrDefault(uuID, 0);
    }

    public void setNoiseMeter(ServerPlayer Player, int Value) {
        int NewValue = Math.clamp(Value, 0, AncientWarden.NOISE_METER_MAX_VALUE);

        if (noiseMeters.getOrDefault(Player.getUUID(), 0) == NewValue) {
            return;
        }

        noiseMeters.put(Player.getUUID(), NewValue);
        setDirty();

        ClassesNetworking.sendNoiseMeter(Player, NewValue);
    }

    public void addNoise(ServerPlayer Player, int Amount) {
        setNoiseMeter(Player,getNoiseMeter(Player.getUUID()) + Amount);
    }
}