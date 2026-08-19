package sqyro.classessmp.sounds;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SansClientSounds {
    private static final Map<UUID, ButItRefusedSoundInstance> sounds = new HashMap<>();

    public static void startButItRefused(Player player) {
        UUID uuid = player.getUUID();

        stopButItRefused(player);

        ButItRefusedSoundInstance sound = new ButItRefusedSoundInstance(player);

        sounds.put(uuid, sound);

        Minecraft.getInstance().getSoundManager().play(sound);
    }

    public static void stopButItRefused(Player player) {
        UUID uuid = player.getUUID();

        ButItRefusedSoundInstance sound = sounds.remove(uuid);

        if (sound != null) {
            sound.stopSound();
        }
    }
}
