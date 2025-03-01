package xyz.Extencion.extencion.event.impl;

import lombok.Getter;
import net.minecraft.entity.player.PlayerEntity;
import xyz.Extencion.extencion.event.Event;

@Getter
public class EventTotemPop extends Event {
    private final PlayerEntity entity;
    private int pops;

    public EventTotemPop(PlayerEntity entity, int pops) {
        this.entity = entity;
        this.pops = pops;
    }
}