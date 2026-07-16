package sqyro.classessmp.client;

import java.util.HashMap;
import java.util.Map;

public class FreezeClientCache {
    private static final Map<Integer, Boolean> FROZEN_ENTITIES = new HashMap<>();

    public static void setFrozen(int entityId, boolean frozen) {
        FROZEN_ENTITIES.put(entityId, frozen);
    }

    public static boolean isFrozen(int entityId) {
        return FROZEN_ENTITIES.getOrDefault(entityId, false);
    }

    public static void remove(int entityId) {
        FROZEN_ENTITIES.remove(entityId);
    }

    public static void clear() {
        FROZEN_ENTITIES.clear();
    }
}
