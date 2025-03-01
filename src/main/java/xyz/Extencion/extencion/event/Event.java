package xyz.Extencion.extencion.event;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Event {
    private boolean cancelled;
    private Era era = Era.PRE;

    public enum Era {
        PRE, POST
    }
}