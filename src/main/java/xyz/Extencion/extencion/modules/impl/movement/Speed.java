package xyz.Extencion.extencion.modules.impl.movement;

import net.minecraft.client.network.ClientPlayerEntity;
import  xyz.Extencion.extencion.event.Event;
import  xyz.Extencion.extencion.event.EventTarget;
import  xyz.Extencion.extencion.event.impl.EventMotion;
import  xyz.Extencion.extencion.event.impl.EventTick;
import  xyz.Extencion.extencion.modules.api.Category;
import  xyz.Extencion.extencion.modules.api.Module;

public class Speed extends Module {

    public Speed() {
        super("Speed", "Не включай ато улетишь", Category.Movement, true, false, false);
    }

    private double moveSpeed = 0.2873D;
    private int stage = 0;
    private double lastDist;

    @EventTarget
    public void onMotion(EventMotion event) {
        if (mc.player == null) return;

        double xDist = event.getX() - mc.player.prevX;
        double zDist = event.getZ() - mc.player.prevZ;
        lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
    }

    @EventTarget
    public void onTick(EventTick event) {
        if (mc.player == null || event.getEra() != Event.Era.PRE) return;

        ClientPlayerEntity player = mc.player;

        if (player.forwardSpeed != 0 || player.sidewaysSpeed != 0) {
            if (player.isOnGround()) {
                if (stage == 2) {
                    moveSpeed *= 1.6;
                    player.jump();
                } else if (stage == 3) {
                    double diff = 0.66 * (lastDist - 0.2873D);
                    moveSpeed = lastDist - diff;
                } else {
                    moveSpeed = lastDist - lastDist / 159.0;
                }
            }

            moveSpeed = Math.max(moveSpeed, 0.2873D);
            moveSpeed *= 1.2D;

            float yaw = player.getYaw();
            double forward = player.forwardSpeed;
            double strafe = player.sidewaysSpeed;

            if (forward != 0) {
                if (strafe > 0) {
                    yaw += forward > 0 ? -45 : 45;
                } else if (strafe < 0) {
                    yaw += forward > 0 ? 45 : -45;
                }
                strafe = 0;
                if (forward > 0) {
                    forward = 1;
                } else {
                    forward = -1;
                }
            }

            double mx = Math.cos(Math.toRadians(yaw + 90));
            double mz = Math.sin(Math.toRadians(yaw + 90));

            player.setVelocity(
                    forward * moveSpeed * mx + strafe * moveSpeed * Math.cos(Math.toRadians(yaw)),
                    player.getVelocity().y,
                    forward * moveSpeed * mz + strafe * moveSpeed * Math.sin(Math.toRadians(yaw))
            );

            stage++;
        } else {
            stage = 0;
            moveSpeed = 0.2873D;
        }
    }
}