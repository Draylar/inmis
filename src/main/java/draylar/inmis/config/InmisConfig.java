package draylar.inmis.config;

import draylar.omegaconfig.api.Comment;
import draylar.omegaconfig.api.Config;
import net.minecraft.sound.SoundEvents;

import java.util.Arrays;
import java.util.List;

public class InmisConfig implements Config {
    public List<BackpackInfo> backpacks = Arrays.asList(
            BackpackInfo.of("baby", 3, 1, false, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER),
            BackpackInfo.of("frayed", 9, 1, false, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER),
            BackpackInfo.of("plated", 9, 2, false, SoundEvents.ITEM_ARMOR_EQUIP_IRON),
            BackpackInfo.of("gilded", 9, 3, false, SoundEvents.ITEM_ARMOR_EQUIP_GOLD),
            BackpackInfo.of("bejeweled", 9, 5, false, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND),
            BackpackInfo.of("blazing", 9, 6, true, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER),
            BackpackInfo.of("withered", 11, 6, false, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER),
            BackpackInfo.of("endless", 15, 6, false, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER)
    );

    public boolean unstackablesOnly = false;

    @Comment(value = "Whether Shulker Boxes should be blacklisted from being placed inside Inmis Backpacks.")
    public boolean disableShulkers = true;

    @Comment(value = "Whether Backpacks should play a sound when opened.")
    public boolean playSound = true;

    @Override
    public String getName() {
        return "inmis";
    }
}
