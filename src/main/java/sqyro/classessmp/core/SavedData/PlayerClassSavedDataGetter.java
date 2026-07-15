package sqyro.classessmp.core.SavedData;

import net.minecraft.server.level.ServerLevel;

public class PlayerClassSavedDataGetter {
    public static PlayerClassSavedData get(ServerLevel Level) {
        return Level.getServer().overworld().getDataStorage().computeIfAbsent(PlayerClassSavedData.TYPE);
    }
}