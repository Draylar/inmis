package draylar.inmis.mixin;

import draylar.inmis.Inmis;
import draylar.inmis.ui.BackpackHandledScreen;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ScreenManager.class)
public abstract class ScreenManagerMixin {

    @Shadow
    public static <M extends Container, U extends Screen & IHasContainer<M>> void register(ContainerType<? extends M> p_216911_0_, ScreenManager.IScreenFactory<M, U> p_216911_1_) {
    }

    static {
        register(Inmis.CONTAINER_TYPE, BackpackHandledScreen::new);
    }
}
