package sqyro.classessmp.playerclasses;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import sqyro.classessmp.core.PlayerClass;

public class SuperFishNinja extends PlayerClass {
    public static final String NINJA_INVISIBILITY_ID = "invisibility";
    public static final int NINJA_INVISIBILITY_COOLDOWN = 100;

    public static final String NINJA_TELEPORT_ID = "ninja_teleport";
    public static final int NINJA_TELEPORT_COOLDOWN = 100;

    public SuperFishNinja(ServerPlayer Player) {
        super(Player);
    }

    @Override
    public String getID() {
        return "superfishninja";
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
