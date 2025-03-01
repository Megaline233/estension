package xyz.Extencion.extencion.settings;

import java.util.Arrays;
import java.util.List;

public class ModeSetting extends Setting {
    private String value;
    private List<String> modes;
    private int index;

    public ModeSetting(String name, String defaultMode, String... modes) {
        super(name);
        this.modes = Arrays.asList(modes);
        this.index = this.modes.indexOf(defaultMode);
        this.value = this.modes.get(index);
    }

    public String getMode() {
        return value;
    }

    public void setMode(String mode) {
        this.value = mode;
        this.index = modes.indexOf(mode);
    }

    public List<String> getModes() {
        return modes;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
        this.value = modes.get(index);
    }

    public void cycle() {
        index = (index + 1) % modes.size();
        value = modes.get(index);
    }

    public boolean is(String mode) {
        return value.equalsIgnoreCase(mode);
    }
} 