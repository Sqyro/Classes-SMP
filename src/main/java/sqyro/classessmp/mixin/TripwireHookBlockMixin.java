package sqyro.classessmp.mixin;

import com.google.common.base.MoreObjects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TripWireHookBlock.class)
public abstract class TripwireHookBlockMixin extends Block {

    public TripwireHookBlockMixin(Properties properties) {
        super(properties);
    }

    @Shadow
    private static void emitState(Level level, BlockPos pos, boolean attached, boolean powered, boolean oldAttached, boolean oldPowered) {
    }

    @Shadow
    private static void notifyNeighbors(Block block, Level level, BlockPos pos, Direction direction) {
    }

    /**
     * @author Sqyro
     * @reason Fixing the tripwire hook duplication bug in 1.21.11 Mojank mappings
     */
    @Overwrite
    public static void calculateState(Level level, BlockPos pos, BlockState state, boolean beingRemoved, boolean updateNeighbors, int changedDistance, @Nullable BlockState changedState) {
        Direction direction = state.getValue(TripWireHookBlock.FACING);

        boolean attached = state.getValue(TripWireHookBlock.ATTACHED);
        boolean powered = state.getValue(TripWireHookBlock.POWERED);

        Block block = state.getBlock();

        boolean valid = !beingRemoved;
        boolean poweredWire = false;

        int distance = 0;

        BlockState[] states = new BlockState[42];

        for (int i = 1; i < 42; i++) {
            BlockPos checkPos = pos.relative(direction, i);
            BlockState checkState = level.getBlockState(checkPos);

            if (checkState.is(Blocks.TRIPWIRE_HOOK)) {
                if (checkState.getValue(TripWireHookBlock.FACING) == direction.getOpposite()) {
                    distance = i;
                }
                break;
            }

            if (!checkState.is(Blocks.TRIPWIRE) && i != changedDistance) {
                states[i] = null;
                valid = false;
            } else {
                if (i == changedDistance) {
                    checkState = MoreObjects.firstNonNull(changedState, checkState);
                }

                boolean armed = !checkState.getValue(TripWireBlock.DISARMED);
                poweredWire |= armed && checkState.getValue(TripWireBlock.POWERED);
                states[i] = checkState;

                if (i == changedDistance) {
                    level.scheduleTick(pos, block, 10);
                    valid &= armed;
                }
            }
        }

        valid &= distance > 1;
        poweredWire &= valid;

        BlockState newState = block.defaultBlockState().setValue(TripWireHookBlock.ATTACHED, valid).setValue(TripWireHookBlock.POWERED, poweredWire);

        if (distance > 0) {
            BlockPos otherHook = pos.relative(direction, distance);
            Direction opposite = direction.getOpposite();

            level.setBlock(otherHook, newState.setValue(TripWireHookBlock.FACING, opposite), 3);
            notifyNeighbors(block, level, otherHook, opposite);

            emitState(level, otherHook, valid, poweredWire, attached, powered);
        }

        emitState(level, pos, valid, poweredWire, attached, powered);

        if (!beingRemoved && level.getBlockState(pos).is(Blocks.TRIPWIRE_HOOK)) {
            level.setBlock(pos, newState.setValue(TripWireHookBlock.FACING, direction), 3);

            if (updateNeighbors) {
                notifyNeighbors(block, level, pos, direction);
            }
        }

        if (attached != valid) {
            for (int i = 1; i < distance; i++) {
                BlockPos wirePos = pos.relative(direction, i);
                BlockState oldState = states[i];

                if (oldState != null) {
                    BlockState current = level.getBlockState(wirePos);

                    if (current.is(Blocks.TRIPWIRE) || current.is(Blocks.TRIPWIRE_HOOK)) {
                        level.setBlock(wirePos, oldState.setValue(TripWireHookBlock.ATTACHED, valid), 3);
                    }
                }
            }
        }
    }
}