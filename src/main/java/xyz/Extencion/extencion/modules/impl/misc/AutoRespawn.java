package xyz.Extencion.extencion.modules.impl.misc;

import net.minecraft.client.gui.screen.DeathScreen;
import  xyz.Extencion.extencion.event.EventTarget;
import  xyz.Extencion.extencion.event.impl.EventTick;
import  xyz.Extencion.extencion.modules.api.Category;
import  xyz.Extencion.extencion.modules.api.Module;

public class AutoRespawn extends Module {

    public AutoRespawn() {
        super("AutoRespawn", "Сам респавнит", Category.Misc, true, false, false);
    }

    @EventTarget
    public void onTick(EventTick e) {
        if (mc.currentScreen instanceof DeathScreen) {
            mc.player.requestRespawn();
            mc.setScreen(null);
        }
    }
}
