package draylar.inmis;

import draylar.inmis.config.BackpackInfo;
import draylar.inmis.config.InmisConfig;
import draylar.inmis.content.BackpackItem;
import draylar.inmis.content.EnderBackpackItem;
import draylar.inmis.ui.BackpackContainer;
import io.github.cottonmc.component.item.InventoryComponent;
import io.github.cottonmc.component.item.impl.ItemInventoryComponent;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import nerdhub.cardinal.components.api.ComponentRegistry;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.event.ItemComponentCallback;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;

public class Inmis implements ModInitializer {

    public static final Identifier CONTAINER_ID = new Identifier("inmis",  "backpack");
    public static final ItemGroup GROUP = FabricItemGroupBuilder.build(CONTAINER_ID, () -> new ItemStack(Registry.ITEM.get(new Identifier("inmis", "frayed_backpack"))));
    public static final Item ENDER_POUCH = Registry.ITEM.add(new Identifier("inmis", "ender_pouch"), new EnderBackpackItem());
    private static final InmisConfig CONFIG = AutoConfig.register(InmisConfig.class, GsonConfigSerializer::new).getConfig();
    public static final ComponentType<InventoryComponent> INVENTORY = ComponentRegistry.INSTANCE.registerIfAbsent(new Identifier("inmis","inventory"), InventoryComponent.class);

    @Override
    public void onInitialize() {
        ContainerProviderRegistry.INSTANCE.registerFactory(CONTAINER_ID, BackpackContainer::new);

        for (BackpackInfo backpack : Inmis.CONFIG.backpacks) {
            Item item = Registry.ITEM.add(new Identifier("inmis", backpack.getName().toLowerCase() + "_backpack"), new BackpackItem(backpack));
            ItemComponentCallback.event(item).register((stack, componentContainer) ->
                    componentContainer.put(INVENTORY, new ItemInventoryComponent(backpack.getNumberOfRows() * backpack.getRowWidth())));
        }

        UseItemCallback.EVENT.register((playerEntity, world, hand) -> {
            if(playerEntity.getStackInHand(hand).getItem() instanceof BackpackItem) {
                ItemStack heldStack = playerEntity.getStackInHand(hand);
                playerEntity.setCurrentHand(hand);

                if(!world.isClient) {
                    ContainerProviderRegistry.INSTANCE.openContainer(Inmis.CONTAINER_ID, playerEntity, buf -> {
                    });
                }

                return TypedActionResult.pass(heldStack);
            }

            return TypedActionResult.pass(playerEntity.getStackInHand(hand));
        });
    }
}
