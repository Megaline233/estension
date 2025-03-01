package xyz.Extencion.extencion;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.Extencion.extencion.managers.*;
import xyz.Extencion.extencion.managers.ModuleManager;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Environment(EnvType.CLIENT)
public class Extencion implements ModInitializer, ClientModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("Extencion");

    @Getter
    private static Extencion instance;

    private ModuleManager moduleManager;
    private EventManager eventManager;
    public MinecraftClient mc;

    @Override
    public void onInitialize() {
        instance = this;
        this.mc = MinecraftClient.getInstance();
        moduleManager = new ModuleManager();
        moduleManager.init();
        eventManager = new EventManager();
        eventManager.init();
    }

    @Override
    public void onInitializeClient() {
        LOGGER.info("Initialized Extencion library");
    }
}