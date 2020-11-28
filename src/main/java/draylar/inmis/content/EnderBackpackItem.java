package draylar.inmis.content;

import draylar.inmis.Inmis;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class EnderBackpackItem extends Item {

    public static final TranslationTextComponent CONTAINER_NAME = new TranslationTextComponent("container.enderchest");

    public EnderBackpackItem() {
        super(new Item.Properties().tab(Inmis.GROUP).stacksTo(1));
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        EnderChestInventory enderChestInventory = player.getEnderChestInventory();

        if(world.isClientSide) {
            world.playSound(player, player.blockPosition(), SoundEvents.ENDER_CHEST_OPEN, SoundCategory.PLAYERS, 1f, 0f);
        }

        if (enderChestInventory != null) {
            if (!world.isClientSide) {
                player.openMenu(new SimpleNamedContainerProvider((i, playerInventory, playerEntity) ->
                        ChestContainer.threeRows(i, playerInventory, enderChestInventory), CONTAINER_NAME));

                player.awardStat(Stats.OPEN_ENDERCHEST);
            }
        }

        return ActionResult.success(player.getItemInHand(hand));
    }
}
