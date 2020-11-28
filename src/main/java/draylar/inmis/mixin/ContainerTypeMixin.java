package draylar.inmis.mixin;

import draylar.inmis.Inmis;
import draylar.inmis.ui.BackpackContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ContainerType.class)
public abstract class ContainerTypeMixin {

    @Shadow
    protected static <T extends Container> ContainerType<T> register(String p_221505_0_, ContainerType.IFactory<T> p_221505_1_) {
        return null;
    }

    static {
        Inmis.CONTAINER_TYPE = register(Inmis.CONTAINER_ID.toString(), (a, b) -> new BackpackContainer(a, b, b.player.getUsedItemHand()));
    }
}
