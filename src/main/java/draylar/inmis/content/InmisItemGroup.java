package draylar.inmis.content;

import draylar.inmis.registry.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class InmisItemGroup extends ItemGroup {

    public InmisItemGroup() {
        super("inmis.group");
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ModItems.ENDER_POUCH.get());
    }
}
