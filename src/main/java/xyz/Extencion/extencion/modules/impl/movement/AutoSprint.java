package xyz.Extencion.extencion.modules.impl.movement;

import  xyz.Extencion.extencion.event.EventTarget;
import  xyz.Extencion.extencion.event.impl.EventTick;
import  xyz.Extencion.extencion.modules.api.Category;
import  xyz.Extencion.extencion.modules.api.Module;

public class AutoSprint extends Module {

    public AutoSprint() {
        super("AutoSprint", "Сам спринтит", Category.Movement, true, false, false);
    }

    @EventTarget
    public void onTick(EventTick event) {
        if (mc.player == null) return;

        if (mc.player.forwardSpeed > 0) {
            mc.player.setSprinting(true);
        }
    }
}