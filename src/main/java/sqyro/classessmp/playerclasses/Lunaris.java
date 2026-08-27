package sqyro.classessmp.playerclasses;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import sqyro.classessmp.core.PlayerClass;

public class Lunaris extends PlayerClass {
    public Lunaris(ServerPlayer Player) {
        super(Player);
    }

    @Override
    public String getID() {
        return "lunaris";
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
