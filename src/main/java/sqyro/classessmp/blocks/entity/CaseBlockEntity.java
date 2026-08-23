package sqyro.classessmp.blocks.entity;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;
import sqyro.classessmp.blocks.CaseLootTable;
import sqyro.classessmp.client.GUI.CaseMenu;
import sqyro.classessmp.items.ClassesItems;
import sqyro.classessmp.network.cases.CaseNetworking;

public class CaseBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPos> {
    public CaseBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ClassesBlockEntities.CASE_BLOCK_ENTITY, blockPos, blockState);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Case");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
        return new CaseMenu(id, inventory,this);
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayer player) {
        return getBlockPos();
    }

    public boolean tryOpenCase(ServerPlayer player) {
        if (level == null || level.isClientSide()) {
            return false;
        }

        if (isRemoved()) {
            return false;
        }

        if (player.distanceToSqr(getBlockPos().getX() + 0.5, getBlockPos().getY() + 0.5, getBlockPos().getZ() + 0.5) > 64.0) {
            return false;
        }

        if (!(player.containerMenu instanceof CaseMenu menu)) {
            return false;
        }

        if (menu.getBlockEntity() != this) {
            return false;
        }

        ItemStack key = findKey(player);

        if (key.isEmpty()) {
            player.displayClientMessage(Component.literal("You need a key!").withStyle(ChatFormatting.RED), true);

            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.VILLAGER_NO, SoundSource.BLOCKS);

            return false;
        }

        key.shrink(1);
        player.getInventory().setChanged();

        ItemStack reward = CaseLootTable.roll(level.getRandom());

        giveReward(player, reward);

        level.removeBlock(getBlockPos(), false);

        player.closeContainer();

        level.playSound(null, getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), SoundEvents.CHEST_OPEN, SoundSource.BLOCKS);

        CaseNetworking.sendRollResult(player, reward);

        return true;
    }

    private ItemStack findKey(Player player) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.is(ClassesItems.CASE_KEY)) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    private void giveReward(ServerPlayer player, ItemStack reward) {
        ItemStack remaining = reward.copy();

        player.getInventory().add(remaining);

        if (!remaining.isEmpty()) {
            player.drop(remaining, false);
        }
    }
}
