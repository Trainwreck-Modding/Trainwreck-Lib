package xyz.trainwreck.Lib.common.registry;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import xyz.trainwreck.Lib.Lib;
import xyz.trainwreck.Lib.api.util.IBlockRender;
import xyz.trainwreck.Lib.common.blocks.BlockBase;
import xyz.trainwreck.Lib.common.util.Platform;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class RegistryHelper {

    private static List<Block> blocks = new ArrayList<>();
    private static List<ItemBlock> items = new ArrayList<>();

    public static Block addBlockToRegistry(String modid, Class<? extends Block> blockClass, Class<? extends ItemBlock> itemBlockClass) {
        Block block = null;
        ItemBlock itemBlock;
        String internalName;

        try {
            block = blockClass.getConstructor().newInstance();
            itemBlock = itemBlockClass.getConstructor(Block.class).newInstance(block);

            internalName = ((BlockBase) block).getInternalName();

            if (!internalName.equals(internalName.toLowerCase(Locale.US)))
                throw new IllegalArgumentException(String.format("InternalName names need to be all lowercase! Item: %s", internalName));

            if (internalName.isEmpty())
                throw new IllegalArgumentException(String.format("InternalName name cannot be blank! Item: %s", blockClass.getCanonicalName()));


            block.setRegistryName(modid, internalName);
            block.setUnlocalizedName(internalName);
            itemBlock.setRegistryName(Objects.requireNonNull(block.getRegistryName()));

            blocks.add(block);
            items.add(itemBlock);


            if (block instanceof IBlockRender && Platform.isClient()) {
                ((IBlockRender) block).registerBlockRenderer();
                ((IBlockRender) block).registerBlockItemRenderer();
            }


        } catch (Exception e) {
            Lib.LOGGER.info(String.format("Block %s has had a error : %s", blockClass.getCanonicalName(), e));

        }
        return block;
    }

    public static List<Block> getBlocks() {
        return blocks;
    }

    public static List<ItemBlock> getItems() {
        return items;
    }

}
