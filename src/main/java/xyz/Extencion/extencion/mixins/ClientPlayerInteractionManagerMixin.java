package xyz.Extencion.extencion.mixins;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.Extencion.extencion.event.EventBus;
import xyz.Extencion.extencion.event.impl.EventClickSlot;
import xyz.Extencion.extencion.modules.api.Module;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {
    @Inject(method = "clickSlot", at = @At("HEAD"), cancellable = true)
    public void clickSlotHook(int syncId, int slotId, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo ci) {
        if (Module.fullNullCheck()) return;
        EventClickSlot event = new EventClickSlot(actionType, slotId, button, syncId);
        EventBus.call(event);
        if (event.isCancelled()) ci.cancel();
    }
}