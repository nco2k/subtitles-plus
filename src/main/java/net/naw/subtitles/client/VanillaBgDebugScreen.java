package net.naw.subtitles.client;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

/**
 * VanillaBgDebugScreen: Live tuning screen for vanilla background (mode 2) alignment values.
 * Transparent background so you can see real subtitles while tweaking.
 * Values are saved to config automatically and persist across restarts.
 */
public class VanillaBgDebugScreen extends Screen {
    private final Screen parent;
    private final SubtitleConfig config = SubtitleConfig.INSTANCE;

    public VanillaBgDebugScreen(Screen parent) {
        super(Component.literal("BG Tune"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int sliderW = 200;
        int startX = 10;
        int startY = 10;

        // Arrow Padding slider (0 to 30)
        this.addRenderableWidget(new AbstractSliderButton(startX, startY, sliderW, 20,
                Component.literal("Arrow Padding: " + (int)config.debugArrowPadding),
                config.debugArrowPadding / 30.0) {
            @Override protected void updateMessage() {
                config.debugArrowPadding = (float)(this.value * 30.0);
                this.setMessage(Component.literal("Arrow Padding: " + (int)config.debugArrowPadding));
            }
            @Override protected void applyValue() {
                config.debugArrowPadding = (float)(this.value * 30.0);
                config.save();
            }
        });

        // Auto Align Inset slider (0 to 30)
        this.addRenderableWidget(new AbstractSliderButton(startX, startY + 25, sliderW, 20,
                Component.literal("Align:Auto Offset: " + (int)config.debugAutoAlignInset),
                config.debugAutoAlignInset / 30.0) {
            @Override protected void updateMessage() {
                config.debugAutoAlignInset = (float)(this.value * 30.0);
                this.setMessage(Component.literal("Align:Auto Offset: " + (int)config.debugAutoAlignInset));
            }
            @Override protected void applyValue() {
                config.debugAutoAlignInset = (float)(this.value * 30.0);
                config.save();
            }
        });

        // Center Offset X slider (-20 to 20)
        this.addRenderableWidget(new AbstractSliderButton(startX, startY + 50, sliderW, 20,
                Component.literal("Align:Center Offset X: " + (int)config.debugCenterOffsetX),
                (config.debugCenterOffsetX + 20.0) / 40.0) {
            @Override protected void updateMessage() {
                config.debugCenterOffsetX = (float)(this.value * 40.0 - 20.0);
                this.setMessage(Component.literal("Align:Center Offset: " + (int)config.debugCenterOffsetX));
            }
            @Override protected void applyValue() {
                config.debugCenterOffsetX = (float)(this.value * 40.0 - 20.0);
                config.save();
            }
        });

        // Arrow Gap slider (0 to 10)
        this.addRenderableWidget(new AbstractSliderButton(startX, startY + 75, sliderW, 20,
                Component.literal("Arrow Gap: " + (int)config.debugArrowGap),
                config.debugArrowGap / 10.0) {
            @Override protected void updateMessage() {
                config.debugArrowGap = (float)(this.value * 10.0);
                this.setMessage(Component.literal("Arrow Gap: " + (int)config.debugArrowGap));
            }
            @Override protected void applyValue() {
                config.debugArrowGap = (float)(this.value * 10.0);
                config.save();
            }
        });

        // BG Width Padding slider (0 to 20)
        this.addRenderableWidget(new AbstractSliderButton(startX, startY + 100, sliderW, 20,
                Component.literal("BG Width Pad: " + (int)config.debugBgWidthPadding),
                config.debugBgWidthPadding / 20.0) {
            @Override protected void updateMessage() {
                config.debugBgWidthPadding = (float)(this.value * 20.0);
                this.setMessage(Component.literal("BG Width Pad: " + (int)config.debugBgWidthPadding));
            }
            @Override protected void applyValue() {
                config.debugBgWidthPadding = (float)(this.value * 20.0);
                config.save();
            }
        });

        // BG Height Padding slider (0 to 10)
        this.addRenderableWidget(new AbstractSliderButton(startX, startY + 125, sliderW, 20,
                Component.literal("BG Height Pad: " + (int)config.debugBgHeightPadding),
                config.debugBgHeightPadding / 10.0) {
            @Override protected void updateMessage() {
                config.debugBgHeightPadding = (float)(this.value * 10.0);
                this.setMessage(Component.literal("BG Height Pad: " + (int)config.debugBgHeightPadding));
            }
            @Override protected void applyValue() {
                config.debugBgHeightPadding = (float)(this.value * 10.0);
                config.save();
            }
        });

        // Arrow Offset Y slider (-10 to 10)
        this.addRenderableWidget(new AbstractSliderButton(startX, startY + 150, sliderW, 20,
                Component.literal("Arrow Offset Y: " + (int)config.debugArrowOffsetY),
                (config.debugArrowOffsetY + 10.0) / 20.0) {
            @Override protected void updateMessage() {
                config.debugArrowOffsetY = (float)(this.value * 20.0 - 10.0);
                this.setMessage(Component.literal("Arrow Offset Y: " + (int)config.debugArrowOffsetY));
            }
            @Override protected void applyValue() {
                config.debugArrowOffsetY = (float)(this.value * 20.0 - 10.0);
                config.save();
            }
        });

        // Back button
        this.addRenderableWidget(Button.builder(Component.literal("Back"), (ignored) -> this.minecraft.setScreen(this.parent))
                .bounds(startX, startY + 180, 60, 20).build());

        // Reset button — resets all debug values to their defaults
        this.addRenderableWidget(Button.builder(Component.literal("Reset"), (ignored) -> {
            config.debugArrowPadding = 5f;
            config.debugAutoAlignInset = 10f;
            config.debugCenterOffsetX = 0f;
            config.debugArrowGap = 1f;
            config.debugBgWidthPadding = 1f;
            config.debugBgHeightPadding = 1f;
            config.debugArrowOffsetY = 0f;
            config.save();
            this.rebuildWidgets();
        }).bounds(startX + 65, startY + 180, 100, 20).build());
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor context, int mouseX, int mouseY, float delta) {
        // No background — transparent so subtitles are visible behind the sliders
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor context, int mouseX, int mouseY, float delta) {
        // Semi-transparent panel behind sliders only so text is readable
        context.fill(5, 5, 220, 215, 0x88000000);
        context.centeredText(this.font, Component.literal("§eVanilla BG Debug — see subtitles behind"), this.width / 2, 220, 0xAAAAAA);
        super.extractRenderState(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean isPauseScreen() {
        return false; // Keep game running so subtitles show
    }
}