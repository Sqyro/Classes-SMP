package sqyro.classessmp.playerclasses;

import net.minecraft.server.level.ServerPlayer;
import sqyro.classessmp.core.PlayerClass;

public class Terrorist extends PlayerClass {
    public Terrorist(ServerPlayer Player) {
        super(Player);
    }

    @Override
    public String getID() {
        return "terrorist";
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
}
