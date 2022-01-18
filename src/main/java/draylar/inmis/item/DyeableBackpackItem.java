package draylar.inmis.item;

import draylar.inmis.config.BackpackInfo;
import net.minecraft.item.DyeableItem;

public class DyeableBackpackItem extends BackpackItem implements DyeableItem {

    public DyeableBackpackItem(BackpackInfo backpack, Settings settings) {
        super(backpack, settings);
    }
}
