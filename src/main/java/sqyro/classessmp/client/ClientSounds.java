package sqyro.classessmp.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import sqyro.classessmp.sounds.ClassesSounds;

public class ClientSounds {
    private static int timer = 0;

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) {
                return;
            }

            if (!ClientPlayerData.getClassID().equals("blood_sword")) {
                return;
            }

            float Fill = ClientPlayerData.getBloodIntensity();

            if (Fill <= 0) {
                return;
            }

            timer--;

            if (timer <= 0) {
                client.player.playSound(ClassesSounds.BLOOD_SWORD_RAGE, Fill, 1F);
                timer = (int)(27 - Fill * 20);
            }
        });
    }
}
