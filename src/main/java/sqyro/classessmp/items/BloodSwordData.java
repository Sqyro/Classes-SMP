package sqyro.classessmp.items;

import com.mojang.serialization.Codec;
import net.minecraft.core.UUIDUtil;

import java.util.*;

public class BloodSwordData {
    public static final Codec<BloodSwordData> CODEC = UUIDUtil.CODEC.listOf().xmap(Kills -> new BloodSwordData(new HashSet<>(Kills)), Data -> new ArrayList<>(Data.getKilledPlayers()));

    public static final int MAX_DAMAGE = 15;

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

    public BloodSwordData removeKill() {
        BloodSwordData newData = new BloodSwordData();
        newData.killedPlayers.addAll(this.killedPlayers);

        if (!newData.killedPlayers.isEmpty()) {
            Iterator<UUID> it = newData.killedPlayers.iterator();
            it.next();
            it.remove();
        } else {
            return null;
        }

        return newData;
    }

    public Set<UUID> getKilledPlayers() {
        return killedPlayers;
    }

    public int getKillCount() {
        return killedPlayers.size();
    }

    public float getFill() {
        return Math.min(getKillCount() / (float) MAX_DAMAGE, 1.0F);
    }
}