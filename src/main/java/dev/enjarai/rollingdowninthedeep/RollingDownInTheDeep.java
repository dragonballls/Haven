package dev.enjarai.rollingdowninthedeep;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;
import nl.enjarai.doabarrelroll.api.event.RollEvents;
import nl.enjarai.doabarrelroll.api.event.RollGroup;

public final class RollingDownInTheDeep implements ClientModInitializer {
    public static final String MOD_ID = "rolling_down_in_the_deep";
    public static final RollGroup SWIM_GROUP = RollGroup.of(id("swimming"));
    public static final RollGroup DABR_GROUP = RollGroup.of(Identifier.fromNamespaceAndPath("do_a_barrel_roll", "fall_flying"));

    @Override
    public void onInitializeClient() {
        SwimConfig.INSTANCE.load();
        ClientTickEvents.END_CLIENT_TICK.register(SwimKeybindings::tick);

        SWIM_GROUP.trueIf(RollingDownInTheDeep::shouldRoll);

        RollEvents.EARLY_CAMERA_MODIFIERS.register(context -> context.useModifier(CameraModifiers::configureRotation),
                1000, () -> SWIM_GROUP.get() && !DABR_GROUP.get());
        RollEvents.EARLY_CAMERA_MODIFIERS.register(context -> context.useModifier(StrafeRollModifiers::applyStrafeRoll),
                2000, () -> SWIM_GROUP.get() && !DABR_GROUP.get());
        RollEvents.LATE_CAMERA_MODIFIERS.register(context -> context.useModifier(SwimModifiers::reorient),
                3000, () -> SWIM_GROUP.get() && !DABR_GROUP.get());
    }

    public static boolean shouldRoll() {
        LocalPlayer player = Minecraft.getInstance().player;
        return SwimConfig.INSTANCE.enabled && player != null && player.isSwimming() && player.isUnderWater();
    }

    public static void tickMovement() {
        Minecraft client = Minecraft.getInstance();
        LocalPlayer player = client.player;
        if (player == null || !shouldRoll()) return;

        Vec3 look = player.getLookAngle();
        Vec3 forward = look.normalize();
        Vec3 right = new Vec3(-forward.z, 0, forward.x);
        if (right.lengthSqr() > 1.0E-6) right = right.normalize();

        Vec3 input = Vec3.ZERO;
        if (client.options.keyUp.isDown()) input = input.add(forward);
        if (client.options.keyDown.isDown()) input = input.subtract(forward);
        if (client.options.keyRight.isDown()) input = input.add(right);
        if (client.options.keyLeft.isDown()) input = input.subtract(right);
        if (client.options.keyJump.isDown()) input = input.add(0, 1, 0);
        if (client.options.keyShift.isDown()) input = input.add(0, -1, 0);

        if (input.lengthSqr() > 1) input = input.normalize();
        Vec3 target = input.scale(SwimConfig.INSTANCE.movementSpeed);
        Vec3 current = player.getDeltaMovement();
        player.setDeltaMovement(current.scale(0.82).add(target.scale(0.18)));
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
