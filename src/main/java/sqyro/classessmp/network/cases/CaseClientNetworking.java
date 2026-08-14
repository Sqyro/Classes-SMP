package sqyro.classessmp.network.cases;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import sqyro.classessmp.client.GUI.screen.CaseRollScreen;

public class CaseClientNetworking {
    public static void registerClient() {
        ClientPlayNetworking.registerGlobalReceiver(CaseRollResultPayload.TYPE,
                (payload, context) -> {
                    context.client().execute(() -> {
                        Minecraft minecraft = context.client();
                        minecraft.setScreen(new CaseRollScreen(payload.reward().copy()));
                    });
                }
        );
    }
}