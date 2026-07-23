package sqyro.classessmp.client;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import sqyro.classessmp.items.BloodSwordData;
import sqyro.classessmp.items.BloodSwordItem;
import sqyro.classessmp.playerclasses.AncientWarden;

import java.util.HashMap;
import java.util.Map;

public class ClientPlayerData {
    private static String classID = "none";

    private static final Map<String, Integer> cooldowns = new HashMap<>();

    private static int gamblerLevel;
    private static int gamblerLastRoll;
    private static long gamblerLastRollTick;

    private static int bloodAmount;
    private static float bloodIntensity;

    private static long lastBloodAttack;
    private static float bloodFade = 1.0f;

    private static int noiseMeter;

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

    public static void setBloodAmount(int amount) {
        bloodAmount = amount;
    }

    public static int getBloodAmount() {
        return bloodAmount;
    }

    public static void markBloodAttack() {
        lastBloodAttack = System.currentTimeMillis();
    }

    public static float getBloodIntensity() {
        Minecraft minecraft = Minecraft.getInstance();

        boolean holdingBloodSword = minecraft.player != null && minecraft.player.getMainHandItem().getItem() instanceof BloodSwordItem;

        if (!holdingBloodSword) {
            bloodFade -= 0.005f;
        }
        else if (System.currentTimeMillis() - lastBloodAttack < 10 * 1000) {
            bloodFade = 1.0f;
        }

        float Target = bloodAmount / (float) BloodSwordData.MAX_DAMAGE;

        bloodIntensity += (Target * bloodFade - bloodIntensity) * 0.02f;

        return Mth.clamp(bloodIntensity, 0, 1);
    }

    public static void setNoiseMeter(int Value) {
        noiseMeter = Mth.clamp(Value, 0, AncientWarden.NOISE_METER_MAX_VALUE);
    }

    public static int getNoiseMeter() {
        return noiseMeter;
    }

    public static void clear() {
        classID = "none";
        cooldowns.clear();

        gamblerLevel = 0;
        gamblerLastRoll = 0;
        gamblerLastRollTick = 0;

        bloodAmount = 0;
        bloodIntensity = 0;
        bloodFade = 1.0f;
        lastBloodAttack = 0;

        noiseMeter = 0;
    }

    public static void tick() {
        cooldowns.replaceAll((ID, Ticks) -> Math.max(0, Ticks - 1));
        cooldowns.entrySet().removeIf(entry -> entry.getValue() <= 0);
    }
}