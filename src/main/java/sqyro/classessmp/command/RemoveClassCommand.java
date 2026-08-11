package sqyro.classessmp.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.NameAndId;
import sqyro.classessmp.core.PlayerClassHolder;
import sqyro.classessmp.core.SavedData.PlayerClassSavedData;
import sqyro.classessmp.core.SavedData.PlayerClassSavedDataGetter;
import sqyro.classessmp.network.ClassesNetworking;

import java.util.Collection;

public class RemoveClassCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("removeclass").requires(source -> {
            if (source.getEntity() instanceof ServerPlayer Player) {
                return source.getServer().getPlayerList().isOp(new NameAndId(Player.getUUID(), Player.getGameProfile().name()));
            }
            return true;
        }).then(Commands.argument("target", EntityArgument.players())
                .executes(context -> {
                    Collection<ServerPlayer> Targets = EntityArgument.getPlayers(context, "target");
                    PlayerClassSavedData savedData = PlayerClassSavedDataGetter.get(context.getSource().getServer().overworld());

                    int hitTargets = 0;

                    for (ServerPlayer Player : Targets) {
                        PlayerClassHolder holder = (PlayerClassHolder) Player;

                        if (holder.getPlayerClass() == null) {
                            continue;
                        }

                        hitTargets++;

                        holder.setPlayerClass(null);
                        holder.setSavedClassID("none");

                        savedData.removeClass(Player);
                        ClassesNetworking.sendClassSync(Player);

                        Player.sendSystemMessage(Component.literal("Your class has been removed.").withStyle(ChatFormatting.RED));
                    }

                    int finalHitTargets = hitTargets;
                    context.getSource().sendSuccess(() -> Component.literal("Removed class from " + finalHitTargets + " player(s).").withStyle(ChatFormatting.GRAY), true);

                    return Targets.size();
                }))
        );
    }
}