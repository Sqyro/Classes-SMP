package sqyro.classessmp.core.SavedData;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import sqyro.classessmp.ClassesSMP;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerClassSavedData extends SavedData {
    private static final Codec<Map<String, String>> CLASS_CODEC = Codec.unboundedMap(Codec.STRING, Codec.STRING);
    private static final Codec<Map<String, Map<String, Long>>> COOLDOWN_CODEC = Codec.unboundedMap(Codec.STRING, Codec.unboundedMap(Codec.STRING, Codec.LONG));

    private static final Codec<PlayerClassSavedData> CODEC = RecordCodecBuilder.create(instance -> instance.group(CLASS_CODEC.fieldOf("classes").forGetter(data -> data.serializeClasses()), COOLDOWN_CODEC.fieldOf("cooldowns").forGetter(data -> data.serializeCooldowns())).apply(instance, PlayerClassSavedData::new));

    public static final SavedDataType<PlayerClassSavedData> TYPE = new SavedDataType<>("player_classes", PlayerClassSavedData::new, CODEC, null);

    private final Map<UUID, String> Classes = new HashMap<>();
    private final Map<UUID, Map<String, Long>> Cooldowns = new HashMap<>();

    public PlayerClassSavedData() {
    }

    private PlayerClassSavedData(Map<String, String> classes, Map<String, Map<String, Long>> cooldowns) {
        classes.forEach((uuID, ID) -> Classes.put(UUID.fromString(uuID), ID));
        cooldowns.forEach((uuID, Map) -> Cooldowns.put(UUID.fromString(uuID), new HashMap<>(Map)));
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
}