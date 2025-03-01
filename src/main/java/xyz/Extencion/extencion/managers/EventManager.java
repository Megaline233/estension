package xyz.Extencion.extencion.managers;

import net.minecraft.client.gui.screen.ChatScreen;
import  xyz.Extencion.extencion.Extencion;
import  xyz.Extencion.extencion.event.EventBus;
import  xyz.Extencion.extencion.event.EventTarget;
import  xyz.Extencion.extencion.event.impl.EventKey;
import  xyz.Extencion.extencion.event.impl.EventMouse;
import  xyz.Extencion.extencion.event.impl.EventTick;
import  xyz.Extencion.extencion.modules.api.Feature;

import static  xyz.Extencion.extencion.QuickImports.mc;

public class EventManager extends Feature {

    public static boolean hold_mouse0;

    public void init() {
        EventBus.register(this);
    }

    public void onUnload() {
        EventBus.unregister(this);
    }

    @EventTarget
    public void onTick(EventTick e) {
        if (!fullNullCheck()) {
            Extencion.getInstance().getModuleManager().onUpdate();
            Extencion.getInstance().getModuleManager().sortModules(true);
            Extencion.getInstance().getModuleManager().onTick();
        }
    }

    @EventTarget
    public void onKeyInput(EventKey e) {
        if (!(mc.currentScreen instanceof ChatScreen)) {
            Extencion.getInstance().getModuleManager().onKeyPressed(e.getKey());
        }
    }

    @EventTarget
    public void onMouse(EventMouse e) {
        if (e.getAction() == 0) hold_mouse0 = false;
        if (e.getAction() == 1) hold_mouse0 = true;
    }
}