package draylar.inmis.item;

import draylar.inmis.Inmis;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class EnderBackpackItem extends Item {

    public static final Text CONTAINER_NAME = Text.translatable("container.enderchest");

    public EnderBackpackItem() {
        super(new Item.Settings().maxCount(1));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        EnderChestInventory enderChestInventory = player.getEnderChestInventory();

        if (Inmis.CONFIG.playSound) {
            if (world.isClient) {
                world.playSound(player, player.getBlockPos(), SoundEvents.BLOCK_ENDER_CHEST_OPEN, SoundCategory.PLAYERS, 1, 1);
            }
        }

        if (enderChestInventory != null) {
            if (!world.isClient) {
                player.openHandledScreen(new SimpleNamedScreenHandlerFactory(
                        (i, playerInventory, playerEntity) -> GenericContainerScreenHandler.createGeneric9x3(i, playerInventory, enderChestInventory), CONTAINER_NAME));

                player.incrementStat(Stats.OPEN_ENDERCHEST);
            }
        }

        return TypedActionResult.success(player.getStackInHand(hand));
    }
}
