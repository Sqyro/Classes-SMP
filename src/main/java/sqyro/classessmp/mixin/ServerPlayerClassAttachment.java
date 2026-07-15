package sqyro.classessmp.mixin;

import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import sqyro.classessmp.core.PlayerClass;
import sqyro.classessmp.core.PlayerClassHolder;

@Mixin(ServerPlayer.class)
public class ServerPlayerClassAttachment implements PlayerClassHolder {
    private PlayerClass playerClass;
    private String savedClassID = "none";

    @Override
    public PlayerClass getPlayerClass() {
        return playerClass;
    }

    @Override
    public void setPlayerClass(PlayerClass playerClass) {
        this.playerClass = playerClass;
        if (playerClass != null) {
            this.savedClassID = playerClass.getID();
        }
    }

    @Override
    public String getSavedClassID() {
        return savedClassID;
    }


    @Override
    public void setSavedClassID(String ID) {
        this.savedClassID = ID;
        this.playerClass = null;
    }
}