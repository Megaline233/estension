package xyz.Extencion.extencion.event.impl;

import lombok.Getter;
import xyz.Extencion.extencion.event.Event;

@Getter
public class EventMouse extends Event {

    int button;
    int action;

    public EventMouse(int b,int action) {
        button = b;
        this.action = action;
    }
}
