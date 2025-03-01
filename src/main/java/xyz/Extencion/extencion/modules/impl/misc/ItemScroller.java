package xyz.Extencion.extencion.modules.impl.misc;

import net.minecraft.item.Item;
import net.minecraft.screen.slot.SlotActionType;
import org.lwjgl.glfw.GLFW;
import  xyz.Extencion.extencion.event.EventTarget;
import  xyz.Extencion.extencion.modules.api.Module;
import  xyz.Extencion.extencion.modules.api.Category;
import  xyz.Extencion.extencion.event.impl.EventClickSlot;
import xyz.Extencion.extencion.modules.settings.Setting;

public class ItemScroller extends Module {

    public ItemScroller() {
        super("ItemScroller", "Позволяет быстро перемещать предметы по слотам", Category.Misc, false, false, false);
    }

    public Setting<Integer> delay = register(new Setting<>("Delay", 0, 0, 500));

    private boolean pauseListening = false;

    @EventTarget
    public void onClick(EventClickSlot e) {
        if ((isKeyPressed(GLFW.GLFW_KEY_LEFT_SHIFT)
                || isKeyPressed(GLFW.GLFW_KEY_RIGHT_SHIFT))
                && (isKeyPressed(GLFW.GLFW_KEY_LEFT_CONTROL) || isKeyPressed(GLFW.GLFW_KEY_RIGHT_CONTROL))
                && e.getSlotActionType() == SlotActionType.THROW
                && !pauseListening) {
            if (mc.player == null || mc.interactionManager == null) return;
            Item copy = mc.player.currentScreenHandler.slots.get(e.getSlot()).getStack().getItem();
            pauseListening = true;
            for (int i2 = 0; i2 < mc.player.currentScreenHandler.slots.size(); ++i2) {
                if (mc.player.currentScreenHandler.slots.get(i2).getStack().getItem() == copy) {
                    mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, i2, 1, SlotActionType.THROW, mc.player);
                }
            }
            pauseListening = false;
        }
    }
}
