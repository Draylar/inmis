package draylar.inmis.api;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import draylar.inmis.Inmis;
import draylar.inmis.client.TrinketBackpackRenderer;
import draylar.inmis.config.BackpackInfo;
import draylar.inmis.item.BackpackItem;
import draylar.inmis.item.DyeableTrinketBackpackItem;
import draylar.inmis.item.EnderBackpackItem;
import draylar.inmis.item.TrinketBackpackItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;

import java.util.Optional;

public class TrinketCompat {

    public static void registerTrinketPredicate() {
        TrinketsApi.registerTrinketPredicate(Inmis.id("any_backpack"), (stack, slot, entity) -> {
            if(stack.getItem() instanceof BackpackItem || stack.getItem() instanceof EnderBackpackItem) {
                return TriState.TRUE;
            }

            return TriState.DEFAULT;
        });
    }

    public static void spillTrinketInventory(PlayerEntity player) {
        Optional<TrinketComponent> component = TrinketsApi.getTrinketComponent(player);
        if(component.isPresent()) {
            TrinketComponent trinketComponent = component.get();
            for (Pair<SlotReference, ItemStack> pair : trinketComponent.getAllEquipped()) {
                ItemStack stack = pair.getRight();
                if(stack.getItem() instanceof BackpackItem) {
                    Inmis.getBackpackContents(stack).forEach(backpackItem -> player.dropItem(backpackItem, true, false));
                    Inmis.wipeBackpack(stack);
                    player.dropItem(stack, true, false);
                    pair.getLeft().inventory().clear();
                }
            }
        }
    }

    public static void registerTrinketBackpack(Item item) {
        TrinketsApi.registerTrinket(item, (TrinketBackpackItem) item);
    }

    public static BackpackItem createTrinketBackpack(BackpackInfo backpack, FabricItemSettings settings) {
        return backpack.isDyeable() ? new DyeableTrinketBackpackItem(backpack, settings) : new TrinketBackpackItem(backpack, settings);
    }

    public static void registerTrinketRenderer(BackpackItem backpack) {
        TrinketRendererRegistry.registerRenderer(backpack, new TrinketBackpackRenderer());
    }
}
