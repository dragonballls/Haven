package dev.enjarai.rollingdowninthedeep;

import nl.enjarai.doabarrelroll.api.event.RollContext;
import nl.enjarai.doabarrelroll.api.rotation.RotationInstant;
import nl.enjarai.doabarrelroll.config.ModConfig;

public final class CameraModifiers {
    private CameraModifiers() {}

    public static RotationInstant configureRotation(RotationInstant rotation, RollContext context) {
        double pitch = rotation.pitch();
        double yaw = rotation.yaw();
        double roll = rotation.roll();

        if (!SwimConfig.INSTANCE.swapYawAndRoll) {
            double temp = yaw;
            yaw = roll;
            roll = temp;
        }
        if (ModConfig.INSTANCE.getInvertPitch()) pitch = -pitch;
        return RotationInstant.of(pitch, yaw, roll);
    }
}
