package sqyro.classessmp.mixin;

import net.minecraft.network.protocol.game.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sqyro.classessmp.effect.ClassesEffects;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerPacketMixin {
    @Shadow
    public ServerPlayer player;

    @Inject(method = "handleMovePlayer", at = @At("HEAD"), cancellable = true)
    private void classessmp$stopMovement(ServerboundMovePlayerPacket Packet, CallbackInfo callbackInfo) {
        if (player.hasEffect(ClassesEffects.FREEZING)) {
            player.setYRot(player.yRotO);
            player.setXRot(player.xRotO);

            player.setYHeadRot(player.yRotO);
            player.setYBodyRot(player.yRotO);
            callbackInfo.cancel();
        }
    }

    @Inject(method = "handleInteract", at = @At("HEAD"), cancellable = true)
    private void classessmp$cancelAttack(ServerboundInteractPacket Packet, CallbackInfo callbackInfo) {
        if (player.hasEffect(ClassesEffects.FREEZING)) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = "handlePlayerAction", at = @At("HEAD"), cancellable = true)
    private void classessmp$cancelBlockBreaking(ServerboundPlayerActionPacket Packet, CallbackInfo callbackInfo) {
        if (player.hasEffect(ClassesEffects.FREEZING)) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = "handleUseItem", at = @At("HEAD"), cancellable = true)
    private void classessmp$cancelUseItem(ServerboundUseItemPacket Packet, CallbackInfo callbackInfo) {
        if (player.hasEffect(ClassesEffects.FREEZING)) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = "handleUseItemOn", at = @At("HEAD"), cancellable = true)
    private void classessmp$cancelUseItemOn(ServerboundUseItemOnPacket Packet, CallbackInfo callbackInfo) {
        if (player.hasEffect(ClassesEffects.FREEZING)) {
            callbackInfo.cancel();
        }
    }
}