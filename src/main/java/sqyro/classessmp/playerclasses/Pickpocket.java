package sqyro.classessmp.playerclasses;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.*;
import sqyro.classessmp.ClassesSMP;
import sqyro.classessmp.core.PlayerClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Pickpocket extends PlayerClass {
    public static final String STEAL_ID = "steal";
    public static final int STEAL_COOLDOWN = 1200;

    public Pickpocket(ServerPlayer Player) {
        super(Player);
    }

    @Override
    public String getID() {
        return "pickpocket";
    }

    @Override
    public void onTick() {

    }

    @Override
    public void onRespawn() {

    }

    @Override
    public void onKeybind1() {
        if (isOnCooldown(STEAL_ID)) {
            ClassesSMP.LOGGER.info("{} of class: {} tried to activate Steal, but it was on cooldown: {}", Player.getName().getString(), this.getID(), this.getCooldownTicks(STEAL_ID));
            return;
        }

        Vec3 Start = Player.getEyePosition();
        Vec3 Direction = Player.getLookAngle();

        Vec3 MaxEnd = Start.add(Direction.scale(3));
        BlockHitResult blockHit = Player.level().clip(new ClipContext(Start, MaxEnd, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, Player));
        Vec3 End = blockHit.getType() == HitResult.Type.BLOCK ? blockHit.getLocation() : MaxEnd;

        LivingEntity hitEntity = getEntityHit(Player.level(), Player, Start, End);

        if (!(hitEntity instanceof ServerPlayer Target)) {
            return;
        }

        List<Integer> validSlots = new ArrayList<>();

        for (int i = 0; i < Target.getInventory().getNonEquipmentItems().size(); i++) {
            if (!Target.getInventory().getItem(i).isEmpty()) {
                validSlots.add(i);
            }
        }

        if (validSlots.isEmpty()) {
            return;
        }

        ClassesSMP.LOGGER.info("{} of class {} activated Steal", Player.getName().getString(), this.getID());
        setCooldown(STEAL_ID, STEAL_COOLDOWN);

        int Slot = validSlots.get(Player.getRandom().nextInt(validSlots.size()));

        ItemStack Stack = Target.getInventory().getItem(Slot);
        ItemStack Stolen = Stack.split(1);

        if (Stack.isEmpty()) {
            Target.getInventory().setItem(Slot, ItemStack.EMPTY);
        }

        Player.getInventory().placeItemBackInInventory(Stolen);

        Target.containerMenu.broadcastChanges();
        Player.containerMenu.broadcastChanges();
    }

    @Override
    public void onKeybind2() {

    }

    @Override
    public void onKeybind3() {

    }

    @Override
    public void beginAttack(Entity Target) {

    }

    @Override
    public void endAttack() {

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
