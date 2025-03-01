package xyz.Extencion.extencion.event.impl;

import xyz.Extencion.extencion.event.Event;
import  xyz.Extencion.extencion.modules.settings.Setting;
import  xyz.Extencion.extencion.modules.api.Module;

public class EventClient extends Event {
    private Module feature;
    private Setting<?> setting;
    private int stage;

    public EventClient(int stage, Module feature) {
        this.stage = stage;
        this.feature = feature;
    }

    public EventClient(Setting<?> setting) {
        this.stage = 2;
        this.setting = setting;
    }

    public Module getFeature() {
        return this.feature;
    }

    public Setting<?> getSetting() {
        return this.setting;
    }

    public int getStage() {
        return stage;
    }
}
