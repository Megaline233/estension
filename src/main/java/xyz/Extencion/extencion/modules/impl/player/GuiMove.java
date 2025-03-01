package xyz.Extencion.extencion.modules.impl.player;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import  xyz.Extencion.extencion.event.EventTarget;
import  xyz.Extencion.extencion.event.impl.EventTick;
import  xyz.Extencion.extencion.modules.api.Category;
import  xyz.Extencion.extencion.modules.api.Module;
import net.minecraft.client.gui.screen.ingame.*;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.gui.screen.ChatScreen;
import  xyz.Extencion.extencion.utils.movement.MovingUtil;

public class GuiMove extends Module {

    public GuiMove() {
        super("GuiMove", "Ходить с открытым инвом типо можно", Category.Player, true, false, false);
    }

    @EventTarget
    public void onTick(EventTick e) {
        GameOptions gameOptions = mc.options;

        if (shouldSkipExecution()) {
            return;
        }

        for (KeyBinding keyBinding : MovingUtil.getMovementKeys(false)) {
            long handle = mc.getWindow().getHandle();
            int keyCode = keyBinding.getDefaultKey().getCode();
            keyBinding.setPressed(InputUtil.isKeyPressed(handle, keyCode));
        }
    }

    public boolean shouldSkipExecution() {
        return mc.currentScreen == null
                || mc.currentScreen instanceof ChatScreen
                || mc.currentScreen instanceof SignEditScreen
                || mc.currentScreen instanceof AnvilScreen
                || mc.currentScreen instanceof AbstractCommandBlockScreen
                || mc.currentScreen instanceof StructureBlockScreen;
    }
}