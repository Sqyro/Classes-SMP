package sqyro.classessmp.blocks;

import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.MenuType;
import sqyro.classessmp.ClassesSMP;
import sqyro.classessmp.client.GUI.CaseMenu;

public class ClassesMenuTypes {
    private static final StreamCodec<ByteBuf, BlockPos> BLOCK_POS_CODEC = BlockPos.STREAM_CODEC;

    public static final MenuType<CaseMenu> CASE_MENU = Registry.register(BuiltInRegistries.MENU,
            Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "case_menu"),
            new ExtendedScreenHandlerType<>(CaseMenu::new, BLOCK_POS_CODEC)
    );

    public static void register() {
    }
}