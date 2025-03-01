package xyz.Extencion.extencion.event.impl;

import xyz.Extencion.extencion.event.Event;

public class EventTick extends Event {
    public EventTick(Era era) {
        setEra(era);
    }
}