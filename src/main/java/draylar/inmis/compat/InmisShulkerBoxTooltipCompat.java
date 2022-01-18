package draylar.inmis.compat;

import com.misterpemodder.shulkerboxtooltip.api.ShulkerBoxTooltipApi;
import com.misterpemodder.shulkerboxtooltip.api.provider.PreviewProviderRegistry;
import com.misterpemodder.shulkerboxtooltip.impl.provider.EnderChestPreviewProvider;
import draylar.inmis.Inmis;
import net.minecraft.item.Item;

import java.util.stream.Collectors;

public class InmisShulkerBoxTooltipCompat implements ShulkerBoxTooltipApi {

    @Override
    public void registerProviders(PreviewProviderRegistry registry) {
        registry.register(Inmis.id("backpack"), new InmisPreviewProvider(), Inmis.BACKPACKS.stream().map(backpack -> (Item) backpack).collect(Collectors.toList()));
        registry.register(Inmis.id("ender_pouch"), new EnderChestPreviewProvider(), Inmis.ENDER_POUCH);
    }
}
