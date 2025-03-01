package xyz.Extencion.extencion.mixins;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.InputUtil;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.Extencion.extencion.Extencion;
import xyz.Extencion.extencion.managers.EventManager;
import xyz.Extencion.extencion.modules.api.Module;
import xyz.Extencion.extencion.modules.impl.misc.ItemScroller;
import xyz.Extencion.extencion.utils.math.TimerUtil;

import static xyz.Extencion.extencion.QuickImports.mc;

@Mixin(HandledScreen.class)
public abstract class HandledScreenMixin {

    @Unique
    private final TimerUtil timer = new TimerUtil();

    @Shadow
    protected abstract boolean isPointOverSlot(Slot slotIn, double mouseX, double mouseY);

    @Shadow
    protected abstract void onMouseClick(Slot slotIn, int slotId, int mouseButton, SlotActionType type);

    @Inject(method = "render", at = @At("HEAD"))
    private void drawScreenHook(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (Module.fullNullCheck()) return;
        for (int i1 = 0; i1 < mc.player.currentScreenHandler.slots.size(); ++i1) {
            Slot slot = mc.player.currentScreenHandler.slots.get(i1);
            if (isPointOverSlot(slot, mouseX, mouseY) && slot.isEnabled()) {
                if (Extencion.getInstance().getModuleManager().getModuleByClass(ItemScroller.class).isEnabled() && shit() && attack() && timer.passedMs(Extencion.getInstance().getModuleManager().getModuleByClass(ItemScroller.class).delay.getValue())) {
                    this.onMouseClick(slot, slot.id, 0, SlotActionType.QUICK_MOVE);
                    timer.reset();
                }
            }
        }
    }

    private boolean shit() {
        return InputUtil.isKeyPressed(mc.getWindow().getHandle(), 340) || InputUtil.isKeyPressed(mc.getWindow().getHandle(), 344);
    }

    private boolean attack() {
        return EventManager.hold_mouse0;
    }
}