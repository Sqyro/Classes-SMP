package sqyro.classessmp.playerclasses;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import sqyro.classessmp.ClassesSMP;
import sqyro.classessmp.core.PlayerClass;

public class TestClass extends PlayerClass {
    public TestClass(ServerPlayer Player) {
        super(Player);
    }

    @Override
    public String getID() {
        return "testclass";
    }

    @Override
    public void onTick() {
        if (Player.tickCount % 20 == 0) {
            ClassesSMP.LOGGER.info("TestClass ticking for {}", Player.getName().getString());
        }
    }

    @Override
    public void onAttack(Entity Target) {

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