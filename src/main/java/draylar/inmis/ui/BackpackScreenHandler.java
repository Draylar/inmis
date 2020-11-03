package draylar.inmis.ui;

import draylar.inmis.Inmis;
import draylar.inmis.config.BackpackInfo;
import draylar.inmis.content.BackpackItem;
import io.github.cottonmc.component.UniversalComponents;
import io.github.cottonmc.component.item.InventoryComponent;
import me.shedaniel.math.Dimension;
import me.shedaniel.math.Point;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Hand;

public class BackpackScreenHandler extends ScreenHandler {
    public static final int BACKPACK_INVENTORY = 1;
    private PlayerEntity player;
    private Hand hand;
    int padding = 8;
    int titleSpace = 10;
    
    public BackpackScreenHandler(int synchronizationID, PlayerInventory playerInventory, PacketByteBuf packetByteBuf) {
        this(synchronizationID, playerInventory, packetByteBuf.readEnumConstant(Hand.class));
    }
    
    public BackpackScreenHandler(int synchronizationID, PlayerInventory playerInventory, Hand hand) {
        super(Inmis.CONTAINER_TYPE, synchronizationID);
        this.player = playerInventory.player;
        this.hand = hand;
        ItemStack backpackStack = player.getStackInHand(hand);
        
        if (backpackStack.getItem() instanceof BackpackItem) {
            setupContainer(playerInventory, backpackStack);
        } else {
            this.close(player);
        }
    }
    
    private void setupContainer(PlayerInventory playerInventory, ItemStack backpackStack) {
        InventoryComponent component = UniversalComponents.INVENTORY_COMPONENT.get(backpackStack);
        Inventory inventory = component.asInventory();
        Dimension dimension = getDimension();
        BackpackInfo tier = getItem().getTier();
        int rowWidth = tier.getRowWidth();
        int numberOfRows = tier.getNumberOfRows();
        for (int y = 0; y < numberOfRows; y++) {
            for (int x = 0; x < rowWidth; x++) {
                Point backpackSlotPosition = getBackpackSlotPosition(dimension, x, y);
                addSlot(new BackpackLockedSlot(inventory, y * rowWidth + x, backpackSlotPosition.x + 1, backpackSlotPosition.y + 1));
            }
        }
        
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 9; ++x) {
                Point playerInvSlotPosition = getPlayerInvSlotPosition(dimension, x, y);
                this.addSlot(new BackpackLockedSlot(playerInventory, x + y * 9 + 9, playerInvSlotPosition.x + 1, playerInvSlotPosition.y + 1));
            }
        }
        
        for (int x = 0; x < 9; ++x) {
            Point playerInvSlotPosition = getPlayerInvSlotPosition(dimension, x, 3);
            this.addSlot(new BackpackLockedSlot(playerInventory, x, playerInvSlotPosition.x + 1, playerInvSlotPosition.y + 1));
        }
    }
    
    public BackpackItem getItem() {
        return (BackpackItem) player.getStackInHand(hand).getItem();
    }
    
    public Dimension getDimension() {
        BackpackInfo tier = getItem().getTier();
        return new Dimension(padding * 2 + Math.max(tier.getRowWidth(), 9) * 18, padding * 2 + titleSpace * 2 + 8 + (tier.getNumberOfRows() + 4) * 18);
    }
    
    public Point getBackpackSlotPosition(Dimension dimension, int x, int y) {
        BackpackInfo tier = getItem().getTier();
        return new Point(dimension.getWidth() / 2 - tier.getRowWidth() * 9 + x * 18, padding + titleSpace + y * 18);
    }
    
    public Point getPlayerInvSlotPosition(Dimension dimension, int x, int y) {
        BackpackInfo tier = getItem().getTier();
        return new Point(dimension.getWidth() / 2 - 9 * 9 + x * 18, dimension.getHeight() - padding - 4 * 18 - 3 + y * 18 + (y == 3 ? 4 : 0));
    }
    
    @Override
    public boolean canUse(PlayerEntity player) {
        ItemStack stackInHand = player.getStackInHand(this.hand);
        return stackInHand.getItem() instanceof BackpackItem;
    }
    
    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasStack()) {
            ItemStack toInsert = slot.getStack();
            itemStack = toInsert.copy();
            BackpackInfo tier = getItem().getTier();
            if (index < tier.getNumberOfRows() * tier.getRowWidth()) {
                if (!this.insertItem(toInsert, tier.getNumberOfRows() * tier.getRowWidth(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(toInsert, 0, tier.getNumberOfRows() * tier.getRowWidth(), false)) {
                return ItemStack.EMPTY;
            }
            
            if (toInsert.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }
        
        return itemStack;
    }
    
    private class BackpackLockedSlot extends Slot {
        public BackpackLockedSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }
        
        @Override
        public boolean canTakeItems(PlayerEntity playerEntity) {
            return !(getStack().getItem() instanceof BackpackItem) && getStack() != player.getStackInHand(hand);
        }
        
        @Override
        public boolean canInsert(ItemStack stack) {
            return !(getStack().getItem() instanceof BackpackItem) && getStack() != player.getStackInHand(hand);
        }
    }
}
