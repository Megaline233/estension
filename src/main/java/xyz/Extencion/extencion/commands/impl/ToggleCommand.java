package xyz.Extencion.extencion.commands.impl;

import  xyz.Extencion.extencion.Extencion;
import xyz.Extencion.extencion.commands.Command;
import  xyz.Extencion.extencion.modules.api.Module;

public class ToggleCommand extends Command {

    public static void execute(String[] args) {
        if (args.length < 2) {
            sendMessage("§cИспользование: .toggle <модуль>");
            return;
        }

        Module module = Extencion.getInstance().getModuleManager().getModuleByName(args[1]);
        if (module != null) {
            module.toggle();
            sendMessage("§7" + module.getName() + " §r" + (module.isEnabled() ? "§aвключен" : "§cвыключен"));
        } else {
            sendMessage("§cМодуль §f" + args[1] + " §cне найден!");
        }
    }
}
