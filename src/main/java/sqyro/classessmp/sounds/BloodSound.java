package sqyro.classessmp.sounds;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import sqyro.classessmp.client.ClientPlayerData;

public class BloodSound extends AbstractTickableSoundInstance {
    public static BloodSound INSTANCE;

    public BloodSound() {
        super(ClassesSounds.BLOOD_SWORD_RAGE, SoundSource.PLAYERS, RandomSource.create());

        this.looping = true;
        this.volume = 0;
    }

    public void setVolume(float Volume) {
        this.volume = Volume;
    }

    @Override
    public void tick() {
        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.player == null) {
            stop();
            return;
        }

        this.x = minecraft.player.getX();
        this.y = minecraft.player.getY();
        this.z = minecraft.player.getZ();

        float Intensity = ClientPlayerData.getBloodIntensity();

        this.volume = Intensity;

        if (Intensity <= 0.01f) {
            stop();
        }
    }
}
