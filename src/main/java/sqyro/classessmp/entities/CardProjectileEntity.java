package sqyro.classessmp.entities;

import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;

public class CardProjectileEntity extends Projectile {
    private static final double SPEED = 0.6D;
    private static final double MAX_RANGE = 64.0D;
    private static final float DAMAGE = 10.0F;

    private Vec3 startPosition;

    public CardProjectileEntity(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    @Override
    protected void readAdditionalSaveData(net.minecraft.world.level.storage.ValueInput input) {
    }

    @Override
    protected void addAdditionalSaveData(net.minecraft.world.level.storage.ValueOutput output) {
    }

    public void shoot(Vec3 direction) {
        this.setDeltaMovement(direction.normalize().scale(SPEED));

        if (this.startPosition == null) {
            this.startPosition = this.position();
        }
    }

    @Override
    protected boolean canHitEntity(Entity entity) {
        return super.canHitEntity(entity) && !(entity instanceof CardProjectileEntity);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.startPosition == null) {
            this.startPosition = this.position();
        }

        Vec3 movement = this.getDeltaMovement();

        if (movement.lengthSqr() == 0.0D) {
            return;
        }

        Vec3 start = this.position();
        Vec3 end = start.add(movement);

        if (!this.level().isClientSide()) {

            BlockHitResult blockHit = this.level().clip(new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
            EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(this, start, end, this.getBoundingBox().expandTowards(movement), this::canHitEntity, 0.0F);

            HitResult hitResult = null;

            if (blockHit.getType() != HitResult.Type.MISS) {
                hitResult = blockHit;
            }

            if (entityHit != null) {
                if (hitResult == null || entityHit.getLocation().distanceToSqr(start) < hitResult.getLocation().distanceToSqr(start)) {
                    hitResult = entityHit;
                }
            }

            if (hitResult != null) {

                if (hitResult.getType() == HitResult.Type.ENTITY) {
                    EntityHitResult entityHitResult = (EntityHitResult) hitResult;

                    entityHitResult.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), DAMAGE);
                }

                this.discard();
                return;
            }

            if (this.position().distanceToSqr(this.startPosition) >= MAX_RANGE * MAX_RANGE) {
                this.discard();
                return;
            }
        }

        this.setPos(end);
    }

    @Override
    public boolean isPickable() {
        return true;
    }
}