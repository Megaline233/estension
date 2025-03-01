package xyz.Extencion.extencion;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;

public interface QuickImports {
    MinecraftClient mc = MinecraftClient.getInstance();
    Window window = MinecraftClient.getInstance().getWindow();
}