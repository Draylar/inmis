package draylar.inmis;

import draylar.inmis.ui.BackpackContainer;
import draylar.inmis.ui.BackpackContainerScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;

@Environment(EnvType.CLIENT)
public class InmisClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ScreenProviderRegistry.INSTANCE.registerFactory(
                Inmis.CONTAINER_ID,
                (i, identifier, playerEntity, packetByteBuf) -> new BackpackContainerScreen(new BackpackContainer(i, identifier, playerEntity, packetByteBuf), playerEntity)
        );
    }
}
