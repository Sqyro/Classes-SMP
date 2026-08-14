package sqyro.classessmp.network.cases;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import sqyro.classessmp.blocks.entity.CaseBlockEntity;
import sqyro.classessmp.client.GUI.CaseMenu;

public class CaseNetworking {
    public static void registerServer() {
        PayloadTypeRegistry.playC2S().register(OpenCasePayload.TYPE, OpenCasePayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(OpenCasePayload.TYPE,
                (payload, context) -> {
                    ServerPlayer player = context.player();

                    context.server().execute(() -> {
                        if (!(player.containerMenu instanceof CaseMenu menu)) {
                            return;
                        }

                        CaseBlockEntity blockEntity = menu.getBlockEntity();

                        if (blockEntity == null) {
                            return;
                        }

                        blockEntity.tryOpenCase(player);
                    });
                }
        );
    }

    public static void sendRollResult(ServerPlayer player, ItemStack reward) {
        ServerPlayNetworking.send(
                player,
                new CaseRollResultPayload(reward.copy())
        );
    }
}