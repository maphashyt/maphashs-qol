package pl.maphash.mixin;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pl.maphash.config.ModConfig;

@Mixin(AbstractClientPlayerEntity.class)
public class FovMixin {
    @Inject(method = "getFovMultiplier", at = @At("HEAD"), cancellable = true)
    private void onGetFovMultiplier(boolean firstPerson, float fovEffectScale, CallbackInfoReturnable<Float> cir) {
        if (ModConfig.INSTANCE.noFovChange) {
            cir.setReturnValue(1.0F);
        }
    }
}