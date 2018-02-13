package xyz.trainwreck.Lib.api.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import xyz.trainwreck.Lib.common.inventory.InventoryOperation;

public interface IInventoryHandler {
    void saveChanges();

    void onChangeInventory(IInventory inv, int slot, InventoryOperation inventoryOperation, ItemStack removedStack, ItemStack newStack);
}
