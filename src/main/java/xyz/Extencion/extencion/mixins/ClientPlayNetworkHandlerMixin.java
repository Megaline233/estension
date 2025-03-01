package xyz.Extencion.extencion.mixins;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.Extencion.extencion.commands.impl.*;

import static xyz.Extencion.extencion.commands.Command.sendMessage;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    private void onChatMessage(String message, CallbackInfo ci) {
        if (message.startsWith(".")) {
            ci.cancel();
            String[] args = message.substring(1).split(" ");
            String command = args[0].toLowerCase();

            try {
                switch (command) {
                    case "bind":
                        BindCommand.execute(args);
                        break;

                    case "toggle":
                        ToggleCommand.execute(args);
                        break;

                    case "help":
                        HelpCommand.execute(args);
                        break;

                    default:
                        sendMessage("§cНеизвестная команда. Напишите .help для списка команд");
                        break;
                }
            } catch (Exception e) {
                sendMessage("§cПроизошла ошибка при выполнении команды: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}