package draylar.inmis;

import draylar.inmis.client.InmisKeybinds;
import draylar.inmis.ui.BackpackHandledScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

@Environment(EnvType.CLIENT)
public class InmisClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(Inmis.CONTAINER_TYPE, BackpackHandledScreen::new);
        InmisKeybinds.initialize();
    }
}
