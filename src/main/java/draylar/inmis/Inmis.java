package draylar.inmis;

import dev.emi.trinkets.api.SlotGroups;
import dev.emi.trinkets.api.Slots;
import dev.emi.trinkets.api.TrinketSlots;
import draylar.inmis.config.BackpackInfo;
import draylar.inmis.config.InmisConfig;
import draylar.inmis.content.BackpackItem;
import draylar.inmis.content.EnderBackpackItem;
import draylar.inmis.mixin.trinkets.TrinketsMixinPlugin;
import draylar.inmis.network.ServerNetworking;
import draylar.inmis.ui.BackpackScreenHandler;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Inmis implements ModInitializer {

    // inv component id is universalcomponents:inventory

    public static final Identifier CONTAINER_ID = id("backpack");
    public static final ItemGroup GROUP = FabricItemGroupBuilder.build(CONTAINER_ID, () -> new ItemStack(Registry.ITEM.get(id("frayed_backpack"))));
    public static final InmisConfig CONFIG = AutoConfig.register(InmisConfig.class, GsonConfigSerializer::new).getConfig();
    public static final ScreenHandlerType<BackpackScreenHandler> CONTAINER_TYPE = ScreenHandlerRegistry.registerExtended(CONTAINER_ID, BackpackScreenHandler::new);

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, id("ender_pouch"), new EnderBackpackItem());
        registerBackpacks();

        if (TrinketsMixinPlugin.isTrinketsLoaded) {
            TrinketSlots.addSlot(SlotGroups.CHEST, Slots.BACKPACK, new Identifier("trinkets", "textures/item/empty_trinket_slot_backpack.png"));
        }

        ServerNetworking.init();
    }

    private void registerBackpacks() {
        for (BackpackInfo backpack : Inmis.CONFIG.backpacks) {
            Item.Settings settings = new Item.Settings().group(Inmis.GROUP).maxCount(1);

            // setup fireproof item settings
            if(backpack.isFireImmune()) {
                settings.fireproof();
            }

            Registry.register(Registry.ITEM, new Identifier("inmis", backpack.getName().toLowerCase() + "_backpack"), new BackpackItem(backpack, settings));
        }
    }

    public static Identifier id(String name) {
        return new Identifier("inmis", name);
    }
}
