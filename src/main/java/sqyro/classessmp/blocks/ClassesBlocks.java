package sqyro.classessmp.blocks;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import sqyro.classessmp.ClassesSMP;

import java.util.function.Function;


public class ClassesBlocks {
    public static final Block WHITE_KEYPAD = registerBlock("white_keypad", properties -> new KeypadBlock(properties.strength(2.0F).noOcclusion()));
    public static final Block BOSSMINER2000 = registerBossminerBlock("bossminer2000", properties -> new BossminerBlock(properties.strength(1.0F).noOcclusion().sound(SoundType.WOOL)));

    private static Block registerBlock(String Name, Function<BlockBehaviour.Properties, Block> Factory) {
        Identifier ID = Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, Name);

        Block block = Factory.apply(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, ID)));
        Registry.register(BuiltInRegistries.BLOCK, ID, block);
        Registry.register(BuiltInRegistries.ITEM, ID, new BlockItem(block, new Item.Properties().setId(ResourceKey.create(Registries.ITEM, ID))));

        return block;
    }

    private static Block registerBossminerBlock(String Name, Function<BlockBehaviour.Properties, Block> Factory) {
        Identifier ID = Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, Name);

        Block block = Factory.apply(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, ID)));
        Registry.register(BuiltInRegistries.BLOCK, ID, block);
        Registry.register(BuiltInRegistries.ITEM, ID, new BlockItem(block, new Item.Properties().fireResistant().food(new FoodProperties(4, 6, true)).setId(ResourceKey.create(Registries.ITEM, ID))));

        return block;
    }


    public static void register() {}
}
