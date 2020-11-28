package draylar.inmis.content;

import draylar.inmis.data.BackpackInfo;
import draylar.inmis.ui.BackpackContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class BackpackItem extends Item {

    private final BackpackInfo backpack;

    public BackpackItem(BackpackInfo backpack, Item.Properties settings) {
        super(settings);
        this.backpack = backpack;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.startUsingItem(hand);

        if(world.isClientSide) {
            world.playSound(user, user.blockPosition(), backpack.getSound(), SoundCategory.PLAYERS, 1, 1);
        }

        if(world != null && !world.isClientSide) {
            user.openMenu(new SimpleNamedContainerProvider((sync, inv, player) -> new BackpackContainer(sync, inv, hand), this.getDescription()));
        }

        return ActionResult.success(user.getItemInHand(hand));
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    public BackpackInfo getTier() {
        return backpack;
    }
}
