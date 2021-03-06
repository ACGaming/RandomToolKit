package rtk.udispenser;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import rtk.inventory.InventoryStack;

public class InventoryUDispenser extends InventoryStack {

    public InventoryUDispenser(ItemStack stack, int stackIndex) {
        super(stack, stackIndex);
    }

    public InventoryUDispenser(ItemStack stack) {
        super(stack);
    }

    @Override
    public int getSizeInventory() {
        return 11;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }


    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return true;
    }

    @Override
    public String getName() {
        return "ultraDispenser";
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentTranslation("tile.ultraDispenser.name");
    }
}
