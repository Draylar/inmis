package draylar.inmis;

import draylar.inmis.api.TrinketCompat;
import draylar.inmis.client.InmisKeybinds;
import draylar.inmis.item.BackpackItem;
import draylar.inmis.ui.BackpackHandledScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.item.DyeableItem;

@Environment(EnvType.CLIENT)
public class InmisClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        HandledScreens.register(Inmis.CONTAINER_TYPE, BackpackHandledScreen::new);
        InmisKeybinds.initialize();

        for (BackpackItem backpack : Inmis.BACKPACKS) {
            if (Inmis.TRINKETS_LOADED) {
                TrinketCompat.registerTrinketRenderer(backpack);
            }

            if (backpack instanceof DyeableItem dyeableItem) {
                ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
                    if (tintIndex > 0) {
                        return -1;
                    } else {
                        return dyeableItem.getColor(stack);
                    }
                }, backpack);
            }
        }
    }
}
