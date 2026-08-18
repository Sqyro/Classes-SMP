package sqyro.classessmp.playerclasses;

import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import sqyro.classessmp.ClassesSMP;
import sqyro.classessmp.core.PlayerClass;

public class Midget extends PlayerClass {
    public static final Identifier SIZE_MODIFIER_ID = Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "midget_size");
    private static final float SIZE_REMOVAL = 0.35f;

    public Midget(ServerPlayer Player) {
        super(Player);
    }

    @Override
    public String getID() {
        return "midget";
    }

    @Override
    public void onTick() {
        AttributeInstance size = Player.getAttribute(Attributes.SCALE);

        AttributeModifier modifier = size.getModifier(SIZE_MODIFIER_ID);

        if (size != null && modifier == null) {
            size.addPermanentModifier(new AttributeModifier(SIZE_MODIFIER_ID, -SIZE_REMOVAL, AttributeModifier.Operation.ADD_VALUE));
        }
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
