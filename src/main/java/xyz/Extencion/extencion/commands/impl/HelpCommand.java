package xyz.Extencion.extencion.commands.impl;

import xyz.Extencion.extencion.commands.Command;

public class HelpCommand extends Command {

    public static void execute(String[] args) {
        sendMessage("§6Доступные команды:");
        sendMessage("§7.bind <модуль> <клавиша> §f- установить бинд для модуля");
        sendMessage("§7.toggle <модуль> §f- включить/выключить модуль");
        sendMessage("§7.help §f- показать это сообщение");
    }
}
