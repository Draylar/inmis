package draylar.inmis.api;

import dev.emi.trinkets.api.TrinketsApi;
import draylar.inmis.Inmis;
import draylar.inmis.item.BackpackItem;
import draylar.inmis.item.EnderBackpackItem;
import net.fabricmc.fabric.api.util.TriState;

public class TrinketCompat {

    public static void registerTrinketPredicate() {
        TrinketsApi.registerTrinketPredicate(Inmis.id("any_backpack"), (stack, slot, entity) -> {
            if(stack.getItem() instanceof BackpackItem || stack.getItem() instanceof EnderBackpackItem) {
                return Inmis.CONFIG.enableTrinketCompatibility ? TriState.TRUE : TriState.FALSE;
            }

            return TriState.DEFAULT;
        });
    }
}
