package draylar.inmis.compat;

import com.misterpemodder.shulkerboxtooltip.api.ShulkerBoxTooltipApi;
import com.misterpemodder.shulkerboxtooltip.api.provider.PreviewProvider;
import com.misterpemodder.shulkerboxtooltip.impl.provider.EnderChestPreviewProvider;
import draylar.inmis.Inmis;
import net.minecraft.item.Item;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class InmisShulkerBoxTooltipCompat implements ShulkerBoxTooltipApi {

    @Override
    public String getModId() {
        return "inmis";
    }

    @Override
    public void registerProviders(Map<PreviewProvider, List<Item>> previewProviders) {
        previewProviders.put(new InmisPreviewProvider(), Inmis.BACKPACKS);
        previewProviders.put(new EnderChestPreviewProvider(), Collections.singletonList(Inmis.ENDER_POUCH));
    }
}
