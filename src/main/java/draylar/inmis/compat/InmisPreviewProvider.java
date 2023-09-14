package draylar.inmis.compat;

import com.misterpemodder.shulkerboxtooltip.api.PreviewContext;
import com.misterpemodder.shulkerboxtooltip.api.provider.PreviewProvider;
import draylar.inmis.config.BackpackInfo;
import draylar.inmis.item.BackpackItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.collection.DefaultedList;

import java.util.List;

public class InmisPreviewProvider implements PreviewProvider {

    @Override
    public boolean shouldDisplay(PreviewContext context) {
        return !getInventory(context).stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public List<ItemStack> getInventory(PreviewContext context) {
        BackpackInfo info = ((BackpackItem) context.stack().getItem()).getTier();
        List<ItemStack> stacks = DefaultedList.ofSize(info.getNumberOfRows() * info.getRowWidth(), ItemStack.EMPTY);
        NbtList inventoryTag = context.stack().getOrCreateNbt().getList("Inventory", NbtElement.COMPOUND_TYPE);

        inventoryTag.forEach(element -> {
            NbtCompound stackTag = (NbtCompound) element;
            int slot = stackTag.getInt("Slot");
            ItemStack stack = ItemStack.fromNbt(stackTag.getCompound("Stack"));
            stacks.set(slot, stack);
        });

        return stacks;
    }

    @Override
    public int getInventoryMaxSize(PreviewContext context) {
        return getInventory(context).size();
    }

    @Override
    public int getMaxRowSize(PreviewContext context) {
        return ((BackpackItem) context.stack().getItem()).getTier().getRowWidth();
    }
}
