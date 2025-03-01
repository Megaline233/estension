package xyz.Extencion.extencion.mixins;

import com.mojang.brigadier.context.StringRange;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import net.minecraft.client.gui.screen.ChatInputSuggestor;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Mixin(ChatInputSuggestor.class)
public class ChatInputSuggestorMixin {

    @Final @Shadow TextFieldWidget textField;

    private CompletableFuture<Suggestions> pendingSuggestions;

    private static final List<String> CLIENT_COMMAND_SUGGESTIONS = Arrays.asList(
            ".help", ".bind", ".toggle", ".module list", ".settings", ".friend add", ".friend remove", ".gps set", ".gps clear", ".panic"
    );

    @Shadow
    protected native void show(boolean narrateFirstSuggestion);

    @Inject(method = "refresh", at = @At("HEAD"), cancellable = true)
    private void onRefresh(CallbackInfo ci) {
        String text = textField != null ? textField.getText() : "";
        if (text.startsWith(".")) {
            List<Suggestion> suggestions = new ArrayList<>();
            int commandStartPos = 1;
            StringRange range = StringRange.between(commandStartPos, text.length());

            String commandInput = text.substring(commandStartPos).trim().toLowerCase();

            if (commandInput.isEmpty()) {
                for (String command : CLIENT_COMMAND_SUGGESTIONS) {
                    suggestions.add(new Suggestion(range, command.substring(1)));
                }
            } else {
                for (String command : CLIENT_COMMAND_SUGGESTIONS) {
                    if (command.substring(1).toLowerCase().startsWith(commandInput)) {
                        suggestions.add(new Suggestion(range, command.substring(1)));
                    }
                }
            }

            if (!suggestions.isEmpty()) {
                this.pendingSuggestions = CompletableFuture.completedFuture(
                        new Suggestions(range, suggestions)
                );
                this.show(true);
                ci.cancel();
            }
        }
    }
}