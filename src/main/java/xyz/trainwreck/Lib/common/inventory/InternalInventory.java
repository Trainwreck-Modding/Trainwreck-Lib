package xyz.trainwreck.Lib.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import xyz.trainwreck.Lib.api.inventory.IInventoryHandler;
import xyz.trainwreck.Lib.common.util.Platform;

import java.util.Iterator;

public class InternalInventory implements IInventory, Iterable<ItemStack> {
    protected int size;
    protected int maxsize = 0;
    public boolean enableClientEvents = false;
    public final ItemStack[] inventory;
    IInventoryHandler inventoryHandler;

    public InternalInventory(IInventoryHandler inventory, int size) {
        this.size = size;
        this.inventoryHandler = inventory;
        this.inventory = new ItemStack[size];
        this.maxsize = 64;
        for (int i = 0; i < size; i++) {
            this.inventory[i]=ItemStack.EMPTY;
        }

    }

    protected boolean eventsEnabled() {
        return Platform.isServer() || this.enableClientEvents;
    }

    @Override
    public boolean isEmpty(){
        for (int i = 0; i < this.size; i++){
            if(this.getStackInSlot(i) != ItemStack.EMPTY)
                return false;
        }
        return true;

    }


    @Override
    public Iterator<ItemStack> iterator() {
        return new InventoryIterable(this,maxsize);
    }

    @Override
    public int getSizeInventory() {
        return this.size;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return this.inventory[index];
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        if (this.inventory[index] != ItemStack.EMPTY) {
            ItemStack split = this.getStackInSlot(index);
            ItemStack newStack;

            if (count >= split.getCount()) {
                newStack = this.inventory[index];
                this.inventory[index] = ItemStack.EMPTY;
            } else {
                newStack = split.splitStack(count);
            }

            if (inventoryHandler != null && this.eventsEnabled()) {
                this.inventoryHandler.onChangeInventory(this, index, InventoryOperation.decreaseStackSize, newStack, ItemStack.EMPTY);
            }

            this.markDirty();
            return newStack;
        }

        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        ItemStack oldStack = this.inventory[index];
        this.inventory[index] = stack;

        if (this.inventoryHandler != null && this.eventsEnabled()) {
            ItemStack removed = oldStack;
            ItemStack added = stack;

            if (oldStack != ItemStack.EMPTY && stack != ItemStack.EMPTY && Platform.isSameItem(oldStack, stack)) {
                if (oldStack.getCount() > stack.getCount()) {
                    removed = removed.copy();
                    removed.shrink(stack.getCount());
                } else if (oldStack.getCount() < stack.getCount()) {
                    added = added.copy();
                    added.shrink(oldStack.getCount());
                    removed = ItemStack.EMPTY;
                } else {
                    removed = added = ItemStack.EMPTY;
                }
            }

            this.inventoryHandler.onChangeInventory(this, index, InventoryOperation.setInventorySlotContents, removed, added);

            this.markDirty();
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return this.maxsize > 64 ? 64 : this.maxsize;
    }

    @Override
    public void markDirty() {
        if (this.inventoryHandler != null && this.eventsEnabled()) {
            this.inventoryHandler.onChangeInventory(this, -1, InventoryOperation.markDirty, ItemStack.EMPTY, ItemStack.EMPTY);
        }
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return true;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    public void setMaxsize(int maxsize) {
        this.maxsize = maxsize;
    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {

    }

    @Override
    public String getName() {
        return "internal";
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public ITextComponent getDisplayName() {
        return null;
    }
}
