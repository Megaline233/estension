package xyz.Extencion.extencion.modules.impl.misc;

import net.minecraft.client.MinecraftClient;
import  xyz.Extencion.extencion.modules.api.Category;
import  xyz.Extencion.extencion.modules.api.Module;
import xyz.Extencion.extencion.modules.settings.Setting;
import  xyz.Extencion.extencion.utils.math.TimerUtil; // Изменен импорт

public class ClientName extends Module {
    private final Setting<String> windowTitle = this.register(new Setting<>("Title", "Extencion Client"));
    private final Setting<Boolean> animation = this.register(new Setting<>("Animation", false));
    private final Setting<Mode> type = this.register(new Setting<>("Type", Mode.Scroll, v -> animation.getValue()));
    private final Setting<Integer> speed = this.register(new Setting<>("Speed", 100, 50, 300, v -> animation.getValue()));

    private String fullName;
    private int step = 0;
    private final TimerUtil timer = new TimerUtil(); // Изменен тип на TimerUtil
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public ClientName() {
        super("ClientName", "ьэьэьэьэь", Category.Misc, false, false, false);
    }

    private enum Mode {
        Scroll, Blink, Wave, Bounce
    }

    @Override
    public void onTick() {
        fullName = windowTitle.getValue();

        if (animation.getValue()) {
            if (timer.passedMs(speed.getValue().longValue())) {
                animateTitle();
                timer.reset();
            }
        } else {
            setWindowTitle(fullName);
        }
    }

    private void animateTitle() {
        switch (type.getValue()) {
            case Scroll -> scrollAnimation();
            case Blink -> blinkAnimation();
            case Wave -> waveAnimation();
            case Bounce -> bounceAnimation();
        }
    }

    private void scrollAnimation() {
        int len = fullName.length();
        if (step < len) {
            setWindowTitle(fullName.substring(0, step + 1));
        } else if (step < len + 15) {
            setWindowTitle(fullName);
        } else if (step < len * 2) {
            setWindowTitle(fullName.substring(step - len, len));
        } else {
            step = 0;
        }
        step++;
    }

    private void blinkAnimation() {
        setWindowTitle((step % 2 == 0) ? fullName : "");
        step++;
    }

    private void waveAnimation() {
        StringBuilder waved = new StringBuilder();
        for (int i = 0; i < fullName.length(); i++) {
            waved.append(((i + step) % 2 == 0) ?
                    Character.toUpperCase(fullName.charAt(i)) :
                    Character.toLowerCase(fullName.charAt(i)));
        }
        setWindowTitle(waved.toString());
        step = (step + 1) % fullName.length();
    }

    private void bounceAnimation() {
        setWindowTitle((step % 2 == 0) ? fullName.toUpperCase() : fullName.toLowerCase());
        step++;
    }

    private void setWindowTitle(String title) {
        try {
            if (mc.getWindow() != null) {
                mc.getWindow().setTitle(title);
            }
        } catch (Exception ex) {
            System.err.println("Failed to set window title: " + ex.getMessage());
        }
    }

    @Override
    public void onDisable() {
        setWindowTitle("Minecraft 1.21");
        super.onDisable();
    }
}