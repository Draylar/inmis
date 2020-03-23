package draylar.inmis.content;

import draylar.inmis.Inmis;
import draylar.inmis.config.BackpackInfo;
import net.minecraft.item.Item;

public class BackpackItem extends Item {

    private final BackpackInfo backpack;

    public BackpackItem(BackpackInfo backpack) {
        super(new Item.Settings().group(Inmis.GROUP).maxCount(1));
        this.backpack = backpack;
    }

    public BackpackInfo getTier() {
        return backpack;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
