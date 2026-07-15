package sqyro.classessmp.core.SavedData;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import sqyro.classessmp.ClassesSMP;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerClassSavedData extends SavedData {
    private static final Codec<PlayerClassSavedData> CODEC = Codec.unboundedMap(Codec.STRING, Codec.STRING).fieldOf("classes").codec().xmap(Original -> {
        PlayerClassSavedData savedData = new PlayerClassSavedData();
        for (Map.Entry<String, String> Entry : Original.entrySet()) {
            savedData.Classes.put(UUID.fromString(Entry.getKey()), Entry.getValue());
        }
        return savedData;
        }, savedData -> {
        Map<String, String> Converted = new HashMap<>();
        for (Map.Entry<UUID, String> Entry : savedData.Classes.entrySet()) {
            Converted.put(Entry.getKey().toString(), Entry.getValue());
        }
        return Converted;
    });

    public static final SavedDataType<PlayerClassSavedData> TYPE = new SavedDataType<>("player_classes", PlayerClassSavedData::new, CODEC, null);

    private final Map<UUID, String> Classes = new HashMap<>();

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
}