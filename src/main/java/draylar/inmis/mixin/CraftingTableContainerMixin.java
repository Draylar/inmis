package draylar.inmis.mixin;

import draylar.inmis.content.BackpackItem;
import io.github.cottonmc.component.UniversalComponents;
import net.minecraft.container.CraftingTableContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(CraftingTableContainer.class)
public class CraftingTableContainerMixin {

    @Redirect(
            method = "updateResult",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/recipe/CraftingRecipe;craft(Lnet/minecraft/inventory/Inventory;)Lnet/minecraft/item/ItemStack;"
            )
    )
    private static ItemStack onCraft(CraftingRecipe craftingRecipe, Inventory inv, int syncId, World world, PlayerEntity player, CraftingInventory craftingInventory, CraftingResultInventory resultInventory) {
        ItemStack resultStack = craftingRecipe.craft(craftingInventory);

        if(resultStack.getItem() instanceof BackpackItem) {
            ItemStack inventoryCenterStack = craftingInventory.getInvStack(4);

            if(inventoryCenterStack.getItem() instanceof BackpackItem) {
                resultStack.setTag(inventoryCenterStack.getTag());

                CompoundTag itemTag = new CompoundTag();
                UniversalComponents.INVENTORY_COMPONENT.get(inventoryCenterStack).toTag(itemTag);
                UniversalComponents.INVENTORY_COMPONENT.get(resultStack).fromTag(itemTag);
            }
        }

        return resultStack;
    }
}
