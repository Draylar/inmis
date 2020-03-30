package draylar.inmis.ui;

import draylar.inmis.Inmis;
import draylar.inmis.content.BackpackItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import spinnery.common.BaseContainerScreen;
import spinnery.widget.*;
import spinnery.widget.api.Position;
import spinnery.widget.api.Size;

public class BackpackContainerScreen extends BaseContainerScreen<BackpackContainer> {

    private final static int TOP_OFFSET = 24;
    private final static int SLOT_SIZE = 18;
    private final static int WIDTH_PADDING = 14;
    private final static int INVENTORY_LABEL_EXTRA = 8;

    public BackpackContainerScreen(BackpackContainer linkedContainer, PlayerEntity player) {
        super(new LiteralText("Yeetus"), linkedContainer, player);
        ItemStack heldStack = player.getStackInHand(player.getActiveHand());

        if(heldStack.getItem() instanceof BackpackItem) {
            BackpackItem backpackItem = (BackpackItem) heldStack.getItem();

            int containerWidth = Math.max(9, backpackItem.getTier().getRowWidth()) * SLOT_SIZE + WIDTH_PADDING;

            WInterface mainInterface = getInterface().setBlurred(true).setTheme(Inmis.id("backpack"));
            WPanel mainPanel = mainInterface.createChild(WPanel.class, Position.of(0, 0, 0), Size.of(containerWidth, INVENTORY_LABEL_EXTRA + backpackItem.getTier().getNumberOfRows() * 18 + 108));
            mainPanel.center();
            mainPanel.setOnAlign(WAbstractWidget::center);

            mainPanel.createChild(WStaticText.class).setText(new TranslatableText("item.inmis." + backpackItem.getTier().getName() + "_backpack")).setPosition(Position.of(mainPanel, 8, 6, 0));

            WSlot.addArray(Position.of(mainPanel, containerWidth / 2 - (SLOT_SIZE * backpackItem.getTier().getRowWidth()) / 2, 18, 1), Size.of(SLOT_SIZE, SLOT_SIZE), mainInterface, 0, BackpackContainer.BACKPACK_INVENTORY, backpackItem.getTier().getRowWidth(), backpackItem.getTier().getNumberOfRows());
            WSlot.addPlayerInventory(Position.of(mainPanel, containerWidth / 2 - (SLOT_SIZE * 9) / 2, INVENTORY_LABEL_EXTRA + backpackItem.getTier().getNumberOfRows() * SLOT_SIZE + TOP_OFFSET, 1), Size.of(18, 18), mainInterface);
            mainPanel.createChild(WStaticText.class).setText(new TranslatableText("key.categories.inventory")).setPosition(Position.of(mainPanel, containerWidth / 2 - (SLOT_SIZE * 9) / 2, backpackItem.getTier().getNumberOfRows() * SLOT_SIZE + TOP_OFFSET - 4));
        }
    }
}
