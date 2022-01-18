package draylar.inmis.mixin;

import draylar.inmis.Inmis;
import draylar.inmis.item.BackpackItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.screen.PlayerScreenHandler$1")
public abstract class PlayerInventoryScreenMixin extends Slot {

    public PlayerInventoryScreenMixin(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Inject(method = "canTakeItems(Lnet/minecraft/entity/player/PlayerEntity;)Z", at = @At("HEAD"), cancellable = true)
    private void checkUnequip(PlayerEntity playerEntity, CallbackInfoReturnable<Boolean> cir) {
        ItemStack itemStack = getStack();
        if(itemStack.getItem() instanceof BackpackItem) {
            if(Inmis.CONFIG.requireEmptyForUnequip) {
                if(!Inmis.isBackpackEmpty(itemStack)) {
                    cir.setReturnValue(false);
                }
            }
        }
    }
}
