package draylar.inmis;

import draylar.inmis.config.BackpackInfo;
import draylar.inmis.config.InmisConfig;
import draylar.inmis.content.BackpackItem;
import draylar.inmis.content.EnderBackpackItem;
import draylar.inmis.ui.BackpackContainer;
import io.github.cottonmc.component.UniversalComponents;
import io.github.cottonmc.component.item.impl.ItemInventoryComponent;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import nerdhub.cardinal.components.api.event.ItemComponentCallback;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Inmis implements ModInitializer {

    public static final Identifier CONTAINER_ID = id("backpack");
    public static final ItemGroup GROUP = FabricItemGroupBuilder.build(CONTAINER_ID, () -> new ItemStack(Registry.ITEM.get(id("frayed_backpack"))));
    public static final InmisConfig CONFIG = AutoConfig.register(InmisConfig.class, GsonConfigSerializer::new).getConfig();

    @Override
    public void onInitialize() {
        ContainerProviderRegistry.INSTANCE.registerFactory(CONTAINER_ID, BackpackContainer::new);
        Registry.register(Registry.ITEM, id("ender_pouch"), new EnderBackpackItem());

        setupInventoryComponents();
    }

    private void setupInventoryComponents() {
        for (BackpackInfo backpack : Inmis.CONFIG.backpacks) {
            Item.Settings settings = new Item.Settings().group(Inmis.GROUP).maxCount(1);

            // setup fireproof item settings
            if(backpack.isFireImmune()) {
                settings.fireproof();
            }

            // register backpack
            Item item = Registry.register(Registry.ITEM, new Identifier("inmis", backpack.getName().toLowerCase() + "_backpack"), new BackpackItem(backpack, settings));
            ItemComponentCallback.event(item).register((stack, componentContainer) ->
                    componentContainer.put(UniversalComponents.INVENTORY_COMPONENT, new ItemInventoryComponent(backpack.getNumberOfRows() * backpack.getRowWidth())));
        }
    }

    public static Identifier id(String name) {
        return new Identifier("inmis", name);
    }
}
