package xyz.Extencion.extencion.mixins;

import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.Extencion.extencion.Extencion;
import xyz.Extencion.extencion.event.EventBus;
import xyz.Extencion.extencion.event.impl.EventMouse;

import static xyz.Extencion.extencion.QuickImports.mc;

@Mixin(Mouse.class)
public class MouseMixin {
    @Inject(method = "onMouseButton", at = @At("HEAD"))
    public void onMouseButtonHook(long window, int button, int action, int mods, CallbackInfo ci) {
        if (window == mc.getWindow().getHandle()) {
            if (action == 0) Extencion.getInstance().getModuleManager().onMoseKeyReleased(button);
            EventBus.call(new EventMouse(button, action));
        }
    }
}