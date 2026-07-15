package sqyro.classessmp.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import sqyro.classessmp.core.PlayerClass;
import sqyro.classessmp.core.PlayerClassHolder;
import sqyro.classessmp.core.SavedData.PlayerClassSavedDataGetter;
import sqyro.classessmp.core.SavedData.PlayerClassSavedData;
import sqyro.classessmp.playerclasses.PlayerClasses;

public class ChoseClassCommand {
    public static void register(CommandDispatcher<CommandSourceStack> Dispatcher) {
        Dispatcher.register(Commands.literal("choseclass")
                .then(Commands.argument("class", StringArgumentType.word()).suggests((context, builder) -> {
                    for (String ID : PlayerClasses.getIDs()) {
                        builder.suggest(ID);
                    }
                    return builder.buildFuture();
                }).executes(context -> {
                    ServerPlayer Player = context.getSource().getPlayerOrException();
                    String ID = StringArgumentType.getString(context, "class");
                    PlayerClass playerClass = PlayerClasses.create(ID, Player);

                    if (playerClass == null) {
                        context.getSource().sendFailure(Component.literal(ID + "is not a valid classname").withStyle(ChatFormatting.RED));
                        return 0;
                    }

                    ((PlayerClassHolder) Player).setSavedClassID(ID);
                    ((PlayerClassHolder) Player).setPlayerClass(playerClass);

                    PlayerClassSavedData savedData = PlayerClassSavedDataGetter.get(Player.level().getServer().overworld());
                    savedData.setClass(Player.getUUID(), ID);

                    return 1;
                }))
        );
    }
}