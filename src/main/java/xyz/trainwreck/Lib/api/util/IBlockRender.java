package xyz.trainwreck.Lib.api.util;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IBlockRender {
    @SideOnly(Side.CLIENT)
    void registerBlockRenderer();

    @SideOnly(Side.CLIENT)
    void registerBlockItemRenderer();
}
