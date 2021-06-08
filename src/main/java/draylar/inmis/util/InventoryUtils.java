package draylar.inmis.util;

import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

public class InventoryUtils {

    public static NbtList toTag(SimpleInventory inventory) {
        NbtList tag = new NbtList();

        for(int i = 0; i < inventory.size(); i++) {
            NbtCompound stackTag = new NbtCompound();
            stackTag.putInt("Slot", i);
            stackTag.put("Stack", inventory.getStack(i).writeNbt(new NbtCompound()));
            tag.add(stackTag);
        }

        return tag;
    }

    public static void fromTag(NbtList tag, SimpleInventory inventory) {
        inventory.clear();

        tag.forEach(element -> {
            NbtCompound stackTag = (NbtCompound) element;
            int slot = stackTag.getInt("Slot");
            ItemStack stack = ItemStack.fromNbt(stackTag.getCompound("Stack"));
            inventory.setStack(slot, stack);
        });
    }
}
