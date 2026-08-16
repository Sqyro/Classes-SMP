package sqyro.classessmp.playerclasses;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import sqyro.classessmp.core.PlayerClass;

public class DeathJester extends PlayerClass {
    public static final String CARNIVAL_FRENZY_ID = "carnival_frenzy";
    public static final int CARNIVAL_FRENZY_COOLDOWN = 1200;


    public DeathJester(ServerPlayer Player) {
        super(Player);
    }

    @Override
    public String getID() {
        return "death_jester";
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
