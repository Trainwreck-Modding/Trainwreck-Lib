package xyz.trainwreck.Lib.common.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import xyz.trainwreck.Lib.api.inventory.IInventoryHandler;
import xyz.trainwreck.Lib.common.inventory.InventoryOperation;

public abstract class TileInventoryBase extends TileEntityBase implements ISidedInventory, IInventoryHandler {
    public abstract IInventory getInternalInventory();

    @Override
    public int getSizeInventory() {
        return getInternalInventory().getSizeInventory();
    }

    @Override
    public boolean isEmpty() {
        return getInternalInventory().isEmpty();
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return getInternalInventory().getStackInSlot(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return getInternalInventory().decrStackSize(index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return getInternalInventory().removeStackFromSlot(index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        getInternalInventory().setInventorySlotContents(index,stack);
    }

    @Override
    public int getInventoryStackLimit() {
        return getInternalInventory().getInventoryStackLimit();
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return getInternalInventory().isUsableByPlayer(player);
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return getInternalInventory().isItemValidForSlot(index, stack);
    }

    @Override
    public int getField(int id) {
        return getInternalInventory().getField(id);
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return getInternalInventory().getFieldCount();
    }

    @Override
    public void clear() {

    }

    @Override
    public String getName() {
        return getInternalInventory().getName();
    }

    @Override
    public boolean hasCustomName() {
        return getInternalInventory().hasCustomName();
    }

    @Override
    public void saveChanges() {

    }

    @Override
    public abstract void onChangeInventory(IInventory inv, int slot, InventoryOperation inventoryOperation, ItemStack removedStack, ItemStack newStack);

}
