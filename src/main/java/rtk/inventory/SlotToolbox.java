package rtk.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class SlotToolbox extends Slot {
    public SlotToolbox(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(@Nullable ItemStack stack) {
        return inventory.isItemValidForSlot(slotNumber, stack) && super.isItemValid(stack);
    }
}
