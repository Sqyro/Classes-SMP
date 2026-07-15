package sqyro.classessmp.core;

import net.minecraft.server.level.ServerPlayer;
import sqyro.classessmp.playerclasses.TestClass;

public class PlayerClassCreator {
    public static PlayerClass createClass(String ID, ServerPlayer Player) {
        return switch (ID) {
            case "testclass" -> new TestClass(Player);

            default -> null;
        };
    }
}