package xyz.trainwreck.Lib.common.registry;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import xyz.trainwreck.Lib.TrainwreckLib;
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

    public static Block addBlockToRegistry(String modid, Class<? extends Block> blockClass) {
        Block block = null;
        ItemBlock itemBlock;
        String internalName;

        try {
            block = blockClass.getConstructor().newInstance();
            itemBlock = new ItemBlock(block);

            internalName = ((BlockBase) block).getInternalName();

            if (!internalName.equals(internalName.toLowerCase(Locale.US)))
                throw new IllegalArgumentException(String.format("InternalName values need to be all lowercase! Item: %s", internalName));

            if (internalName.isEmpty())
                throw new IllegalArgumentException(String.format("InternalName cannot be blank! Item: %s", blockClass.getCanonicalName()));


            block.setRegistryName(modid, internalName);
            block.setUnlocalizedName(internalName);
            itemBlock.setRegistryName(Objects.requireNonNull(block.getRegistryName()));

            blocks.add(block);
            items.add(itemBlock);

            //if (block instanceof IBlockRender && Platform.isClient()) {
            if (Platform.isClient()) {
                ((IBlockRender) block).registerBlockRenderer();
                ((IBlockRender) block).registerBlockItemRenderer();
            }

        } catch (Exception e) {

            TrainwreckLib.LOGGER.error(String.format("Block %s has had a error : %s", blockClass.getCanonicalName(), e));
        }

        return block;
    }

    public static void initItemBlocks(String modid, Block block) {

        try {
            Item itemBlock = Item.getItemFromBlock(block);
            ModelResourceLocation model = new ModelResourceLocation(String.format("%s", block.getRegistryName()));
            ModelBakery.registerItemVariants(itemBlock, model);
            ItemMeshDefinition meshDefinition = stack -> model;
            ModelLoader.setCustomMeshDefinition(itemBlock, meshDefinition);

        } catch (Exception e) {

            TrainwreckLib.LOGGER.error(String.format("Failed to initialize ItemBlock for: %s || %s", block.getUnlocalizedName(), e));
        }
    }

    public static List<Block> getBlocks() {
        return blocks;
    }

    public static List<ItemBlock> getItems() {
        return items;
    }
}
