package sqyro.classessmp.mixin;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sqyro.classessmp.client.ClientPlayerData;
import sqyro.classessmp.core.PlayerClass;
import sqyro.classessmp.core.PlayerClassHolder;
import sqyro.classessmp.items.BloodSwordItem;
import sqyro.classessmp.playerclasses.BloodSword;
import sqyro.classessmp.playerclasses.Gambler;

@Mixin(Player.class)
public abstract class PlayerMixin {
    @Shadow
    @Final
    public InventoryMenu inventoryMenu;

    @Inject(method = "attack", at = @At("HEAD"))
    private void classes$beforeAttack(Entity Target, CallbackInfo callbackInfo) {
        Player Self = (Player)(Object)this;

        if (!(Self instanceof ServerPlayer Player)) {
            return;
        }

        PlayerClass playerClass = ((PlayerClassHolder)Player).getPlayerClass();

        if (playerClass != null) {
            playerClass.beginAttack(Target);
        }
    }

    @Inject(method = "attack", at = @At("RETURN"))
    private void classes$afterAttack(Entity Target, CallbackInfo callbackInfo) {
        Player Self = (Player)(Object)this;

        if (!(Self instanceof ServerPlayer Player)) {
            return;
        }

        PlayerClass playerClass = ((PlayerClassHolder)Player).getPlayerClass();

        if (playerClass != null) {
            playerClass.endAttack();
        }
    }

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