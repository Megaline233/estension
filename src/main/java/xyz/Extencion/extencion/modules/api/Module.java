package xyz.Extencion.extencion.modules.api;

import lombok.Getter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Formatting;
import  xyz.Extencion.extencion.Extencion;
import  xyz.Extencion.extencion.event.EventBus;
import  xyz.Extencion.extencion.event.impl.EventClient;
import xyz.Extencion.extencion.modules.impl.client.Notifications;
import xyz.Extencion.extencion.modules.settings.Bind;
import xyz.Extencion.extencion.modules.settings.Setting;

import static  xyz.Extencion.extencion.commands.Command.sendMessage;

public class Module extends Feature {

    protected static MinecraftClient mc = MinecraftClient.getInstance();
    @Getter
    private final String description;
    @Getter
    private final Category category;
    public Setting<Boolean> enabled = this.register(new Setting<>("Enabled", false));
    public Setting<Boolean> drawn = this.register(new Setting<>("Drawn", true));
    public Setting<Bind> bind = this.register(new Setting<>("Keybind", new Bind(-1)));
    public Setting<String> displayName;
    public boolean hasListener;
    public boolean alwaysListening;
    public boolean hidden;

    public Module(String name, String description, Category category, boolean hasListener, boolean hidden, boolean alwaysListening) {
        super(name);
        this.displayName = this.register(new Setting<>("DisplayName", name));
        this.description = description;
        this.category = category;
        this.hasListener = hasListener;
        this.hidden = hidden;
        this.alwaysListening = alwaysListening;
    }

    public void onEnable() {
        Formatting green = Formatting.GREEN;
        Formatting gray = Formatting.GRAY;
        Formatting darkgreen = Formatting.DARK_GREEN;
        if (Extencion.getInstance().getModuleManager().getModuleByClass(Notifications.class).isEnabled()) {
            sendMessage(green + "[" + darkgreen + "+" + green + "]" + gray + " " + getName());
        }
    }

    public void onDisable() {
        Formatting red = Formatting.RED;
        Formatting gray = Formatting.GRAY;
        Formatting darkred = Formatting.DARK_RED;
        if (Extencion.getInstance().getModuleManager().getModuleByClass(Notifications.class).isEnabled()) {
            sendMessage(red + "[" + darkred + "-" + red + "]" + gray + " " + getName());
        }
    }

    public void onToggle() {
    }

    public void onLoad() {
    }

    public void onTick() {
    }

    public void onUpdate() {
    }

//    public void onRender2D(Render2DEvent event) {
//    }
//
//    public void onRender3D(Render3DEvent event) {
//    }

    public void onUnload() {
    }

    public String getDisplayInfo() {
        return null;
    }

    public boolean isOn() {
        return this.enabled.getValue();
    }

    public boolean isOff() {
        return !this.enabled.getValue();
    }

    public void setEnabled(boolean enabled) {
        if (enabled) {
            this.enable();
        } else {
            this.disable();
        }
    }

    public void enable() {
        this.enabled.setValue(true);
        this.onToggle();
        this.onEnable();
        if (this.isOn() && this.hasListener && !this.alwaysListening) {
            EventBus.register(this);
        }
    }

    public void disable() {
        if (this.hasListener && !this.alwaysListening) {
            EventBus.unregister(this);
        }
        this.enabled.setValue(false);
        this.onToggle();
        this.onDisable();
    }

    public void toggle() {
        EventClient event = new EventClient(!this.isEnabled() ? 1 : 0, this);
        EventBus.call(event);
        if (!event.isCancelled()) {
            this.setEnabled(!this.isEnabled());
        }
    }

    public String getDisplayName() {
        return this.displayName.getValue();
    }

    public void setDisplayName(String name) {
        Module module = Extencion.getInstance().getModuleManager().getModuleByDisplayName(name);
        Module originalModule = Extencion.getInstance().getModuleManager().getModuleByName(name);
        if (module == null && originalModule == null) {
            sendMessage(this.getDisplayName() + ", name: " + this.getName() + ", has been renamed to: " + name);
            this.displayName.setValue(name);
            return;
        }
        sendMessage(Formatting.RED + "A module of this name already exists.");
    }

    @Override public boolean isEnabled() {
        return isOn();
    }

    public boolean isDrawn() {
        return this.drawn.getValue();
    }

    public void setDrawn(boolean drawn) {
        this.drawn.setValue(drawn);
    }

    public String getInfo() {
        return null;
    }

    public Bind getBind() {
        return this.bind.getValue();
    }

    public void setBind(int key) {
        this.bind.setValue(new Bind(key));
    }

    public boolean listening() {
        return this.hasListener && this.isOn() || this.alwaysListening;
    }

    public String getFullArrayString() {
        return this.getDisplayName() + Formatting.GRAY + (this.getDisplayInfo() != null ? " [" + Formatting.WHITE + this.getDisplayInfo() + Formatting.GRAY + "]" : "");
    }

    public boolean isKeyPressed(int button) {
        if (button == -1) return false;

        if (Extencion.getInstance().getModuleManager().activeMouseKeys.contains(button)) {
            Extencion.getInstance().getModuleManager().activeMouseKeys.clear();
            return true;
        }

        if (button < 10) return false;

        return InputUtil.isKeyPressed(mc.getWindow().getHandle(), button);
    }

    public boolean isKeyPressed(Setting<Bind> bind) {
        if (bind.getValue().getKey() == -1) return false;
        return isKeyPressed(bind.getValue().getKey());
    }
}