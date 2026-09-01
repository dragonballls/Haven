package dev.enjarai.rollingdowninthedeep;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import nl.enjarai.doabarrelroll.api.event.RollContext;
import nl.enjarai.doabarrelroll.api.rotation.RotationInstant;

public final class StrafeRollModifiers {
    private StrafeRollModifiers() {}

    public static RotationInstant applyStrafeRoll(RotationInstant rotation, RollContext context) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return rotation;

        double roll = 0;
        double yaw = 0;
        if (SwimConfig.INSTANCE.strafeDoStrafe) {
            if (Minecraft.getInstance().options.keyLeft.isDown() && !Minecraft.getInstance().options.keyRight.isDown()) {
                roll = -SwimConfig.INSTANCE.strafeRollStrength;
                yaw = -SwimConfig.INSTANCE.strafeYawStrength;
            } else if (Minecraft.getInstance().options.keyRight.isDown() && !Minecraft.getInstance().options.keyLeft.isDown()) {
                roll = SwimConfig.INSTANCE.strafeRollStrength;
                yaw = SwimConfig.INSTANCE.strafeYawStrength;
            }
        }

        double speed = player.getDeltaMovement().length();
        double velocityMultiplier = SwimConfig.INSTANCE.velocityEnable
                ? 1.0 + Math.max(SwimConfig.INSTANCE.velocityMin, Math.min(SwimConfig.INSTANCE.velocityMax, speed * 2.835)) * SwimConfig.INSTANCE.velocityScale
                : 1.0;

        double delta = context.getRenderDelta();
        return rotation.add(0, yaw * 50 * velocityMultiplier * delta, roll * 50 * velocityMultiplier * delta);
    }
}
