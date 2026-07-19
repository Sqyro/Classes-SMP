package sqyro.classessmp.items;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.minecart.MinecartTNT;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import sqyro.classessmp.core.PlayerClassHolder;

public class C4Item extends ClassRestrictedItem {
    public static final float NOT_TERRORIST_EXPLOSION_STRENGTH = 4f;
    public static final int C4_COOLDOWN = 100;

    public C4Item(Properties properties) {
        super(properties);
    }

    @Override
    protected String getRequiredClass() {
        return "terrorist";
    }

    @Override
    public InteractionResult use(Level Level, Player Player, InteractionHand Hand) {
        ItemStack Stack = Player.getItemInHand(Hand);

        if (!Level.isClientSide()) {
            if (!(Player instanceof PlayerClassHolder Holder) || Holder.getPlayerClass() == null || !Holder.getPlayerClass().getID().equals(getRequiredClass())) {
                Level.explode(null, Player.getX(), Player.getY(), Player.getZ(), NOT_TERRORIST_EXPLOSION_STRENGTH, net.minecraft.world.level.Level.ExplosionInteraction.TNT);

                if (!Player.getAbilities().instabuild) {
                    Stack.shrink(1);
                }

                return InteractionResult.SUCCESS;
            }

            if (Player.getCooldowns().isOnCooldown(Stack)) {
                return InteractionResult.FAIL;
            }

            Vec3 Look = Player.getLookAngle();

            double x = Player.getX() + Look.x * 2;
            double y = Player.getY();
            double z = Player.getZ() + Look.z * 2;

            MinecartTNT minecart = EntityType.TNT_MINECART.create(Level, EntitySpawnReason.SPAWN_ITEM_USE);

            if (minecart != null) {
                minecart.setPos(x, y, z);
                Level.addFreshEntity(minecart);

                Player.getCooldowns().addCooldown(Stack, C4_COOLDOWN);
            }
        }

        return InteractionResult.SUCCESS;
    }
}