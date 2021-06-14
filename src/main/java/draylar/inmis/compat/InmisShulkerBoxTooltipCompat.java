package draylar.inmis.compat;

import com.misterpemodder.shulkerboxtooltip.api.ShulkerBoxTooltipApi;
import com.misterpemodder.shulkerboxtooltip.api.provider.PreviewProviderRegistry;
import com.misterpemodder.shulkerboxtooltip.impl.provider.EnderChestPreviewProvider;
import draylar.inmis.Inmis;

public class InmisShulkerBoxTooltipCompat implements ShulkerBoxTooltipApi {

    @Override
    public void registerProviders(PreviewProviderRegistry registry) {
        registry.register(Inmis.id("backpack"), new InmisPreviewProvider(), Inmis.BACKPACKS);
        registry.register(Inmis.id("ender_pouch"), new EnderChestPreviewProvider(), Inmis.ENDER_POUCH);
    }
}
