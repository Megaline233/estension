package xyz.Extencion.extencion.modules.impl.misc;

import  xyz.Extencion.extencion.mixins.accesors.IMinecraftClient;
import  xyz.Extencion.extencion.event.EventTarget;
import  xyz.Extencion.extencion.event.impl.EventTick;
import  xyz.Extencion.extencion.modules.api.Category;
import  xyz.Extencion.extencion.modules.api.Module;

public class FastUse extends Module {

    public FastUse() {
        super("FastUse", "Убирает кулдаун на действие", Category.Misc, true, false, false);
    }

    @EventTarget
    public void onTick(EventTick e) {
        ((IMinecraftClient) mc).setUseCooldown(0);
    }
}
