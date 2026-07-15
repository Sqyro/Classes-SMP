package sqyro.classessmp.playerclasses;

import net.minecraft.server.level.ServerPlayer;
import sqyro.classessmp.ClassesSMP;
import sqyro.classessmp.core.PlayerClass;

public class TestClass extends PlayerClass {
    public TestClass(ServerPlayer Player) {
        super(Player);
    }

    @Override
    public void onTick() {
        if (Player.tickCount % 20 == 0) {
            ClassesSMP.LOGGER.info("TestClass ticking for {}", Player.getName().getString());
        }
    }

    @Override
    public String getID() {
        return "testclass";
    }
}