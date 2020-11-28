package draylar.inmis.util;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;

public class InventoryUtils {

    public static ListNBT toTag(Inventory inventory) {
        ListNBT tag = new ListNBT();

        for(int i = 0; i < inventory.getContainerSize(); i++) {
            CompoundNBT stackTag = new CompoundNBT();
            stackTag.putInt("Slot", i);
            stackTag.put("Stack", inventory.getItem(i).save(new CompoundNBT()));
            tag.add(stackTag);
        }

        return tag;
    }

    public static void fromTag(ListNBT tag, Inventory inventory) {
        inventory.clearContent();

        tag.forEach(element -> {
            CompoundNBT stackTag = (CompoundNBT) element;
            int slot = stackTag.getInt("Slot");
            ItemStack stack = ItemStack.of(stackTag.getCompound("Stack"));
            inventory.setItem(slot, stack);
        });
    }
}
