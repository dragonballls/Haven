package dev.enjarai.rollingdowninthedeep;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public final class SwimKeybindings {
    public static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(
            Identifier.fromNamespaceAndPath(RollingDownInTheDeep.MOD_ID, "controls")
    );

    public static final KeyMapping TOGGLE_ENABLED = KeyMappingHelper.registerKeyMapping(
            new KeyMapping("key.rolling_down_in_the_deep.toggle_enabled", InputConstants.Type.KEYSYM,
                    InputConstants.KEY_O, CATEGORY)
    );

    private SwimKeybindings() {}

    public static void tick(Minecraft client) {
        while (TOGGLE_ENABLED.consumeClick()) {
            SwimConfig.INSTANCE.enabled = !SwimConfig.INSTANCE.enabled;
            SwimConfig.INSTANCE.save();
            if (client.player != null) {
                client.player.sendSystemMessage(Component.translatable(
                        SwimConfig.INSTANCE.enabled
                                ? "rolling_down_in_the_deep.enabled"
                                : "rolling_down_in_the_deep.disabled"));
            }
        }
    }
}
