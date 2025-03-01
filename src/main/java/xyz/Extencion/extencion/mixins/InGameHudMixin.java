package xyz.Extencion.extencion.mixins;

import xyz.Extencion.extencion.event.RenderEvents;
import xyz.Extencion.extencion.utils.RenderProfiler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {
	@Inject(method = "render", at = @At("RETURN"))
	void renderer_postHud(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
		RenderProfiler.begin("Hud");
		RenderEvents.HUD.invoker().rendered(context);
		RenderProfiler.pop();
	}
}
