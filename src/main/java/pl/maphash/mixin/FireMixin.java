package pl.maphash.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.texture.AtlasManager;
import net.minecraft.client.render.command.FireCommandRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.maphash.config.ModConfig;

@Mixin(FireCommandRenderer.class)
public class FireMixin {
    @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack$Entry;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/render/entity/state/EntityRenderState;Lorg/joml/Quaternionf;Lnet/minecraft/client/texture/AtlasManager;)V", at = @At("HEAD"))
    private void onRenderFire(MatrixStack.Entry matricesEntry, VertexConsumerProvider vertexConsumers, EntityRenderState renderState, Quaternionf rotation, AtlasManager atlasManager, CallbackInfo ci) {
        if (ModConfig.INSTANCE.lowFire) {
            matricesEntry.translate(0.0F, -0.4F, 0.0F);
        }
    }
}