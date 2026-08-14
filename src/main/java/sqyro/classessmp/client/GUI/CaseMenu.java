package sqyro.classessmp.client.GUI;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import sqyro.classessmp.blocks.entity.CaseBlockEntity;

public class CaseMenu extends AbstractContainerMenu {
    private final CaseBlockEntity blockEntity;

    public CaseMenu(int id, Inventory playerInventory, BlockPos blockPos) {
        this(id, playerInventory, getCaseBlockEntity(playerInventory, blockPos));
    }

    public CaseMenu(int id, Inventory playerInventory, CaseBlockEntity blockEntity) {
        super(ClassesMenuTypes.CASE_MENU, id);

        this.blockEntity = blockEntity;

        addPlayerInventorySlots(playerInventory, 8, 84);
    }

    private static CaseBlockEntity getCaseBlockEntity(Inventory inventory, BlockPos pos) {
        if (inventory.player.level().getBlockEntity(pos) instanceof CaseBlockEntity caseBlockEntity) {
            return caseBlockEntity;
        }

        return null;
    }

    public CaseBlockEntity getBlockEntity() {
        return blockEntity;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        if (blockEntity == null || blockEntity.isRemoved()) {
            return false;
        }

        return player.distanceToSqr(blockEntity.getBlockPos().getX() + 0.5, blockEntity.getBlockPos().getY() + 0.5, blockEntity.getBlockPos().getZ() + 0.5) <= 64.0;
    }

    private void addPlayerInventorySlots(Inventory playerInventory, int startX, int startY) {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                addSlot(new Slot(playerInventory, column + row * 9 + 9, startX + column * 18, startY + row * 18));
            }
        }

        for (int column = 0; column < 9; column++) {
            addSlot(new Slot(playerInventory, column, startX + column * 18, startY + 58));
        }
    }
}
