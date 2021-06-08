package draylar.inmis;

import draylar.inmis.config.BackpackInfo;
import draylar.inmis.config.InmisConfig;
import draylar.inmis.item.BackpackItem;
import draylar.inmis.item.EnderBackpackItem;
import draylar.inmis.mixin.trinkets.TrinketsMixinPlugin;
import draylar.inmis.network.ServerNetworking;
import draylar.inmis.ui.BackpackScreenHandler;
import draylar.omegaconfig.OmegaConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Inmis implements ModInitializer {

    public static final Logger LOGGER = LogManager.getLogger();
    public static final Identifier CONTAINER_ID = id("backpack");
    public static final ItemGroup GROUP = FabricItemGroupBuilder.build(CONTAINER_ID, () -> new ItemStack(Registry.ITEM.get(id("frayed_backpack"))));
    public static final InmisConfig CONFIG = OmegaConfig.register(InmisConfig.class);
    public static final ScreenHandlerType<BackpackScreenHandler> CONTAINER_TYPE = ScreenHandlerRegistry.registerExtended(CONTAINER_ID, BackpackScreenHandler::new);
    public static final List<Item> BACKPACKS = new ArrayList<>();
    public static final Item ENDER_POUCH = Registry.register(Registry.ITEM, id("ender_pouch"), new EnderBackpackItem());;

    @Override
    public void onInitialize() {
        registerBackpacks();
        ServerNetworking.init();
    }

    private void registerBackpacks() {
        InmisConfig defaultConfig = new InmisConfig();

        for (BackpackInfo backpack : Inmis.CONFIG.backpacks) {
            Item.Settings settings = new Item.Settings().group(Inmis.GROUP).maxCount(1);

            // setup fireproof item settings
            if(backpack.isFireImmune()) {
                settings.fireproof();
            }

            // old config instances do not have the sound stuff
            if(backpack.getOpenSound() == null) {
                Optional<BackpackInfo> any = defaultConfig.backpacks.stream().filter(info -> info.getName().equals(backpack.getName())).findAny();
                any.ifPresent(backpackInfo -> backpack.setOpenSound(backpackInfo.getOpenSound()));

                // if it is STILL null, log an error and set a default
                if(backpack.getOpenSound() == null) {
                    LOGGER.info(String.format("Could not find a sound event for %s in inmis.json config.", backpack.getName()));
                    LOGGER.info("Consider regenerating your config, or assigning the openSound value. Rolling with defaults for now.");
                    backpack.setOpenSound("minecraft:item.armor.equip_leather");
                }
            }

            BackpackItem registered = Registry.register(Registry.ITEM, new Identifier("inmis", backpack.getName().toLowerCase() + "_backpack"), new BackpackItem(backpack, settings));
            BACKPACKS.add(registered);
        }
    }

    public static Identifier id(String name) {
        return new Identifier("inmis", name);
    }
}
