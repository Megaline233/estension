package xyz.Extencion.extencion.event.impl;

import lombok.Getter;
import xyz.Extencion.extencion.event.Event;

@Getter
public class EventSync extends Event {
    public EventSync(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

    float yaw;
    float pitch;
    Runnable postAction;

    public void addPostAction(Runnable r) {
        postAction = r;
    }
}