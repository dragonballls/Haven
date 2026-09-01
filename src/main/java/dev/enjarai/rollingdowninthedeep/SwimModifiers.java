package dev.enjarai.rollingdowninthedeep;

import nl.enjarai.doabarrelroll.api.event.RollContext;
import nl.enjarai.doabarrelroll.api.rotation.RotationInstant;

public final class SwimModifiers {
    private SwimModifiers() {}

    public static RotationInstant reorient(RotationInstant rotation, RollContext context) {
        double roll = rotation.roll();
        double delta = Math.max(-45.0, Math.min(45.0, roll));
        return rotation.add(0, 0, -delta * 0.05 * context.getRenderDelta());
    }
}
