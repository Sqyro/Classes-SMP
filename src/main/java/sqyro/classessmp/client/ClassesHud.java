package sqyro.classessmp.client;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.util.Mth;
import sqyro.classessmp.ClassesSMP;

import java.util.List;

public class ClassesHud {
    private static final int ICON_SIZE = 32;
    private static final int ICON_SPACING = 8;

    private static final int RIGHT_MARGIN = 10;
    private static final int BOTTOM_MARGIN = 10;

    public static void register() {
        HudRenderCallback.EVENT.register((graphics, deltaTracker) -> {
            render(graphics);
        });
    }

    private static void render(GuiGraphics Graphics) {
        Minecraft minecraft = Minecraft.getInstance();
        String classID = ClientPlayerData.getClassID();
        ClientClassData classData = ClientClasses.get(classID);

        if (classData == null) {
            ClassesSMP.LOGGER.info("No client data for {}", classID);
            return;
        }

        int screenWidth = minecraft.getWindow().getGuiScaledWidth();
        int screenHeight = minecraft.getWindow().getGuiScaledHeight();

        int PosX = screenWidth - RIGHT_MARGIN - ICON_SIZE;
        int baseY = screenHeight - BOTTOM_MARGIN - ICON_SIZE;

        List<ClientAbility> Abilities = classData.getAbilities();

        for (int i = 0; i < Abilities.size(); i++) {
            renderAbility(Graphics, Abilities.get(i), PosX, baseY - (i * (ICON_SIZE + ICON_SPACING)));
        }
    }

    private static void renderAbility(GuiGraphics Graphics, ClientAbility Ability, int PosX, int PosY) {
        Graphics.blit(RenderPipelines.GUI_TEXTURED, Ability.getBackground(), PosX, PosY, 0, 0, ICON_SIZE, ICON_SIZE, ICON_SIZE, ICON_SIZE);

        int Remaining = ClientPlayerData.getCooldown(Ability.getID());
        float Progress = 1.0F;

        if (Ability.getMaxCooldown() > 0) {
            Progress = 1.0F - Remaining / (float)(Ability.getMaxCooldown());
        }

        Progress = Mth.clamp(Progress, 0.0F, 1.0F);
        int visibleHeight = Math.round(ICON_SIZE * Progress);

        if (visibleHeight <= 0) {
            return;
        }

        int clipTop = PosY + ICON_SIZE - visibleHeight;

        Graphics.enableScissor(PosX, clipTop, PosX + ICON_SIZE, PosY + ICON_SIZE);
        Graphics.blit(RenderPipelines.GUI_TEXTURED, Ability.getColor(), PosX, PosY, 0, 0, ICON_SIZE, ICON_SIZE, ICON_SIZE, ICON_SIZE);
        Graphics.disableScissor();
    }
}