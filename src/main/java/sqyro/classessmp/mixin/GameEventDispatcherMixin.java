package sqyro.classessmp.mixin;

import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventDispatcher;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sqyro.classessmp.core.PlayerClassHolder;
import sqyro.classessmp.playerclasses.AncientWarden;

@Mixin(GameEventDispatcher.class)
public class GameEventDispatcherMixin {
    @Inject(method = "post", at = @At("HEAD"))
    private void classesSMP$detectNoise(Holder<GameEvent> gameEvent, Vec3 Pos, GameEvent.Context context, CallbackInfo callbackInfo) {
        GameEventDispatcher dispatcher = (GameEventDispatcher)(Object)this;
        ServerLevel Level = ((GameEventDispatcherAccessor) dispatcher).getLevel();

        for (ServerPlayer Player : Level.players()) {
            if (!(Player instanceof PlayerClassHolder holder)) {
                continue;
            }

            if (!(holder.getPlayerClass() instanceof AncientWarden warden)) {
                continue;
            }

            double Distance = Player.position().distanceTo(Pos);

            if (Distance > AncientWarden.NOISE_DETECTION_RADIUS) {
                continue;
            }

            int Noise;

            if (gameEvent.is(GameEvent.BLOCK_DESTROY) || gameEvent.is(GameEvent.BLOCK_PLACE) || gameEvent.is(GameEvent.JUKEBOX_PLAY)
                    || gameEvent.is(GameEvent.NOTE_BLOCK_PLAY) || gameEvent.is(GameEvent.BLOCK_OPEN) || gameEvent.is(GameEvent.BLOCK_CLOSE)
                    || gameEvent.is(GameEvent.BLOCK_ACTIVATE) || gameEvent.is(GameEvent.BLOCK_DEACTIVATE)) {
                Noise = 1;
            } else if (gameEvent.is(GameEvent.EXPLODE) || gameEvent.is(GameEvent.LIGHTNING_STRIKE) || gameEvent.is(GameEvent.SHRIEK)) {
                Noise = 5;
            } else {
                Noise = 0;
            }

            if (Noise * 0.3f * ((AncientWarden.NOISE_DETECTION_RADIUS / Distance) - 1) > 0.6f) {
                warden.hearNoise(Noise);
            }
        }
    }
}