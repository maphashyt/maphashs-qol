package pl.maphash.mixin;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.maphash.config.ModConfig;

@Mixin(HeldItemRenderer.class)
public class ShieldMixin {
    @Inject(method = "renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemDisplayContext;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;I)V", at = @At("HEAD"))
    private void onRenderItem(LivingEntity entity, ItemStack stack, net.minecraft.item.ItemDisplayContext renderMode, MatrixStack matrices, net.minecraft.client.render.command.OrderedRenderCommandQueue queue, int light, CallbackInfo ci) {
        if (ModConfig.INSTANCE.lowShield && stack.isOf(Items.SHIELD)) {
            if (renderMode == net.minecraft.item.ItemDisplayContext.FIRST_PERSON_RIGHT_HAND || renderMode == net.minecraft.item.ItemDisplayContext.FIRST_PERSON_LEFT_HAND) {
                matrices.translate(0.0, -0.2, 0.0);
            }
        }
    }
}