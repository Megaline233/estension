package xyz.Extencion.extencion.commands;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static xyz.Extencion.extencion.QuickImports.mc;

public class Command {

    public static void sendMessage(String message) {
        Formatting gray = Formatting.GRAY;
        Formatting reset = Formatting.WHITE;
        if (mc.player != null) {
            mc.player.sendMessage(Text.literal(gray + "[" + reset + "pizdec" + gray + "] " + reset + message), false);
        }
    }
}
