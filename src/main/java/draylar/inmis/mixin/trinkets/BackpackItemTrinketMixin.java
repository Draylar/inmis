package draylar.inmis.mixin.trinkets;

import dev.emi.trinkets.api.Trinket;
import draylar.inmis.item.BackpackItem;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BackpackItem.class)
public abstract class BackpackItemTrinketMixin extends Item implements Trinket {

    public BackpackItemTrinketMixin(Settings settings) {
        super(settings);
    }
}
