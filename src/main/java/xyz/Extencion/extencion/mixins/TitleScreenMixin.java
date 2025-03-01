package xyz.Extencion.extencion.mixins;

import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.MathHelper;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
    private static final int COLOR_TOP = 0xFF1A1A1A;
    private static final int COLOR_MID = 0xFF333333;
    private static final int COLOR_BOTTOM = 0xFF4A4A4A;

    private static final int MAX_PARTICLES = 30;
    private static final float[] SIN_CACHE = new float[360];
    private static final float[] COS_CACHE = new float[360];
    private final int[] particleX = new int[MAX_PARTICLES];
    private final int[] particleY = new int[MAX_PARTICLES];
    private int lastWidth = -1;
    private int lastHeight = -1;
    private long lastFrameTime = System.nanoTime();
    private float animationOffset = 0f;

    static {
        for (int i = 0; i < 360; i++) {
            double radians = Math.toRadians(i);
            SIN_CACHE[i] = (float) Math.sin(radians);
            COS_CACHE[i] = (float) Math.cos(radians);
        }
    }

    @Inject(method = "render", at = @At("HEAD"))
    private void renderCustomBackground(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        int width = context.getScaledWindowWidth();
        int height = context.getScaledWindowHeight();

        if (width != lastWidth || height != lastHeight) {
            initializeParticles(width, height);
            lastWidth = width;
            lastHeight = height;
        }

        long currentTime = System.nanoTime();
        float deltaTime = (currentTime - lastFrameTime) / 1_000_000_000f;
        lastFrameTime = currentTime;
        animationOffset = (animationOffset + deltaTime * 2) % 1f;

        renderOptimizedBackground(context, width, height);

        renderOptimizedParticles(context, width, height, deltaTime, mouseX, mouseY);
    }

    private void renderOptimizedBackground(DrawContext context, int width, int height) {
        int stripeHeight = 4;
        int stripes = height / stripeHeight;

        for (int i = 0; i < stripes; i++) {
            int y = i * stripeHeight;
            float progress = (float) y / height;

            int angleIndex = (int) ((progress * 360 + animationOffset * 360) % 360);
            float wave = SIN_CACHE[angleIndex] * 0.05f;

            int color;
            if (progress < 0.5f) {
                color = interpolateColor(COLOR_TOP, COLOR_MID, progress * 2);
            } else {
                color = interpolateColor(COLOR_MID, COLOR_BOTTOM, (progress - 0.5f) * 2);
            }

            color = adjustColorBrightness(color, 1 + wave);

            context.fill(0, y, width, y + stripeHeight, color);
        }
    }

    private void renderOptimizedParticles(DrawContext context, int width, int height, float deltaTime, int mouseX, int mouseY) {
        for (int i = 0; i < MAX_PARTICLES; i++) {
            particleY[i] -= (int)(50 * deltaTime);

            if (particleY[i] < 0) {
                particleY[i] = height;
                particleX[i] = (int)(Math.random() * width);
            }

            int dx = mouseX - particleX[i];
            int dy = mouseY - particleY[i];
            int distSq = dx * dx + dy * dy;

            if (distSq < 10000) {
                float force = 1 - (distSq / 10000f);
                particleX[i] += dx * force * 0.1f;
                particleY[i] += dy * force * 0.1f;
            }

            context.fill(particleX[i], particleY[i], particleX[i] + 1, particleY[i] + 1, 0x99FFFFFF);
        }
    }

    private void initializeParticles(int width, int height) {
        for (int i = 0; i < MAX_PARTICLES; i++) {
            particleX[i] = (int)(Math.random() * width);
            particleY[i] = (int)(Math.random() * height);
        }
    }

    private int interpolateColor(int color1, int color2, float progress) {
        int r1 = (color1 >> 16) & 0xFF;
        int g1 = (color1 >> 8) & 0xFF;
        int b1 = color1 & 0xFF;

        int r2 = (color2 >> 16) & 0xFF;
        int g2 = (color2 >> 8) & 0xFF;
        int b2 = color2 & 0xFF;

        int r = (int)(r1 + (r2 - r1) * progress);
        int g = (int)(g1 + (g2 - g1) * progress);
        int b = (int)(b1 + (b2 - b1) * progress);

        return 0xFF000000 | (r << 16) | (g << 8) | b;
    }

    private int adjustColorBrightness(int color, float factor) {
        int r = MathHelper.clamp((int)((color >> 16 & 0xFF) * factor), 0, 255);
        int g = MathHelper.clamp((int)((color >> 8 & 0xFF) * factor), 0, 255);
        int b = MathHelper.clamp((int)((color & 0xFF) * factor), 0, 255);
        return 0xFF000000 | (r << 16) | (g << 8) | b;
    }
}