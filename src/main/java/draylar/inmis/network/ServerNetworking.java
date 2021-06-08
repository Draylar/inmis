package draylar.inmis.network;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import draylar.inmis.Inmis;
import draylar.inmis.item.BackpackItem;
import draylar.inmis.mixin.trinkets.TrinketsMixinPlugin;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class ServerNetworking {

    public static Identifier OPEN_BACKPACK = Inmis.id("open_backpack");

    public static void init() {
        registerOpenBackpackPacketHandler();
    }

    private static void registerOpenBackpackPacketHandler() {
        ServerPlayNetworking.registerGlobalReceiver(OPEN_BACKPACK, ServerNetworking::receiveOpenBackpackPacket);
    }

    private static void receiveOpenBackpackPacket(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        if (TrinketsMixinPlugin.isTrinketsLoaded) {
            Optional<TrinketComponent> component = TrinketsApi.getTrinketComponent(player);

            // Iterate over the player's Trinket inventory.
            // Once a backpack has been found, open it.
            // TODO: HOW DOES THIS WORK WHEN TRINKETS IS NOT INSTALLED???
            if(component.isPresent()) {
                List<Pair<SlotReference, ItemStack>> allEquipped = component.get().getAllEquipped();
                for(Pair<SlotReference, ItemStack> entry : allEquipped) {
                    if(entry.getRight().getItem() instanceof BackpackItem) {
                        BackpackItem.openScreen(player, entry.getRight());
                        return;
                    }
                }
            }
        }

        ItemStack firstBackpackItemStack = Stream.concat(player.getInventory().offHand.stream(), player.getInventory().main.stream())
                .filter((itemStack) -> itemStack.getItem() instanceof BackpackItem)
                .findFirst()
                .orElse(ItemStack.EMPTY);
        if (firstBackpackItemStack != ItemStack.EMPTY) {
            BackpackItem.openScreen(player, firstBackpackItemStack);
        }
    }
}
