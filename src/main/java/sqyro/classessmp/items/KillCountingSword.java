package sqyro.classessmp.items;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface KillCountingSword {
    boolean canUse(ServerPlayer Player, ItemStack Stack);
    void onKill(ServerPlayer Player, LivingEntity Target, ItemStack Weapon);
}