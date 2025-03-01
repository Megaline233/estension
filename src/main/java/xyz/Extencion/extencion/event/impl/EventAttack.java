package xyz.Extencion.extencion.event.impl;

import lombok.Getter;
import net.minecraft.entity.Entity;
import xyz.Extencion.extencion.event.Event;

@Getter
public class EventAttack extends Event {
    private Entity entity;
    boolean pre;

    public EventAttack(Entity entity, boolean pre){
        this.entity = entity;
        this.pre = pre;
    }

}
