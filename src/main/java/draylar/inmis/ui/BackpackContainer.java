package draylar.inmis.ui;

import draylar.inmis.content.BackpackItem;
import io.github.cottonmc.component.UniversalComponents;
import io.github.cottonmc.component.item.InventoryComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import spinnery.common.container.BaseContainer;
import spinnery.widget.WAbstractWidget;
import spinnery.widget.WInterface;
import spinnery.widget.WSlot;
import spinnery.widget.api.Action;

public class BackpackContainer extends BaseContainer {

    public static final int BACKPACK_INVENTORY = 1;

    public BackpackContainer(int synchronizationID, Identifier identifier, PlayerEntity player, PacketByteBuf packetByteBuf) {
        super(synchronizationID, player.inventory);
        ItemStack backpackStack = player.getStackInHand(player.getActiveHand());

        if(backpackStack.getItem() instanceof BackpackItem) {
            BackpackItem backpackItem = (BackpackItem) backpackStack.getItem();
            setupInventory(backpackStack);
            WInterface mainInterface = setupContainer(backpackItem);
            lockBackpackSlots(mainInterface);
        } else {
            this.close(player);
        }
    }

    private void setupInventory(ItemStack backpackStack) {
        InventoryComponent component = UniversalComponents.INVENTORY_COMPONENT.get(backpackStack);
        getInventories().put(BACKPACK_INVENTORY, component.asInventory());
    }

    private WInterface setupContainer(BackpackItem backpackItem) {
        WInterface mainInterface = getInterface();
        WSlot.addHeadlessArray(mainInterface, 0, BACKPACK_INVENTORY, backpackItem.getTier().getRowWidth(), backpackItem.getTier().getNumberOfRows());
        WSlot.addHeadlessPlayerInventory(mainInterface);
        return mainInterface;
    }

    private void lockBackpackSlots(WInterface mainInterface) {
        for (WAbstractWidget widget : mainInterface.getWidgets()) {
            if (widget instanceof WSlot) {
                WSlot slot = (WSlot) widget;

                if(slot.getStack().getItem() instanceof BackpackItem) {
                    ((WSlot) widget).setLocked(true);
                }
            }
        }
    }

    @Override
    public void onSlotAction(int slotNumber, int inventoryNumber, int button, Action action, PlayerEntity player) {
        for (WAbstractWidget widget : this.serverInterface.getAllWidgets()) {
            if (widget instanceof WSlot && ((WSlot) widget).getSlotNumber() == slotNumber && ((WSlot) widget).getInventoryNumber() == inventoryNumber) {
                if (!(((WSlot) widget).getStack().getItem() instanceof BackpackItem)) {
                    super.onSlotAction(slotNumber, inventoryNumber, button, action, player);
                }
            }
        }
    }
}
