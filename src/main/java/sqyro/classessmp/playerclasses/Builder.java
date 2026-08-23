package sqyro.classessmp.playerclasses;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.phys.Vec3;
import sqyro.classessmp.core.PlayerClass;
import sqyro.classessmp.items.ClassesItems;

public class Builder extends PlayerClass {
    private static final float MIN_FLYING_SPEED_FOR_ENERGY_CONSUMPTION = 0.1f;
    private final int MAX_FLYING_ENERGY = 200;
    private int flyingEnergy;

    private Vec3 lastPosition;

    public static final float BUILDER_FLYING_SPEED = 0.05f;
    public static final float DEFAULT_FLYING_SPEED = 0.05f;

    public Builder(ServerPlayer Player) {
        super(Player);
        this.lastPosition = Player.position();
    }

    @Override
    public String getID() {
        return "builder";
    }

    @Override
    public void onTick() {
        boolean hasFeatherArmor = Player.getItemBySlot(EquipmentSlot.HEAD).is(ClassesItems.FEATHER_HELMET) && Player.getItemBySlot(EquipmentSlot.CHEST).is(ClassesItems.FEATHER_CHESTPLATE) && Player.getItemBySlot(EquipmentSlot.LEGS).is(ClassesItems.FEATHER_LEGGINGS) && Player.getItemBySlot(EquipmentSlot.FEET).is(ClassesItems.FEATHER_BOOTS);

        if (hasFeatherArmor) {
            Player.resetFallDistance();
            Player.addEffect(new MobEffectInstance(MobEffects.HASTE, 20, 2, false, false));
            Player.getAbilities().setFlyingSpeed(BUILDER_FLYING_SPEED);
            if (flyingEnergy > 0) {
                if (!Player.getAbilities().mayfly) {
                    Player.getAbilities().mayfly = true;
                    Player.onUpdateAbilities();
                }
            } else {
                if (Player.getAbilities().mayfly && !Player.isCreative() && !Player.isSpectator()) {
                    Player.getAbilities().mayfly = false;
                    Player.getAbilities().flying = false;
                    Player.onUpdateAbilities();
                }
            }
            Player.displayClientMessage(Component.literal("Flying Energy " + flyingEnergy).withStyle(ChatFormatting.GREEN), true);
        } else {
            Player.getAbilities().setFlyingSpeed(DEFAULT_FLYING_SPEED);
            if (Player.getAbilities().mayfly && !Player.isCreative() && !Player.isSpectator()) {
                Player.getAbilities().mayfly = false;
                Player.getAbilities().flying = false;
                Player.onUpdateAbilities();
            }
        }

        Vec3 currentPosition = Player.position();

        if (Player.getAbilities().flying && !Player.isCreative() && !Player.isSpectator()) {
            if (currentPosition.subtract(lastPosition).length() > MIN_FLYING_SPEED_FOR_ENERGY_CONSUMPTION && flyingEnergy > 0) {
                flyingEnergy--;
            }
        } else {
            if (flyingEnergy < MAX_FLYING_ENERGY && Player.onGround()) {
                flyingEnergy++;
            }
        }

        lastPosition = currentPosition;
    }

    @Override
    public void onRespawn() {

    }

    @Override
    public void onKill(Entity Target) {

    }

    @Override
    public void onKeybind1() {

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
}