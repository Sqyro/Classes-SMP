package sqyro.classessmp.playerclasses;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import sqyro.classessmp.core.PlayerClass;

public class SigeonPex extends PlayerClass {
    public static final int SIGEON_ROOTING_COOLDOWN = 100;

    public SigeonPex(ServerPlayer Player) {
        super(Player);
    }

    @Override
    public String getID() {
        return "sigeon_pex";
    }

    @Override
    public void onTick() {

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
