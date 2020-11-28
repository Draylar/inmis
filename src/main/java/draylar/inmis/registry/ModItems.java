package draylar.inmis.registry;

import draylar.inmis.Inmis;
import draylar.inmis.content.BackpackItem;
import draylar.inmis.content.EnderBackpackItem;
import draylar.inmis.data.BackpackInfo;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final Item.Properties DEFAULT_SETTINGS = new Item.Properties().tab(Inmis.GROUP).stacksTo(1);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "inmis");

    public static final RegistryObject<Item> ENDER_POUCH = register("ender_pouch", new EnderBackpackItem());
    public static final RegistryObject<Item> BABY_POUCH = register("baby_backpack", new BackpackItem(BackpackInfo.of("baby", 3, 1, false, SoundEvents.ARMOR_EQUIP_LEATHER), DEFAULT_SETTINGS));
    public static final RegistryObject<Item> FRAYED_backpack = register("frayed_backpack", new BackpackItem(BackpackInfo.of("frayed", 9, 1, false, SoundEvents.ARMOR_EQUIP_LEATHER), DEFAULT_SETTINGS));
    public static final RegistryObject<Item> PLATED_backpack = register("plated_backpack", new BackpackItem(BackpackInfo.of("plated", 9, 2, false, SoundEvents.ARMOR_EQUIP_IRON), DEFAULT_SETTINGS));
    public static final RegistryObject<Item> GILDED_backpack = register("gilded_backpack", new BackpackItem(BackpackInfo.of("gilded", 9, 3, false, SoundEvents.ARMOR_EQUIP_GOLD), DEFAULT_SETTINGS));
    public static final RegistryObject<Item> BEJEWELED_backpack = register("bejeweled_backpack", new BackpackItem(BackpackInfo.of("bejeweled", 9, 5, false, SoundEvents.ARMOR_EQUIP_DIAMOND), DEFAULT_SETTINGS));
    public static final RegistryObject<Item> BLAZING_backpack = register("blazing_backpack", new BackpackItem(BackpackInfo.of("blazing", 9, 6, true, SoundEvents.ARMOR_EQUIP_LEATHER), new Item.Properties().tab(Inmis.GROUP).stacksTo(1).fireResistant()));
    public static final RegistryObject<Item> WITHERED_backpack = register("withered_backpack", new BackpackItem(BackpackInfo.of("withered", 11, 6, false, SoundEvents.ARMOR_EQUIP_LEATHER), DEFAULT_SETTINGS));
    public static final RegistryObject<Item> ENDLESS_backpack = register("endless_backpack", new BackpackItem(BackpackInfo.of("endless", 15, 6, false, SoundEvents.ARMOR_EQUIP_LEATHER), DEFAULT_SETTINGS));

    private static RegistryObject<Item> register(String name, Item item) {
        return ITEMS.register(name, () -> {
            return item;
        });
    }

    private ModItems() {
        System.out.println("hi");
    }
}
