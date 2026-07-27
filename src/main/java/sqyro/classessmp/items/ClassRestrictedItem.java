package sqyro.classessmp.items;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import sqyro.classessmp.core.PlayerClass;
import sqyro.classessmp.core.PlayerClassHolder;
import sqyro.classessmp.core.SavedData.PlayerClassSavedDataGetter;

public abstract class ClassRestrictedItem extends Item {
    public ClassRestrictedItem(Properties properties) {
        super(properties);
    }

    protected abstract String getRequiredClass();

    protected boolean hasRequiredClass(ServerPlayer Player) {
        String playerClass = PlayerClassSavedDataGetter.get(Player.level()).getClass(Player.getUUID());
        return getRequiredClass().equals(playerClass);
    }
}