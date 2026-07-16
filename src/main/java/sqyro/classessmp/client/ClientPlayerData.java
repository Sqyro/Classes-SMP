package sqyro.classessmp.client;

import java.util.HashMap;
import java.util.Map;

public class ClientPlayerData {
    private static String classID = "none";

    private static final Map<String, Integer> cooldowns = new HashMap<>();

    public static void setClass(String ID) {
        classID = ID;
    }

    public static String getClassID() {
        return classID;
    }

    public static void setCooldown(String Ability, int Ticks) {
        cooldowns.put(Ability, Ticks);
    }

    public static int getCooldown(String Ability) {
        return cooldowns.getOrDefault(Ability, 0);
    }


    public static void replaceCooldowns(Map<String,Integer> Values) {
        cooldowns.clear();
        cooldowns.putAll(Values);
    }

    public static void clear() {
        classID = "none";
        cooldowns.clear();
    }

    public static void tick() {
        cooldowns.replaceAll((ID, Ticks) -> Math.max(0, Ticks - 1));
        cooldowns.entrySet().removeIf(entry -> entry.getValue() <= 0);
    }
}
