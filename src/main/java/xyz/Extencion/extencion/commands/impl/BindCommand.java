package xyz.Extencion.extencion.commands.impl;

import xyz.Extencion.extencion.commands.Command;
import  xyz.Extencion.extencion.modules.api.Module;
import  xyz.Extencion.extencion.Extencion;

public class BindCommand extends Command {

    public static void execute(String[] args) {
        if (Module.fullNullCheck()) return;
        Module module = Extencion.getInstance().getModuleManager().getModuleByName(args[1]);
        if (args.length < 3) {
            sendMessage("§cИспользование: .bind <модуль> <клавиша>");
            return;
        }

        if (module == null) {
            sendMessage("§cМодуль §f" + args[1] + " §cне найден!");
            return;
        }

        try {
            // Преобразуем имя клавиши в верхний регистр и добавляем префикс GLFW
            String glfwKeyName = "GLFW_KEY_" + args[2].toUpperCase();
            int keyCode = org.lwjgl.glfw.GLFW.class.getField(glfwKeyName).getInt(null);
            module.setBind(keyCode);
            sendMessage("§aУстановлен бинд §f" + args[2].toUpperCase() + "§a для модуля §f" + module.getName());
        } catch (Exception e) {
            sendMessage("§cНеверная клавиша! Используйте английские буквы (например: R, F, X)");
        }
    }
}