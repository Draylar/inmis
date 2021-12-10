package draylar.inmis.ui;

import com.mojang.blaze3d.systems.RenderSystem;
import draylar.inmis.api.Dimension;
import draylar.inmis.api.Rectangle;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;

public class BackpackHandledScreen extends HandledScreen<BackpackScreenHandler> {

    private static final Identifier GUI_TEXTURE = new Identifier("inmis", "textures/gui/backpack_container.png");
    private final static int TOP_OFFSET = 24;
    private final static int SLOT_SIZE = 18;
    private final static int WIDTH_PADDING = 14;
    private final static int INVENTORY_LABEL_EXTRA = 8;
    
    public BackpackHandledScreen(BackpackScreenHandler handler, PlayerInventory player, Text title) {
        super(handler, player, title);
        
        Dimension dimension = handler.getDimension();
        this.backgroundWidth = dimension.getWidth();
        this.backgroundHeight = dimension.getHeight();
        this.titleY = 7;
        this.playerInventoryTitleX = handler.getPlayerInvSlotPosition(dimension, 0, 0).x;
        this.playerInventoryTitleY = this.backgroundHeight - 94;
    }
    
    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.clearColor(1.0F, 1.0F, 1.0F, 1.0F);
        int x = (this.width - this.backgroundWidth) / 2;
        int y = (this.height - this.backgroundHeight) / 2;
        renderBackgroundTexture(matrices, new Rectangle(x, y, backgroundWidth, backgroundHeight), delta, 0xFFFFFFFF);
        RenderSystem.setShaderTexture(0, new Identifier("textures/gui/container/hopper.png"));
        for (Slot slot : getScreenHandler().slots) {
            this.drawTexture(matrices, x + slot.x - 1, y + slot.y - 1, 43, 19, 18, 18);
        }
    }
    
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }
    
    public void renderBackgroundTexture(MatrixStack matrices, Rectangle bounds, float delta, int color) {
        float alpha = ((color >> 24) & 0xFF) / 255f;
        float red = ((color >> 16) & 0xFF) / 255f;
        float green = ((color >> 8) & 0xFF) / 255f;
        float blue = (color & 0xFF) / 255f;
        RenderSystem.clearColor(red, green, blue, alpha);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        int x = bounds.x, y = bounds.y, width = bounds.width, height = bounds.height;
        int xTextureOffset = 0;
        int yTextureOffset = 66;
        
        // 9 Patch Texture
        
        // Four Corners
        this.drawTexture(matrices, x, y, 106 + xTextureOffset, 124 + yTextureOffset, 8, 8);
        this.drawTexture(matrices, x + width - 8, y, 248 + xTextureOffset, 124 + yTextureOffset, 8, 8);
        this.drawTexture(matrices, x, y + height - 8, 106 + xTextureOffset, 182 + yTextureOffset, 8, 8);
        this.drawTexture(matrices, x + width - 8, y + height - 8, 248 + xTextureOffset, 182 + yTextureOffset, 8, 8);
        
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        // Sides
        drawTexturedQuad(matrix, x + 8, x + width - 8, y, y + 8, getZOffset(), (114 + xTextureOffset) / 256f, (248 + xTextureOffset) / 256f, (124 + yTextureOffset) / 256f, (132 + yTextureOffset) / 256f);
        drawTexturedQuad(matrix, x + 8, x + width - 8, y + height - 8, y + height, getZOffset(), (114 + xTextureOffset) / 256f, (248 + xTextureOffset) / 256f, (182 + yTextureOffset) / 256f, (190 + yTextureOffset) / 256f);
        drawTexturedQuad(matrix, x, x + 8, y + 8, y + height - 8, getZOffset(), (106 + xTextureOffset) / 256f, (114 + xTextureOffset) / 256f, (132 + yTextureOffset) / 256f, (182 + yTextureOffset) / 256f);
        drawTexturedQuad(matrix, x + width - 8, x + width, y + 8, y + height - 8, getZOffset(), (248 + xTextureOffset) / 256f, (256 + xTextureOffset) / 256f, (132 + yTextureOffset) / 256f, (182 + yTextureOffset) / 256f);
        
        // Center
        drawTexturedQuad(matrix, x + 8, x + width - 8, y + 8, y + height - 8, getZOffset(), (114 + xTextureOffset) / 256f, (248 + xTextureOffset) / 256f, (132 + yTextureOffset) / 256f, (182 + yTextureOffset) / 256f);
    }
    
    private static void drawTexturedQuad(Matrix4f matrices, int x0, int x1, int y0, int y1, int z, float u0, float u1, float v0, float v1) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        bufferBuilder.vertex(matrices, (float)x0, (float)y1, (float)z).texture(u0, v1).next();
        bufferBuilder.vertex(matrices, (float)x1, (float)y1, (float)z).texture(u1, v1).next();
        bufferBuilder.vertex(matrices, (float)x1, (float)y0, (float)z).texture(u1, v0).next();
        bufferBuilder.vertex(matrices, (float)x0, (float)y0, (float)z).texture(u0, v0).next();
        bufferBuilder.end();
        BufferRenderer.draw(bufferBuilder);
    }
}
