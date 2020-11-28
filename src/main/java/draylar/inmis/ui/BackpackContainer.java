package draylar.inmis.ui;

import draylar.inmis.Inmis;
import draylar.inmis.data.BackpackInfo;
import draylar.inmis.content.BackpackItem;
import draylar.inmis.ui.api.Dimension;
import draylar.inmis.ui.api.Point;
import draylar.inmis.util.InventoryUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Hand;

public class BackpackContainer extends Container {

    public static final int BACKPACK_INVENTORY = 1;
    private PlayerEntity player;
    private Hand hand;
    private ItemStack backpackStack;
    int padding = 8;
    int titleSpace = 10;
    /*
    public BackpackContainer(int synchronizationID, PlayerInventory playerInventory, PacketBuffer packetByteBuf) {
        this(synchronizationID, playerInventory, packetByteBuf.readEnum(Hand.class));
    }*/
    
    public BackpackContainer(int synchronizationID, PlayerInventory playerInventory, Hand hand) {
        super(Inmis.CONTAINER_TYPE, synchronizationID);
        this.player = playerInventory.player;
        this.hand = hand;
        ItemStack backpackStack = player.getItemInHand(hand);
        
        if (backpackStack.getItem() instanceof BackpackItem) {
            setupContainer(playerInventory, backpackStack);
        }
    }
    
    private void setupContainer(PlayerInventory playerInventory, ItemStack backpackStack) {
        Dimension dimension = getDimension();
        BackpackInfo tier = getItem().getTier();
        int rowWidth = tier.getRowWidth();
        int numberOfRows = tier.getNumberOfRows();

        ListNBT tag = backpackStack.getOrCreateTag().getList("Inventory", 10);
        Inventory inventory = new Inventory(rowWidth * numberOfRows) {

            @Override
            public void setChanged() {
                backpackStack.getOrCreateTag().put("Inventory", InventoryUtils.toTag(this));
                super.setChanged();
            }
        };

        InventoryUtils.fromTag(tag, inventory);

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
        return (BackpackItem) player.getItemInHand(hand).getItem();
    }
    
    public Dimension getDimension() {
        BackpackInfo tier = getItem().getTier();
        return new Dimension(padding * 2 + Math.max(tier.getRowWidth(), 9) * 18, padding * 2 + titleSpace * 2 + 8 + (tier.getNumberOfRows() + 4) * 18);
    }
    
    public Point getBackpackSlotPosition(Dimension dimension, int x, int y) {
        BackpackInfo tier = getItem().getTier();
        return new Point(dimension.width / 2 - tier.getRowWidth() * 9 + x * 18, padding + titleSpace + y * 18);
    }
    
    public Point getPlayerInvSlotPosition(Dimension dimension, int x, int y) {
        BackpackInfo tier = getItem().getTier();
        return new Point(dimension.width / 2 - 9 * 9 + x * 18, dimension.height - padding - 4 * 18 - 3 + y * 18 + (y == 3 ? 4 : 0));
    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        ItemStack stackInHand = player.getItemInHand(this.hand);
        return stackInHand.getItem() instanceof BackpackItem;
    }
    
    @Override
    public ItemStack quickMoveStack(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack toInsert = slot.getItem();
            itemStack = toInsert.copy();
            BackpackInfo tier = getItem().getTier();
            if (index < tier.getNumberOfRows() * tier.getRowWidth()) {
                if (!this.moveItemStackTo(toInsert, tier.getNumberOfRows() * tier.getRowWidth(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(toInsert, 0, tier.getNumberOfRows() * tier.getRowWidth(), false)) {
                return ItemStack.EMPTY;
            }
            
            if (toInsert.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        
        return itemStack;
    }

    private class BackpackLockedSlot extends Slot {

        public BackpackLockedSlot(IInventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public boolean mayPickup(PlayerEntity player) {
            return !(getItem().getItem() instanceof BackpackItem) && getItem() != player.getItemInHand(hand);
        }
        
        @Override
        public boolean mayPlace(ItemStack stack) {
            return !(stack.getItem() instanceof BackpackItem) && stack != player.getItemInHand(hand);
        }
    }
}
