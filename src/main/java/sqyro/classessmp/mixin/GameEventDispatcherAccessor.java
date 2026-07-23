package sqyro.classessmp.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.gameevent.GameEventDispatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GameEventDispatcher.class)
public interface GameEventDispatcherAccessor {
    @Accessor("level")
    ServerLevel getLevel();
}
