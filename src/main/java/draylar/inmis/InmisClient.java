package draylar.inmis;

import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import draylar.inmis.client.InmisKeybinds;
import draylar.inmis.client.TrinketBackpackRenderer;
import draylar.inmis.ui.BackpackHandledScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.item.DyeableItem;

@Environment(EnvType.CLIENT)
public class InmisClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(Inmis.CONTAINER_TYPE, BackpackHandledScreen::new);
        InmisKeybinds.initialize();

        Inmis.BACKPACKS.forEach(backpack -> {
            if(Inmis.TRINKETS_LOADED) {
                TrinketRendererRegistry.registerRenderer(backpack, new TrinketBackpackRenderer());
            }

            if(backpack instanceof DyeableItem dyeableItem) {
                ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
                    if(tintIndex > 0) {
                        return -1;
                    } else {
                        return dyeableItem.getColor(stack);
                    }
                }, backpack);
            }
        });
    }
}
