package sqyro.classessmp.sounds;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class ButItRefusedSoundInstance extends AbstractTickableSoundInstance {
    private final UUID playerUUID;

    public ButItRefusedSoundInstance(Player player) {
        super(ClassesSounds.THE_SLAUGHTER_CONTINUES, SoundSource.PLAYERS, SoundInstance.createUnseededRandom());

        this.playerUUID = player.getUUID();

        this.looping = true;
        this.delay = 0;

        this.volume = 1.0f;
        this.pitch = 1.0f;

        this.x = player.getX();
        this.y = player.getY();
        this.z = player.getZ();
    }

    @Override
    public void tick() {
        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.level == null) {
            stop();
            return;
        }

        Player player = minecraft.level.getPlayerByUUID(playerUUID);

        if (player == null) {
            stop();
            return;
        }

        this.x = player.getX();
        this.y = player.getY();
        this.z = player.getZ();
    }

    public void stopSound() {
        super.stop();
    }
}
