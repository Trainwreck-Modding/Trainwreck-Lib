package xyz.trainwreck.Lib.common.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import xyz.trainwreck.Lib.api.util.IOrientable;
import xyz.trainwreck.Lib.common.util.TileHelper;

public class TileEntityBase extends TileEntity implements IOrientable {
    private EnumFacing forward = EnumFacing.NORTH;




    public void dropItems() {
        TileHelper.DropItems(this);
    }

    @Override
    public boolean canBeRotated() {
        return false;
    }

    @Override
    public EnumFacing getForward() {
        return forward;
    }


    @Override
    public void setOrientation(EnumFacing forward) {
        this.forward = forward;
    }
}
