package draylar.inmis.mixin;

import draylar.inmis.Inmis;
import draylar.inmis.api.TrinketCompat;
import draylar.inmis.item.BackpackItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(PlayerEntity.class)
public abstract class PlayerDropMixin extends LivingEntity {

    @Shadow @Final private PlayerInventory inventory;
    @Shadow @Nullable public abstract ItemEntity dropItem(ItemStack stack, boolean throwRandomly, boolean retainOwnership);

    private PlayerDropMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "dropInventory", at = @At("HEAD"))
    private void emptyBackpacks(CallbackInfo ci) {
        if (!this.world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY)) {
            if(Inmis.CONFIG.spillArmorBackpacksOnDeath) {
                spillInventory(inventory.armor);

                // Trinkets
                if(Inmis.TRINKETS_LOADED) {
                    TrinketCompat.spillTrinketInventory((PlayerEntity) (Object) this);
                }
            }

            if(Inmis.CONFIG.spillMainBackpacksOnDeath) {
                spillInventory(inventory.main);
                spillInventory(inventory.offHand);
            }
        }
    }

    @Unique
    private void spillInventory(List<ItemStack> items) {
        for (int i = 0; i < items.size(); i++) {
            ItemStack stack = items.get(i);
            if(stack.getItem() instanceof BackpackItem) {
                // Empty contents, wipe tag, drop backpack
                Inmis.getBackpackContents(stack).forEach(backpackItem -> dropItem(backpackItem, true, false));

                // Wipe backpack
                Inmis.wipeBackpack(stack);

                // Drop backpack stack
                dropItem(stack, true, false);

                // Remove from armor inventory
                items.set(i, ItemStack.EMPTY);
            }
        }
    }
}
