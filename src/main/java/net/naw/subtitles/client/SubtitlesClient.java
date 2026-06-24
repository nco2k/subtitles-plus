package net.naw.subtitles.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

public class SubtitlesClient implements ClientModInitializer {

    public static KeyMapping configKey;
    public static KeyMapping toggleKey;

    @Override
    public void onInitializeClient() {
        // --- INITIALIZATION ---
        SubtitleConfig.load();

        // --- KEYBINDING REGISTRATION ---
        configKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.subtitles.open_menu",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_F4,
                KeyMapping.Category.MISC
        ));

        toggleKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.subtitles.toggle",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_H,
                KeyMapping.Category.MISC
        ));

        // --- TICK EVENTS ---
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // Open the configuration menu
            while (configKey.consumeClick()) {
                client.setScreen(new SubtitleConfigScreen());
            }

            // Toggle subtitle visibility
            while (toggleKey.consumeClick()) {
                boolean active = !client.options.showSubtitles().get();
                client.options.showSubtitles().set(active);
                client.options.save();

				// Feedback message for the player
                Component status = active
                        ? Component.literal("Subtitles: §aON")
                        : Component.literal("Subtitles: §cOFF");

                client.gui.setOverlayMessage(status, false);
            }
        });
    }
}
