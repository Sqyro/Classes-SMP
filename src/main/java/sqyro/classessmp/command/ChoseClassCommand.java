package sqyro.classessmp.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import sqyro.classessmp.core.PlayerClass;
import sqyro.classessmp.core.PlayerClassCreator;
import sqyro.classessmp.core.PlayerClassHolder;
import sqyro.classessmp.core.SavedData.ModSavedData;
import sqyro.classessmp.core.SavedData.PlayerClassSavedData;

public class ChoseClassCommand {
    public static void register(CommandDispatcher<CommandSourceStack> Dispatcher) {
        Dispatcher.register(Commands.literal("choseclass")
                .then(Commands.literal("testclass").executes(Context -> {
                    ServerPlayer Player = Context.getSource().getPlayerOrException();

                    PlayerClassHolder Holder = (PlayerClassHolder) Player;
                    String ID = "testclass";

                    PlayerClass playerClass = PlayerClassCreator.createClass(ID, Player);

                    Holder.setSavedClassID(ID);
                    Holder.setPlayerClass(playerClass);

                    PlayerClassSavedData savedData = ModSavedData.get(Player.level().getServer().overworld());

                    savedData.setClass(Player.getUUID(), ID);
                    return 1;
                }))
        );
    }
}