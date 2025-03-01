package xyz.Extencion.extencion.event.impl;

import lombok.Getter;
import net.minecraft.screen.slot.SlotActionType;
import xyz.Extencion.extencion.event.Event;

@Getter
public class EventClickSlot extends Event {
    private final SlotActionType slotActionType;
    private final int slot, button, id;

    public EventClickSlot(SlotActionType slotActionType, int slot, int button, int id) {
        this.slot = slot;
        this.button = button;
        this.id = id;
        this.slotActionType = slotActionType;
    }
}