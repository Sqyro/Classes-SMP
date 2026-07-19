package sqyro.classessmp.items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;

public class C4DispenseBehaviour extends DefaultDispenseItemBehavior {
    @Override
    protected ItemStack execute(BlockSource Source, ItemStack Stack) {
        ServerLevel Level = Source.level();

        BlockPos Pos = Source.pos();
        Direction Direction = Source.state().getValue(DispenserBlock.FACING);

        double x = Pos.getX() + 0.5 + Direction.getStepX();
        double y = Pos.getY() + 0.5 + Direction.getStepY();
        double z = Pos.getZ() + 0.5 + Direction.getStepZ();

        Stack.shrink(1);

        Level.explode(null, x, y, z, C4Item.NOT_TERRORIST_EXPLOSION_STRENGTH, net.minecraft.world.level.Level.ExplosionInteraction.TNT);

        return Stack;
    }
}
