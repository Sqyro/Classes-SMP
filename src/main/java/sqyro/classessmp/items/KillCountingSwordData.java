package sqyro.classessmp.items;

import com.mojang.serialization.Codec;
import net.minecraft.core.UUIDUtil;

import java.util.*;

public class KillCountingSwordData {
    public static final Codec<KillCountingSwordData> CODEC = UUIDUtil.CODEC.listOf().xmap(Kills -> new KillCountingSwordData(new HashSet<>(Kills)), Data -> new ArrayList<>(Data.getKilledPlayers()));
    private final Set<UUID> killedPlayers;

    public KillCountingSwordData() {
        this.killedPlayers = new HashSet<>();
    }

    public KillCountingSwordData(Set<UUID> killedPlayers) {
        this.killedPlayers = killedPlayers;
    }

    public int getKillCount() {
        return killedPlayers.size();
    }

    public KillCountingSwordData addKill(UUID uuid) {
        Set<UUID> newKills = new HashSet<>(killedPlayers);
        newKills.add(uuid);

        return new KillCountingSwordData(newKills);
    }

    public RemovedKillResult removeKill() {
        KillCountingSwordData newData = new KillCountingSwordData();
        newData.killedPlayers.addAll(this.killedPlayers);

        if (newData.killedPlayers.isEmpty()) {
            return null;
        }

        Iterator<UUID> it = newData.killedPlayers.iterator();
        UUID removedUUID = it.next();
        it.remove();

        return new RemovedKillResult(newData, removedUUID);
    }

    public Set<UUID> getKilledPlayers() {
        return killedPlayers;
    }

    public record RemovedKillResult(KillCountingSwordData data, UUID playerUUID) {}
}