package xyz.trainwreck.Lib.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import xyz.trainwreck.Lib.common.tileentity.TileEntityBase;
import xyz.trainwreck.Lib.common.util.TileHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockTileBase extends BlockBase {
    @Nonnull
    private Class<? extends TileEntity> tileEntityClass;



    public BlockTileBase(Material blockMaterialIn, String resourcePath, String modid) {
        super(blockMaterialIn, resourcePath, modid);

    }

    protected void setTileEntity(final Class<? extends TileEntity> clazz) {
        this.tileEntityClass = clazz;
        this.setTileProvider(true);
        this.isInventory = IInventory.class.isAssignableFrom(clazz);

        String tileName = "tileentity." + modid + "." + clazz.getSimpleName();
        GameRegistry.registerTileEntity(this.tileEntityClass, tileName);
    }

    @Nullable
    @Override
    public EnumFacing[] getValidRotations(World world, BlockPos pos) {
        final TileEntityBase tileEntity = TileHelper.getTileEntity(world, pos, TileEntityBase.class);
        if (tileEntity != null && tileEntity.canBeRotated())
            return EnumFacing.HORIZONTALS;

        return super.getValidRotations(world, pos);
    }


    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        final TileEntityBase tileEntity = TileHelper.getTileEntity(worldIn,pos,TileEntityBase.class);
        if (tileEntity != null){
            tileEntity.dropItems();
        }
    }

    private void setTileProvider(final boolean b) {
        ReflectionHelper.setPrivateValue(Block.class, this, b, "isTileProvider");
    }


    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        super.getDrops(drops, world, pos, state, fortune);
    }


    @Nullable
    @Override
    public net.minecraft.tileentity.TileEntity createTileEntity(World world, IBlockState state) {
        try {
            return this.tileEntityClass.newInstance();
        } catch (final InstantiationException ex) {
            throw new IllegalStateException("Failed to create a new instance of an illegal class " + this.tileEntityClass, ex);
        } catch (final IllegalAccessException ex) {
            throw new IllegalStateException("Failed to create a new instance of " + this.tileEntityClass + " because of a lack of permissions", ex);
        }
    }

    public Class<? extends TileEntity> getTileEntityClass() {
        return this.tileEntityClass;
    }
}
