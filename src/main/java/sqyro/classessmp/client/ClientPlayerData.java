package sqyro.classessmp.client;

import net.minecraft.client.Minecraft;
import sqyro.classessmp.ClassesSMP;

import java.util.HashMap;
import java.util.Map;

public class ClientPlayerData {
    private static String classID = "none";

    private static final Map<String, Integer> cooldowns = new HashMap<>();

    private static int gamblerLevel;
    private static int gamblerLastRoll;
    private static long gamblerLastRollTick;

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

    public static void setGamblerLevel(int level) {
        gamblerLevel = level;
    }

    public static int getGamblerLevel() {
        return gamblerLevel;
    }

    public static void setGamblerRoll(int Roll) {
        gamblerLastRoll = Roll;
        gamblerLastRollTick = Minecraft.getInstance().level.getGameTime();
    }

    public static int getGamblerRoll() {
        return gamblerLastRoll;
    }

    public static boolean shouldRenderGamblerRoll() {
        Minecraft minecraft = Minecraft.getInstance();
        boolean shouldRender = minecraft.level != null && minecraft.level.getGameTime() - gamblerLastRollTick < 40;
        return shouldRender;
    }

    public static void replaceCooldowns(Map<String,Integer> Values) {
        cooldowns.clear();
        cooldowns.putAll(Values);
    }

    public static void clear() {
        classID = "none";
        cooldowns.clear();

        gamblerLevel = 0;
        gamblerLastRoll = 0;
        gamblerLastRollTick = 0;
    }

    public static void tick() {
        cooldowns.replaceAll((ID, Ticks) -> Math.max(0, Ticks - 1));
        cooldowns.entrySet().removeIf(entry -> entry.getValue() <= 0);
    }
}