package draylar.inmis.item;

import draylar.inmis.Inmis;
import draylar.inmis.config.BackpackInfo;
import draylar.inmis.ui.BackpackScreenHandler;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BackpackItem extends Item implements FabricItem {

    private final BackpackInfo backpack;

    public BackpackItem(BackpackInfo backpack, Item.Settings settings) {
        super(settings);
        this.backpack = backpack;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        // Only allow the user to open the backpack with right-click if the disableInteractOpening option is false.
        // This option is synced S2C through OmegaConfig, which is why we can bundle client & server calls inside the same config check.
        if(!Inmis.CONFIG.requireArmorTrinketToOpen) {
            user.setCurrentHand(hand);

            // Play an opening sound on the client as long as the config option is set.
            if(Inmis.CONFIG.playSound) {
                if(world.isClient) {
                    world.playSound(user, user.getBlockPos(), Registry.SOUND_EVENT.get(new Identifier(backpack.getOpenSound())), SoundCategory.PLAYERS, 1, 1);
                }
            }

            openScreen(user, user.getStackInHand(hand));
            return TypedActionResult.success(user.getStackInHand(hand));
        }

        return TypedActionResult.pass(user.getStackInHand(hand));
    }

    public static void openScreen(PlayerEntity player, ItemStack backpackItemStack) {
        if(player.world != null && !player.world.isClient) {
            player.openHandledScreen(new ExtendedScreenHandlerFactory() {
                @Override
                public void writeScreenOpeningData(ServerPlayerEntity serverPlayerEntity, PacketByteBuf packetByteBuf) {
                    packetByteBuf.writeItemStack(backpackItemStack);
                }

                @Override
                public Text getDisplayName() {
                    return Text.translatable(backpackItemStack.getItem().getTranslationKey());
                }

                @Override
                public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
                    return new BackpackScreenHandler(syncId, inv, backpackItemStack);
                }
            });
        }
    }

    public BackpackInfo getTier() {
        return backpack;
    }

    @Override
    public boolean allowNbtUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
        return false;
    }
}
