package sqyro.classessmp.items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jspecify.annotations.Nullable;
import sqyro.classessmp.ClassesSMP;
import sqyro.classessmp.core.PlayerClassHolder;

import java.util.function.Supplier;

public class SpawnerItem extends ClassRestrictedItem {
    public static final int SPAWNER_ITEM_COOLDOWN = 800;
    public static final int SPAWNER_ITEM_OFFSET_RANGE = 3;

    private final String requiredClassID;
    private final EntityType SpawnedEntity;

    public SpawnerItem(Properties properties, String requiredClassID, EntityType SpawnedEntity) {
        super(properties);
        this.requiredClassID = requiredClassID;
        this.SpawnedEntity = SpawnedEntity;
    }

    @Override
    protected String getRequiredClass() {
        return requiredClassID;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand interactionHand) {
        if (level instanceof ServerLevel) {
            if (!(player instanceof PlayerClassHolder Holder) || Holder.getPlayerClass() == null || !Holder.getPlayerClass().getID().equals(getRequiredClass())) {
                return InteractionResult.FAIL;
            }

            Supplier<Integer> RandomOffset = () -> player.getRandom().nextInt(-SPAWNER_ITEM_OFFSET_RANGE, SPAWNER_ITEM_OFFSET_RANGE);
            BlockPos spawnPos = new BlockPos((int) player.getX() + RandomOffset.get(), (int) player.getY(), (int) player.getZ() + RandomOffset.get());

            while (!level.getBlockState(spawnPos).isAir()) {
                spawnPos = spawnPos.above();
            }
            while (!level.getBlockState(spawnPos.below()).canOcclude()) {
                spawnPos = spawnPos.below();
            }

            ItemStack thisItemStack = player.getItemInHand(interactionHand);
            player.getCooldowns().addCooldown(thisItemStack, SPAWNER_ITEM_COOLDOWN);

            return this.spawnMob(player, thisItemStack, level, spawnPos);
        } else {
            return InteractionResult.SUCCESS;
        }
    }

    private InteractionResult spawnMob(@Nullable LivingEntity useEntity, ItemStack itemStack, Level level, BlockPos blockPos) {
        EntityType<?> entityType = this.SpawnedEntity;
        if (entityType == null) {
            return InteractionResult.FAIL;
        }

        Entity spawnedEntity = entityType.spawn((ServerLevel)level, itemStack, useEntity, blockPos, EntitySpawnReason.SPAWN_ITEM_USE, false, false);

        if (spawnedEntity != null) {
            level.gameEvent(useEntity, GameEvent.ENTITY_PLACE, blockPos);
        }

        if (spawnedEntity instanceof TamableAnimal tamableAnimal && useEntity instanceof Player player) {
            tamableAnimal.tame(player);
            ClassesSMP.LOGGER.info("Spawned Tamable Animal");
        }

        level.playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundEvents.BRUSH_SAND_COMPLETED, SoundSource.PLAYERS);
        ((ServerLevel) level).sendParticles(ParticleTypes.SMOKE, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 20, 0.4, 0.6, 0.4, 0.05);

        return InteractionResult.SUCCESS;
    }
}