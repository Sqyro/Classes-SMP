package sqyro.classessmp.core;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public abstract class PlayerClass {
    protected final ServerPlayer Player;

    public PlayerClass(ServerPlayer Player) {
        this.Player = Player;
    }

    public abstract String getID();

    public abstract void onTick();
    public abstract void onAttack(Entity Target);
    public abstract void onKeybind1();
    public abstract void onKeybind2();
}