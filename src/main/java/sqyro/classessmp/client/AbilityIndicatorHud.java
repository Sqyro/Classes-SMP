package sqyro.classessmp.client;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import sqyro.classessmp.ClassesSMP;

import java.util.List;

public class AbilityIndicatorHud {
    private static final int NORMAL_INDICATOR_SIZE_X = 4;
    private static final int NORMAL_INDICATOR_SIZE_Y = 8;
    private static final int OFFSET = 10;

    private static final Identifier[] ABILITY_INDICATORS = {
            Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/ability_indicators/ability_1_indicator.png"),
            Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/ability_indicators/ability_2_indicator.png"),
            Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/ability_indicators/ability_3_indicator.png")
    };

    public static void register() {
        HudRenderCallback.EVENT.register((graphics, deltaTracker) -> {
            render(graphics);
        });
    }

    private static void render(GuiGraphics Graphics) {
        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.player == null) {
            return;
        }

        ClientClassData classData = ClientClasses.get(ClientPlayerData.getClassID());

        if (classData == null) {
            return;
        }

        List<ClientAbility> Abilities = classData.getAbilities();

        if (Abilities.isEmpty()) {
            return;
        }

        int CenterX = Graphics.guiWidth() / 2;
        int CenterY = Graphics.guiHeight() / 2;

        int Count = Math.min(Abilities.size(), 3);

        for (int i = 0; i < Count; i++) {
            ClientAbility Ability = Abilities.get(i);

            if (ClientPlayerData.getCooldown(Ability.getID()) > 0) {
                continue;
            }

            drawAbilityIndicator(Graphics, i, CenterX, CenterY);
        }
    }

    private static void drawAbilityIndicator(GuiGraphics Graphics, int Index, int CenterX, int CenterY) {
        Identifier identifierTexture = ABILITY_INDICATORS[Index];

        switch (Index) {
            case 0 -> drawIndicator(Graphics, identifierTexture, CenterX - OFFSET - NORMAL_INDICATOR_SIZE_X, CenterY - NORMAL_INDICATOR_SIZE_Y / 2, NORMAL_INDICATOR_SIZE_X, NORMAL_INDICATOR_SIZE_Y);
            case 1 -> drawIndicator(Graphics, identifierTexture, CenterX + OFFSET, CenterY - NORMAL_INDICATOR_SIZE_Y / 2, NORMAL_INDICATOR_SIZE_X, NORMAL_INDICATOR_SIZE_Y);
            case 2 -> drawIndicator(Graphics, identifierTexture, CenterX - NORMAL_INDICATOR_SIZE_Y / 2, CenterY - OFFSET - NORMAL_INDICATOR_SIZE_X / 2, NORMAL_INDICATOR_SIZE_Y, NORMAL_INDICATOR_SIZE_X);
            default -> {
                return;
            }
        }
    }

    private static void drawIndicator(GuiGraphics Graphics, Identifier Texture, int PosX, int PosY, int SizeX, int SizeY) {
        Graphics.blit(RenderPipelines.CROSSHAIR, Texture, PosX, PosY, 0, 0, SizeX, SizeY, SizeX, SizeY);
    }
}