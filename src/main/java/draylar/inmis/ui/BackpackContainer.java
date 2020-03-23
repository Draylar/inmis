package draylar.inmis.ui;

import draylar.inmis.Inmis;
import draylar.inmis.content.BackpackItem;
import io.github.cottonmc.component.item.InventoryComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;
import spinnery.common.BaseContainer;
import spinnery.common.BaseInventory;
import spinnery.util.InventoryUtilities;
import spinnery.widget.WAbstractWidget;
import spinnery.widget.WInterface;
import spinnery.widget.WSlot;

public class BackpackContainer extends BaseContainer {

    public static final int BACKPACK_INVENTORY = 1;
    private final ItemStack backpackStack;

    public BackpackContainer(int synchronizationID, Identifier identifier, PlayerEntity player, PacketByteBuf packetByteBuf) {
        super(synchronizationID, player.inventory);
        backpackStack = player.getStackInHand(player.getActiveHand());

        if(backpackStack.getItem() instanceof BackpackItem) {
            BackpackItem backpackItem = (BackpackItem) backpackStack.getItem();
            InventoryComponent component = Inmis.INVENTORY.get(backpackStack);

            getInventories().put(BACKPACK_INVENTORY, component.asInventory());

            WInterface mainInterface = getInterface();
            WSlot.addHeadlessArray(mainInterface, 0, BACKPACK_INVENTORY, backpackItem.getTier().getRowWidth(), backpackItem.getTier().getNumberOfRows());
            WSlot.addHeadlessPlayerInventory(mainInterface);

            for (WAbstractWidget widget : mainInterface.getWidgets()) {
                if (widget instanceof WSlot) {
                    WSlot slot = (WSlot) widget;

                    if(slot.getStack().getItem() instanceof BackpackItem) {
                        ((WSlot) widget).setLocked(true);
                    }
                }
            }
        } else {
            this.close(player);
        }
    }

    @Override
    public void close(PlayerEntity player) {
        backpackStack.setTag(InventoryUtilities.write(getInventory(BACKPACK_INVENTORY), new CompoundTag()));
        super.close(player);
    }
}
