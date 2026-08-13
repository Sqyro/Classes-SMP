package sqyro.classessmp.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import sqyro.classessmp.ClassesSMP;
import sqyro.classessmp.core.PlayerClassHolder;
import sqyro.classessmp.core.SavedData.PlayerClassSavedData;
import sqyro.classessmp.core.SavedData.PlayerClassSavedDataGetter;
import sqyro.classessmp.network.ClassesNetworking;

public class RemoveClassItem extends Item {
    public RemoveClassItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand interactionHand) {
        if (player instanceof PlayerClassHolder holder) {
            if (holder.getPlayerClass() != null) {
                PlayerClassSavedData savedData = PlayerClassSavedDataGetter.get((ServerLevel) level);

                String previousClassID = holder.getSavedClassID();

                holder.setPlayerClass(null);
                holder.setSavedClassID("none");

                savedData.removeClass(player);
                if (player instanceof ServerPlayer serverPlayer) {
                    ClassesNetworking.sendClassSync(serverPlayer);
                }
                level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDER_DRAGON_GROWL, SoundSource.PLAYERS);
                player.displayClientMessage(Component.literal("Successfully removed Class").withStyle(ChatFormatting.GREEN), true);
                ClassesSMP.LOGGER.info(player.getPlainTextName() + " removed their class " + previousClassID + " using the Remove Class Item");

                if (!player.getAbilities().instabuild) {
                    player.getItemInHand(interactionHand).shrink(1);
                }

                return InteractionResult.SUCCESS;
            } else {
                player.displayClientMessage(Component.literal("No Class selected!").withStyle(ChatFormatting.RED), true);
                return InteractionResult.FAIL;
            }
        } else {
            return InteractionResult.FAIL;
        }
    }
}
