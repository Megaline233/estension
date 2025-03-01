package xyz.Extencion.extencion.event.impl;

import xyz.Extencion.extencion.event.Event;

public class EventKey extends Event {
    private final int key;

    public EventKey(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}