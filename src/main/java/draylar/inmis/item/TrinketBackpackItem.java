package draylar.inmis.item;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import draylar.inmis.Inmis;
import draylar.inmis.config.BackpackInfo;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class TrinketBackpackItem extends BackpackItem implements Trinket {

    public TrinketBackpackItem(BackpackInfo backpack, Settings settings) {
        super(backpack, settings);
    }

    @Override
    public boolean canUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if(Inmis.CONFIG.requireEmptyForUnequip) {
            return Inmis.isBackpackEmpty(stack);
        }

        return true;
    }
}
