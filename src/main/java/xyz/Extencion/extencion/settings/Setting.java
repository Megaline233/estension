package xyz.Extencion.extencion.settings;

public class Setting {
    private String name;
    private boolean visible = true;

    public Setting(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
} 