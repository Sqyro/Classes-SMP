package sqyro.classessmp.playerclasses;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;
import sqyro.classessmp.ClassesSMP;
import sqyro.classessmp.core.PlayerClass;
import sqyro.classessmp.items.ClassesItems;

public class Builder extends PlayerClass {
    public static final Identifier BLOCK_BREAK_SPEED_MODIFIER_ID = Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "builder_block_break_speed");
    public static final float BLOCK_BREAK_SPEED_BONUS = 0.7f;
    public static final Identifier BLOCK_INTERACTION_RANGE_MODIFIER_ID = Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "builder_block_interaction_range");
    public static final int BLOCK_INTERACTION_RANGE_BONUS = 4;

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
        AttributeInstance blockBreakSpeed = Player.getAttribute(Attributes.BLOCK_BREAK_SPEED);
        AttributeInstance blockInteractionRange = Player.getAttribute(Attributes.BLOCK_INTERACTION_RANGE);

        if (blockInteractionRange != null && blockInteractionRange.getModifier(BLOCK_INTERACTION_RANGE_MODIFIER_ID) == null) {
            blockInteractionRange.addPermanentModifier(new AttributeModifier(BLOCK_INTERACTION_RANGE_MODIFIER_ID, BLOCK_INTERACTION_RANGE_BONUS, AttributeModifier.Operation.ADD_VALUE));
        }

        if (blockBreakSpeed != null && blockBreakSpeed.getModifier(BLOCK_BREAK_SPEED_MODIFIER_ID) == null) {
            blockBreakSpeed.addPermanentModifier(new AttributeModifier(BLOCK_BREAK_SPEED_MODIFIER_ID, BLOCK_BREAK_SPEED_BONUS, AttributeModifier.Operation.ADD_VALUE));
        }

        boolean hasFeatherArmor = Player.getItemBySlot(EquipmentSlot.HEAD).is(ClassesItems.FEATHER_HELMET) && Player.getItemBySlot(EquipmentSlot.CHEST).is(ClassesItems.FEATHER_CHESTPLATE) && Player.getItemBySlot(EquipmentSlot.LEGS).is(ClassesItems.FEATHER_LEGGINGS) && Player.getItemBySlot(EquipmentSlot.FEET).is(ClassesItems.FEATHER_BOOTS);

        if (hasFeatherArmor) {
            Player.resetFallDistance();
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