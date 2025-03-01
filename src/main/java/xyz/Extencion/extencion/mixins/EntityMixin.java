package xyz.Extencion.extencion.mixins;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.Extencion.extencion.event.EventBus;
import xyz.Extencion.extencion.event.impl.EventMotion;

@Mixin(Entity.class)
public class EntityMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void onPreMotion(CallbackInfo ci) {
        Entity entity = (Entity)(Object)this;
        EventBus.call(new EventMotion(
                entity.getX(),
                entity.getY(),
                entity.getZ(),
                entity.getYaw(),
                entity.getPitch(),
                entity.isOnGround()
        ));
    }
}