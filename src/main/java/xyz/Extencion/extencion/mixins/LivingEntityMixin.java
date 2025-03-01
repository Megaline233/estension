package xyz.Extencion.extencion.mixins;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.Extencion.extencion.event.EventBus;
import xyz.Extencion.extencion.event.impl.EventPushPlayer;

@SuppressWarnings("all")
@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "isPushable", at = @At("HEAD"), cancellable = true)
    public void isPushable(CallbackInfoReturnable<Boolean> infoReturnable) {
        EventPushPlayer eventPushPlayer = new EventPushPlayer();
        EventBus.call(eventPushPlayer);

        if ((Object) this instanceof ClientPlayerEntity && eventPushPlayer.isCancelled()) {
            infoReturnable.setReturnValue(false);
        }
    }
}
