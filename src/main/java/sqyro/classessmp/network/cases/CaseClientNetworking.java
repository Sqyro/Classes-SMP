package sqyro.classessmp.network.cases;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.client.Minecraft;
import sqyro.classessmp.client.GUI.screen.CaseRollScreen;

public class CaseClientNetworking {
    public static void registerClient() {
        PayloadTypeRegistry.playS2C().register(CaseRollResultPayload.TYPE, CaseRollResultPayload.CODEC);

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