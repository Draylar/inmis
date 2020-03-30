package draylar.inmis.content;

import draylar.inmis.Inmis;
import draylar.inmis.config.BackpackInfo;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class BackpackItem extends Item {

    private final BackpackInfo backpack;

    public BackpackItem(BackpackInfo backpack) {
        super(new Item.Settings().group(Inmis.GROUP).maxCount(1));
        this.backpack = backpack;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.setCurrentHand(hand);

        if(world != null && !world.isClient) {
            ContainerProviderRegistry.INSTANCE.openContainer(Inmis.CONTAINER_ID, user, buf -> { });
        }

        return TypedActionResult.success(user.getStackInHand(hand));
    }

    public BackpackInfo getTier() {
        return backpack;
    }
}
