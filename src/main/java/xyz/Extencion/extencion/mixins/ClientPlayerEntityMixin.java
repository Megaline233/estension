package xyz.Extencion.extencion.mixins;

import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.Extencion.extencion.event.EventBus;
import xyz.Extencion.extencion.event.impl.EventPlayerUpdate;
import xyz.Extencion.extencion.event.impl.EventPushBlock;

import static xyz.Extencion.extencion.QuickImports.mc;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    public void tickHook(CallbackInfo info) {
        if (mc.player == null) return;
        EventBus.call(new EventPlayerUpdate());
    }

    @Inject(method = "pushOutOfBlocks", at = @At("HEAD"), cancellable = true)
    public void pushOutOfBlocks(double x, double z, CallbackInfo callbackInfo) {
        EventPushBlock eventPushBlock = new EventPushBlock();
        EventBus.call(eventPushBlock);
        if (eventPushBlock.isCancelled()) {
            callbackInfo.cancel();
        }
    }
}