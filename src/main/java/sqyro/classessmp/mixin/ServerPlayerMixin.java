package sqyro.classessmp.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sqyro.classessmp.ClassesSMP;
import sqyro.classessmp.core.PlayerClass;
import sqyro.classessmp.core.PlayerClassHolder;
import sqyro.classessmp.playerclasses.Gambler;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin {
    @Inject(method = "die", at = @At("HEAD"))
    private void classes$death(DamageSource source, CallbackInfo ci) {
        ServerPlayer Player = (ServerPlayer)(Object)this;

        PlayerClass playerClass = ((PlayerClassHolder)Player).getPlayerClass();

        if (playerClass instanceof Gambler gambler) {
            if (gambler.getBonusLevel() > gambler.MIN_BONUS_DAMAGE_LEVEL) {
                gambler.setBonusLevel(gambler.getBonusLevel() - 1);
                ClassesSMP.LOGGER.info("{} of class {} leveled down bonus damage (Level {} -> {})", Player.getName().getString(), gambler.getID(), gambler.getBonusLevel() + 1, gambler.getBonusLevel());
            }
        }
    }
}