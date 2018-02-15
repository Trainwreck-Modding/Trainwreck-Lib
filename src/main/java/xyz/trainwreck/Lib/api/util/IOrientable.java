package xyz.trainwreck.Lib.api.util;

import net.minecraft.util.EnumFacing;

public interface IOrientable {
    boolean canBeRotated();

    EnumFacing getForward();

    void setOrientation(EnumFacing forward);
}
