package draylar.inmis.item;

import draylar.inmis.config.BackpackInfo;
import net.minecraft.item.DyeableItem;

public class DyeableTrinketBackpackItem extends TrinketBackpackItem implements DyeableItem {

    public DyeableTrinketBackpackItem(BackpackInfo backpack, Settings settings) {
        super(backpack, settings);
    }
}
