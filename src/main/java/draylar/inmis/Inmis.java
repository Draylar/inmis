package draylar.inmis;

import draylar.inmis.content.InmisItemGroup;
import draylar.inmis.registry.ModItems;
import draylar.inmis.ui.BackpackContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("inmis")
public class Inmis {

    public static final ResourceLocation CONTAINER_ID = id("backpack");
    public static final ItemGroup GROUP = new InmisItemGroup();
    public static ContainerType<BackpackContainer> CONTAINER_TYPE;

    public Inmis() {
        ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static ResourceLocation id(String name) {
        return new ResourceLocation("inmis", name);
    }
}
