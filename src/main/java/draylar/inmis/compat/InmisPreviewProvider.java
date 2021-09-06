package draylar.inmis.compat;

import com.misterpemodder.shulkerboxtooltip.api.PreviewContext;
import com.misterpemodder.shulkerboxtooltip.api.provider.PreviewProvider;
import draylar.inmis.item.BackpackItem;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

import java.util.ArrayList;
import java.util.List;

public class InmisPreviewProvider implements PreviewProvider {

    @Override
    public boolean shouldDisplay(PreviewContext context) {
        return !getInventory(context).stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public List<ItemStack> getInventory(PreviewContext context) {
        List<ItemStack> stacks = new ArrayList<>();
        NbtList inventoryTag = context.getStack().getOrCreateNbt().getList("Inventory", NbtType.COMPOUND);

        inventoryTag.forEach(element -> {
            NbtCompound stackTag = (NbtCompound) element;
            int slot = stackTag.getInt("Slot");
            ItemStack stack = ItemStack.fromNbt(stackTag.getCompound("Stack"));
            stacks.add(slot, stack);
        });

        return stacks;
    }

    @Override
    public int getInventoryMaxSize(PreviewContext context) {
        return getInventory(context).size();
    }

    @Override
    public int getMaxRowSize(PreviewContext context) {
        return ((BackpackItem) context.getStack().getItem()).getTier().getRowWidth();
    }
}
