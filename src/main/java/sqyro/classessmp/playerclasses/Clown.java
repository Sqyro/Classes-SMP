package sqyro.classessmp.playerclasses;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import sqyro.classessmp.ClassesSMP;
import sqyro.classessmp.core.PlayerClass;
import sqyro.classessmp.effect.ClassesEffects;
import sqyro.classessmp.entities.CardProjectileEntity;
import sqyro.classessmp.entities.ClassesEntities;
import sqyro.classessmp.items.ClownSwordItem;

import java.util.List;
import java.util.Optional;

public class Clown extends PlayerClass {
    private static final Identifier DAMAGE_MODIFIER_ID = Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "clown_damage");
    private static final Identifier MOVEMENT_SPEED_ID = Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "clown_movement_speed");

    private static final String CIRCUS_CAGE_ID = "circus_cage";
    public static final int CIRCUS_CAGE_COOLDOWN = 600;
    public static final int CIRCUS_CAGE_RANGE = 20;
    public static final int CIRCUS_CAGE_ROOTING_DURATION = 100;

    private static final String TRI_CARD_ATTACK_ID = "tri_card_attack";
    public static final int TRI_CARD_ATTACK_COOLDOWN = 500;

    private static final String CIRCUS_FINALE_ID = "circus_finale";
    public static final int CIRCUS_FINALE_COOLDOWN = 4800;
    public static final int CIRCUS_FINALE_DURATION = 2400;

    public Clown(ServerPlayer Player) {
        super(Player);
    }

    @Override
    public String getID() {
        return "clown";
    }

    @Override
    public void onTick() {
        ItemStack itemInHand = Player.getItemInHand(InteractionHand.MAIN_HAND);
        AttributeInstance movementSpeed = Player.getAttribute(Attributes.MOVEMENT_SPEED);

        if (movementSpeed == null) {
            return;
        }

        movementSpeed.removeModifier(MOVEMENT_SPEED_ID);

        if (itemInHand == ItemStack.EMPTY) {
            return;
        }

        if (!(itemInHand.getItem() instanceof ClownSwordItem)) {
            return;
        }

        movementSpeed.addTransientModifier(getMovementSpeedModifier(itemInHand));
    }

    @Override
    public void onRespawn() {

    }

    @Override
    public void onKeybind1() {
        if (isOnCooldown(CIRCUS_CAGE_ID)) {
            ClassesSMP.LOGGER.info("{} of class: {} tried to activate Circus Cage, but it was on cooldown: {}", Player.getName().getString(), this.getID(), this.getCooldownTicks(CIRCUS_CAGE_ID));
            return;
        }

        ClassesSMP.LOGGER.info("{} of class {} activated Circus Cage", Player.getName().getString(), this.getID());
        setCooldown(CIRCUS_CAGE_ID, CIRCUS_CAGE_COOLDOWN);

        ServerLevel level = Player.level();

        Vec3 Start = Player.getEyePosition();
        Vec3 Direction = Player.getLookAngle();

        Vec3 MaxEnd = Start.add(Direction.scale(CIRCUS_CAGE_RANGE));
        BlockHitResult blockHit = level.clip(new ClipContext(Start, MaxEnd, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, Player));
        Vec3 End = blockHit.getType() == HitResult.Type.BLOCK ? blockHit.getLocation() : MaxEnd;

        LivingEntity hitEntity = getEntityHit(level, Player, Start, End);

        if (hitEntity != null) {
            Vec3 hitPos = hitEntity.position().add(0, hitEntity.getBbHeight() / 2, 0);
            End = hitPos;
            hitEntity.addEffect(new MobEffectInstance(ClassesEffects.ROOTING, CIRCUS_CAGE_ROOTING_DURATION, 0));
            ClassesSMP.LOGGER.info("{} hit {} with Circus Cage", Player.getName().getString(), hitEntity.getName().getString());
        }

        double Length = Start.distanceTo(End);

        for (int i = 0; i < Length; i++) {
            double Position = i / Length;
            Vec3 particlePos = Start.lerp(End, Position);
            level.sendParticles(ParticleTypes.WAX_ON, particlePos.x, particlePos.y, particlePos.z, 16, 0.12, 0.12, 0.12, 0.01);
        }
    }

    @Override
    public void onKeybind2() {
        if (isOnCooldown(TRI_CARD_ATTACK_ID)) {
            ClassesSMP.LOGGER.info("{} of class: {} tried to activate Tri Card Attack, but it was on cooldown: {}", Player.getName().getString(), this.getID(), this.getCooldownTicks(TRI_CARD_ATTACK_ID));
            return;
        }

        ClassesSMP.LOGGER.info("{} of class {} activated Tri Card Attack", Player.getName().getString(), this.getID());
        setCooldown(TRI_CARD_ATTACK_ID, TRI_CARD_ATTACK_COOLDOWN);

        Vec3 direction = Player.getLookAngle();
        Vec3 spawnPosition = new Vec3(
                Player.getX(),
                Player.getEyeY(),
                Player.getZ()
        );

        double angle = Math.toRadians(15.0);

        Vec3 leftDirection = direction.yRot((float) angle);
        Vec3 centerDirection = direction;
        Vec3 rightDirection = direction.yRot((float) -angle);

        shootCard(spawnPosition, leftDirection);
        shootCard(spawnPosition, centerDirection);
        shootCard(spawnPosition, rightDirection);
    }

    private void shootCard(Vec3 position, Vec3 direction) {
        CardProjectileEntity card = new CardProjectileEntity(
                ClassesEntities.CARD_PROJECTILE,
                Player.level()
        );

        card.setOwner(Player);
        card.setPos(position);
        card.shoot(direction);

        Player.level().addFreshEntity(card);
    }

    @Override
    public void onKeybind3() {
        if (isOnCooldown(CIRCUS_FINALE_ID)) {
            ClassesSMP.LOGGER.info("{} of class: {} tried to activate Circus Finale, but it was on cooldown: {}", Player.getName().getString(), this.getID(), this.getCooldownTicks(CIRCUS_FINALE_ID));
            return;
        }

        ClassesSMP.LOGGER.info("{} of class {} activated Circus Finale", Player.getName().getString(), this.getID());
        setCooldown(CIRCUS_FINALE_ID, CIRCUS_FINALE_COOLDOWN);

        Player.addEffect(new MobEffectInstance(MobEffects.STRENGTH, CIRCUS_FINALE_DURATION, 2));
        Player.addEffect(new MobEffectInstance(MobEffects.HASTE, CIRCUS_FINALE_DURATION, 1));
    }

    @Override
    public void beginAttack(Entity Target) {
        ItemStack itemInHand = Player.getItemInHand(InteractionHand.MAIN_HAND);
        AttributeInstance attackDamage = Player.getAttribute(Attributes.ATTACK_DAMAGE);

        if (itemInHand == ItemStack.EMPTY) {
            return;
        }

        if (attackDamage == null) {
            return;
        }

        attackDamage.removeModifier(DAMAGE_MODIFIER_ID);

        if (!(itemInHand.getItem() instanceof ClownSwordItem)) {
            return;
        }

        attackDamage.addTransientModifier(getDamageModifier(itemInHand));
    }

    @Override
    public void endAttack() {
        AttributeInstance attackDamage = Player.getAttribute(Attributes.ATTACK_DAMAGE);

        if (attackDamage != null) {
            attackDamage.removeModifier(DAMAGE_MODIFIER_ID);
        }
    }

    private AttributeModifier getDamageModifier(ItemStack itemInHand) {
        int BonusDamage = ClownSwordItem.getData(itemInHand).getKillCount() / 2;
        return new AttributeModifier(DAMAGE_MODIFIER_ID, BonusDamage, AttributeModifier.Operation.ADD_VALUE);
    }

    private AttributeModifier getMovementSpeedModifier(ItemStack itemInHand) {
        int BonusMovementSpeed = (ClownSwordItem.getData(itemInHand).getKillCount() + 1) / 2;
        return new AttributeModifier(MOVEMENT_SPEED_ID, (float)BonusMovementSpeed/100, AttributeModifier.Operation.ADD_VALUE);
    }

    private LivingEntity getEntityHit(ServerLevel Level, Player player, Vec3 StartPos, Vec3 EndPos) {
        AABB hitBox = player.getBoundingBox().expandTowards(EndPos.subtract(StartPos)).inflate(1.0);

        List<Entity> Entities = Level.getEntities(player, hitBox, Entity -> Entity.isPickable() && Entity instanceof LivingEntity);

        LivingEntity closestEntity = null;
        double closestDistance = Double.MAX_VALUE;

        for (Entity thisEntity : Entities) {
            AABB entityHitBox = thisEntity.getBoundingBox().inflate(0.3);
            Optional<Vec3> hitPos = entityHitBox.clip(StartPos, EndPos);

            if (hitPos.isPresent()) {
                double Distance = StartPos.distanceTo(hitPos.get());

                if (Distance < closestDistance) {
                    closestDistance = Distance;
                    closestEntity = (LivingEntity) thisEntity;
                }
            }
        }

        return closestEntity;
    }
}