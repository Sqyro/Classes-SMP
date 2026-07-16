package sqyro.classessmp.core;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import sqyro.classessmp.core.SavedData.PlayerClassSavedDataGetter;
import sqyro.classessmp.network.ClassesNetworking;

import java.util.HashMap;
import java.util.Map;

public abstract class PlayerClass {
    protected final ServerPlayer Player;

    protected final Map<String, Long> Cooldowns = new HashMap<>();

    public PlayerClass(ServerPlayer Player) {
        this.Player = Player;
    }

    public abstract String getID();

    public abstract void onTick();
    public abstract void onAttack(Entity Target);
    public abstract void onRespawn();
    public abstract void onKeybind1();
    public abstract void onKeybind2();

    protected boolean isOnCooldown(String Ability) {
        return getCooldownTicks(Ability) > 0;
    }

    protected void setCooldown(String Ability, int Ticks) {
        Cooldowns.put(Ability, Player.level().getGameTime() + Ticks);
        PlayerClassSavedDataGetter.get(Player.level()).setCooldowns(Player.getUUID(), Cooldowns);
        ClassesNetworking.sendCooldownStart(Player, Ability, Ticks);
    }

    protected long getCooldownTicks(String Ability) {
        long End = Cooldowns.getOrDefault(Ability, 0L);
        return (int)Math.max(0, End - Player.level().getGameTime());
    }

    public Map<String, Long> getCooldowns() {
        return Cooldowns;
    }

    public void loadCooldowns(Map<String, Long> cooldowns) {
        Cooldowns.clear();
        Cooldowns.putAll(cooldowns);
    }

    public void tickCooldowns() {
        long currentTick = Player.level().getGameTime();
        Cooldowns.entrySet().removeIf(entry -> entry.getValue() <= currentTick);
    }
}