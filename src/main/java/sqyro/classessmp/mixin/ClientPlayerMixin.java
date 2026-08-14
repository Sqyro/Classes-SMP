package sqyro.classessmp.mixin;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sqyro.classessmp.client.ClientPlayerData;
import sqyro.classessmp.items.BloodSwordItem;


@Mixin(Player.class)
public class ClientPlayerMixin {
    @Inject(method = "attack", at = @At("HEAD"))
    private void classes$bloodAttack(Entity target, CallbackInfo ci) {

        Player Self = (Player)(Object)this;

        if (!(Self instanceof LocalPlayer player)) {
            return;
        }

        if (player.getMainHandItem().getItem() instanceof BloodSwordItem) {
            ClientPlayerData.markBloodAttack();
        }
    }
}
