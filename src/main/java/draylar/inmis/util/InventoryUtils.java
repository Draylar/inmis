package draylar.inmis.util;

import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

public class InventoryUtils {

    public static ListTag toTag(SimpleInventory inventory) {
        ListTag tag = new ListTag();

        for(int i = 0; i < inventory.size(); i++) {
            CompoundTag stackTag = new CompoundTag();
            stackTag.putInt("Slot", i);
            stackTag.put("Stack", inventory.getStack(i).toTag(new CompoundTag()));
            tag.add(stackTag);
        }

        return tag;
    }

    public static void fromTag(ListTag tag, SimpleInventory inventory) {
        inventory.clear();

        tag.forEach(element -> {
            CompoundTag stackTag = (CompoundTag) element;
            int slot = stackTag.getInt("Slot");
            ItemStack stack = ItemStack.fromTag(stackTag.getCompound("Stack"));
            inventory.setStack(slot, stack);
        });
    }
}
