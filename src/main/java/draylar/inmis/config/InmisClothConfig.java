package draylar.inmis.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import net.minecraft.sound.SoundEvents;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Config(name = "inmis")
@Config.Gui.Background("minecraft:textures/block/stone.png")
public class InmisClothConfig implements ConfigData {

    public List<BackpackInfo> backpacks = Arrays.asList(
            BackpackInfo.of("baby", 3, 1, false, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER),
            BackpackInfo.of("frayed", 9, 1, false, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, true),
            BackpackInfo.of("plated", 9, 2, false, SoundEvents.ITEM_ARMOR_EQUIP_IRON),
            BackpackInfo.of("gilded", 9, 3, false, SoundEvents.ITEM_ARMOR_EQUIP_GOLD),
            BackpackInfo.of("bejeweled", 9, 5, false, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND),
            BackpackInfo.of("blazing", 9, 6, true, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER),
            BackpackInfo.of("withered", 11, 6, false, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER),
            BackpackInfo.of("endless", 15, 6, false, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER)
    );

    public boolean unstackablesOnly = false;

    @Comment("Whether Shulker Boxes should be blacklisted from being placed inside Inmis Backpacks.")
    public boolean disableShulkers = true;

    @Comment(value = "Backpack item blacklist")
    public List<String> blacklist = new ArrayList<>();

    @Comment("Whether Backpacks should play a sound when opened.")
    public boolean playSound = true;


    @Comment("If true, players will not be able to open Backpacks by right-clicking. This will require the player to open backpacks as a Trinket or Armor slot item with the bound key.")
    public boolean requireArmorTrinketToOpen = false;

    public boolean allowBackpacksInChestplate = true;


    @Comment("If this value is set to false, players will not be able to equip or open backpacks in a Trinkets slot.")
    public boolean enableTrinketCompatibility = true;

    @Comment(value = "If true, players will not be able to take backpacks out of armor/trinket slots if the backpack is not empty.")
    public boolean requireEmptyForUnequip = false;

    @Comment("If true, backpacks in armor/trinket slots will empty out into the world (scatter contents) when the player dies.")
    public boolean spillArmorBackpacksOnDeath = false;

    @Comment("If true, backpacks in main inventory slots will empty out into the world (scatter contents) when the player dies.")
    public boolean spillMainBackpacksOnDeath = false;

    @Comment("Set this value to false to disable Backpack rendering when using Trinkets.")
    public boolean trinketRendering = true;

    @Comment("Color of the text rendered in the INMIS GUI.")
    public String guiTitleColor = "0x404040";

}
