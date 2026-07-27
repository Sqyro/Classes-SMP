package sqyro.classessmp.client;

import java.util.HashMap;
import java.util.Map;

public class RootingClientCache {
    private static final Map<Integer, Boolean> ROOTED_ENTITIES = new HashMap<>();

    public static void setRooted(int entityId, boolean rooted) {
        ROOTED_ENTITIES.put(entityId, rooted);
    }

    public static boolean isRooted(int entityId) {
        return ROOTED_ENTITIES.getOrDefault(entityId, false);
    }

    public static void remove(int entityId) {
        ROOTED_ENTITIES.remove(entityId);
    }

    public static void clear() {
        ROOTED_ENTITIES.clear();
    }
}
