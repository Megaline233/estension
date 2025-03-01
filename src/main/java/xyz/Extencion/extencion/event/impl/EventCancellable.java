package xyz.Extencion.extencion.event.impl;

import xyz.Extencion.extencion.event.Cancellable;
import xyz.Extencion.extencion.event.Event;

public class EventCancellable extends Event implements Cancellable {

    private boolean cancelled;

    protected EventCancellable() {
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void cancel() {
        cancelled = true;
    }
}