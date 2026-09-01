package dev.enjarai.rollingdowninthedeep;

import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Files;
import java.nio.file.Path;

/** Lightweight 26.2 configuration replacing the old Cicada config dependency. */
public final class SwimConfig {
    public static final SwimConfig INSTANCE = new SwimConfig();

    public boolean enabled = true;
    public boolean persistentSwimming = true;
    public boolean swapYawAndRoll = false;
    public boolean strafeDoStrafe = true;
    public boolean velocityEnable = true;
    public double strafeRollStrength = 0.2;
    public double strafeYawStrength = 1.0;
    public double velocityMin = 0.4;
    public double velocityMax = 1.0;
    public double velocityScale = 8.65;
    public double movementSpeed = 0.075;

    private SwimConfig() {}

    public void load() {
        Path path = FabricLoader.getInstance().getConfigDir().resolve("rolling_down_in_the_deep-client.json");
        if (!Files.exists(path)) save();
    }

    public void save() {
        Path path = FabricLoader.getInstance().getConfigDir().resolve("rolling_down_in_the_deep-client.json");
        try {
            Files.createDirectories(path.getParent());
            String json = "{\n" +
                    "  \"enabled\": " + enabled + ",\n" +
                    "  \"persistentSwimming\": " + persistentSwimming + ",\n" +
                    "  \"swapYawAndRoll\": " + swapYawAndRoll + ",\n" +
                    "  \"strafeDoStrafe\": " + strafeDoStrafe + ",\n" +
                    "  \"velocityEnable\": " + velocityEnable + ",\n" +
                    "  \"movementSpeed\": " + movementSpeed + "\n" +
                    "}\n";
            Files.writeString(path, json);
        } catch (Exception ignored) {
        }
    }
}
