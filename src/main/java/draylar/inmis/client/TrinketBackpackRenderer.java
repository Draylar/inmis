package draylar.inmis.client;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.client.TrinketRenderer;
import draylar.inmis.Inmis;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3f;

public class TrinketBackpackRenderer implements TrinketRenderer {

    @Override
    public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity player, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if(!Inmis.CONFIG.trinketRendering) {
            return;
        }

        matrices.push();

        // Initial transformation
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180));
        matrices.translate(0, -0.2, -0.25);

        // Shifting
        if(player.isSneaking()) {
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(25));
            matrices.translate(0, -0.2, 0);
        }

        MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Mode.FIXED, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, 0);
        matrices.pop();
    }
}
