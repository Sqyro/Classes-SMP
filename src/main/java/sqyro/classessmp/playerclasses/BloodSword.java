package sqyro.classessmp.playerclasses;

import net.minecraft.server.level.ServerPlayer;
import sqyro.classessmp.core.PlayerClass;

public class BloodSword extends PlayerClass {
    public BloodSword(ServerPlayer Player) {
        super(Player);
    }

    @Override
    public String getID() {
        return "blood_sword";
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
}
