package sqyro.classessmp.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sqyro.classessmp.core.PlayerClass;
import sqyro.classessmp.core.PlayerClassHolder;
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

        if (playerClass instanceof Gambler gambler) {
            gambler.beginAttack(Target);
        }
    }

    @Inject(method = "attack", at = @At("RETURN"))
    private void classes$afterAttack(Entity Target, CallbackInfo callbackInfo) {
        Player Self = (Player)(Object)this;

        if (!(Self instanceof ServerPlayer Player)) {
            return;
        }

        PlayerClass playerClass = ((PlayerClassHolder)Player).getPlayerClass();

        if (playerClass instanceof Gambler gambler) {
            gambler.endAttack();
        }
    }
}