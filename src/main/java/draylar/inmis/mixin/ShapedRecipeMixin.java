package draylar.inmis.mixin;

import draylar.inmis.item.BackpackItem;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.registry.DynamicRegistryManager;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShapedRecipe.class)
public abstract class ShapedRecipeMixin {

    @Shadow
    public abstract ItemStack getOutput(DynamicRegistryManager registryManager);

    @Inject(method = "craft", at = @At("HEAD"), cancellable = true)
    private void onCraft(RecipeInputInventory recipeInputInventory, DynamicRegistryManager dynamicRegistryManager, CallbackInfoReturnable<ItemStack> cir) {
        // get both backpacks
        ItemStack centerSlot = recipeInputInventory.getStack(4);

        // only attempt to apply nbt if the center stack of the original recipe was a backpack
        if (centerSlot.getItem() instanceof BackpackItem) {
            ItemStack newBackpack = this.getOutput(dynamicRegistryManager).copy();

            if (newBackpack.getItem() instanceof BackpackItem) {
                NbtList oldTag = centerSlot.getOrCreateNbt().getList("Inventory", NbtElement.COMPOUND_TYPE);
                newBackpack.getOrCreateNbt().put("Inventory", oldTag);
                cir.setReturnValue(newBackpack);
            }
        }
    }
}
