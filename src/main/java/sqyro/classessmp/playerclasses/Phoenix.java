package sqyro.classessmp.playerclasses;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import sqyro.classessmp.core.PlayerClass;

public class Phoenix extends PlayerClass {
    public static final String BLAZING_BREATH_ID = "blazing_breath";
    public static final int BLAZING_BREATH_COOLDOWN = 100;

    public static final String WINGS_OF_FIRE_ID = "wings_of_fire";
    public static final int WINGS_OF_FIRE_COOLDOWN = 100;

    public static final String INFERNAL_ID = "infernal";
    public static final int INFERNAL_COOLDOWN = 100;

    public Phoenix(ServerPlayer Player) {
        super(Player);
    }

    @Override
    public String getID() {
        return "phoenix";
    }

    @Override
    public void onTick() {

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
