package sqyro.classessmp.items;

import com.mojang.serialization.Codec;
import net.minecraft.core.UUIDUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class BloodSwordData {
    public static final Codec<BloodSwordData> CODEC = UUIDUtil.CODEC.listOf().xmap(Kills -> new BloodSwordData(new HashSet<>(Kills)), Data -> new ArrayList<>(Data.getKilledPlayers()));

    private static final int MAX_DAMAGE = 20;

    private final Set<UUID> killedPlayers;

    public BloodSwordData() {
        this.killedPlayers = new HashSet<>();
    }

    public BloodSwordData(Set<UUID> killedPlayers) {
        this.killedPlayers = killedPlayers;
    }

    public int getBonusDamage() {
        return Math.min(killedPlayers.size(), MAX_DAMAGE);
    }

    public BloodSwordData addKill(UUID uuid) {
        Set<UUID> newKills = new HashSet<>(killedPlayers);
        newKills.add(uuid);

        return new BloodSwordData(newKills);
    }

    public Set<UUID> getKilledPlayers() {
        return killedPlayers;
    }

    public int getKillCount() {
        return killedPlayers.size();
    }
}