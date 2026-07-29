package sqyro.classessmp.playerclasses;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import sqyro.classessmp.core.PlayerClass;
import sqyro.classessmp.items.ClassesItems;

public class Builder extends PlayerClass {
    public Builder(ServerPlayer Player) {
        super(Player);
    }

    @Override
    public String getID() {
        return "builder";
    }

    @Override
    public void onTick() {
        boolean hasFeatherArmor = Player.getItemBySlot(EquipmentSlot.HEAD).is(ClassesItems.FEATHER_HELMET) && Player.getItemBySlot(EquipmentSlot.CHEST).is(ClassesItems.FEATHER_CHESTPLATE) && Player.getItemBySlot(EquipmentSlot.LEGS).is(ClassesItems.FEATHER_LEGGINGS) && Player.getItemBySlot(EquipmentSlot.FEET).is(ClassesItems.FEATHER_BOOTS);

        if (hasFeatherArmor) {
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
    }

    @Override
    public void onRespawn() {

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
