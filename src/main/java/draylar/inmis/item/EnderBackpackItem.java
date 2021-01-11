package draylar.inmis.item;

import draylar.inmis.Inmis;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.stat.Stats;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class EnderBackpackItem extends Item {

    public static final TranslatableText CONTAINER_NAME = new TranslatableText("container.enderchest");

    public EnderBackpackItem() {
        super(new Item.Settings().group(Inmis.GROUP).maxCount(1));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        EnderChestInventory enderChestInventory = player.getEnderChestInventory();

        if (enderChestInventory != null) {
            if (!world.isClient) {
                player.openHandledScreen(new SimpleNamedScreenHandlerFactory((i, playerInventory, playerEntity) ->
                        GenericContainerScreenHandler.createGeneric9x3(i, playerInventory, enderChestInventory), CONTAINER_NAME));

                player.incrementStat(Stats.OPEN_ENDERCHEST);
            }
        }

        return TypedActionResult.success(player.getStackInHand(hand));
    }
}
