package sqyro.classessmp.core;

import net.minecraft.server.level.ServerPlayer;

public abstract class PlayerClass {
    protected final ServerPlayer Player;

    public PlayerClass(ServerPlayer Player) {
        this.Player = Player;
    }

    public abstract void onTick();

    public abstract String getID();
}