package sqyro.classessmp.client;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import sqyro.classessmp.ClassesSMP;
import sqyro.classessmp.playerclasses.AncientWarden;

import java.util.List;

public class ClassesHud {
    private static final int ICON_SIZE = 32;
    private static final int ICON_SPACING = 8;

    private static final int RIGHT_MARGIN = 10;
    private static final int BOTTOM_MARGIN = 10;

    private static final Identifier DICE = Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/gambler/dice.png");
    private static final Identifier RED_DICE = Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/gambler/red_dice.png");

    private static final int DICE_SIZE = 32;
    private static final int DICE_SPACING = 4;

    private static final Identifier BLOOD_OVERLAY = Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/blood_sword_overlay.png");

    private static final Identifier NOISE_BACKGROUND = Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/ancient_warden/noise_meter_bar_bg.png");
    private static final Identifier NOISE_BAR = Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/ancient_warden/noise_meter_bar_fill.png");

    private static final int NOISE_WIDTH = 8;
    private static final int NOISE_HEIGHT = 128;

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

        if (classID.equals("gambler")) {
            renderGamblerHud(Graphics);
        }

        if (classID.equals("blood_sword")) {
            renderBloodOverlay(Graphics);
        }

        if (classID.equals("ancient_warden")) {
            renderNoiseMeter(Graphics);
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

    private static void renderGamblerHud(GuiGraphics Graphics) {
        renderGamblerRoll(Graphics);
        renderGamblerLevel(Graphics);
    }

    private static void renderGamblerRoll(GuiGraphics Graphics) {
        if (!ClientPlayerData.shouldRenderGamblerRoll()) {
            return;
        }
        Minecraft minecraft = Minecraft.getInstance();

        int Roll = ClientPlayerData.getGamblerRoll();

        String Text = (Roll >= 0 ? "+" : "") + Roll;

        int PosX = Graphics.guiWidth() / 2 + 10;
        int PosY = Graphics.guiHeight() / 2 - 4;

        int Color = getGamblerRollColor(Roll);
        Graphics.drawString(minecraft.font, Text, PosX, PosY, Color);
    }

    private static int getGamblerRollColor(int Roll) {
        int Min = -10 + ClientPlayerData.getGamblerLevel();
        int Max = 20 + ClientPlayerData.getGamblerLevel();

        float Progress;

        if (Roll < 0) {
            Progress = (Roll - Min) / (float)(Min);
            int r = 255;
            int g = (int)(Progress * 255);
            int b = 0;

            return 0xFF000000 | (r << 16) | (g << 8) | b;
        } else {
            Progress = Roll / (float)Max;
            int r = (int)(255 * (1 - Progress));
            int g = 255;
            int b = 0;

            return 0xFF000000 | (r << 16) | (g << 8) | b;
        }
    }

    private static void renderGamblerLevel(GuiGraphics Graphics) {
        int Level = ClientPlayerData.getGamblerLevel();

        if (Level == 0) {
            return;
        }

        Identifier Texture = Level > 0 ? DICE : RED_DICE;

        int Amount = Math.abs(Level);

        int StartX = Graphics.guiWidth() - RIGHT_MARGIN - DICE_SIZE;
        int StartY = Graphics.guiHeight() - BOTTOM_MARGIN - DICE_SIZE;

        for (int i = 0; i < Amount; i++) {
            Graphics.blit(RenderPipelines.GUI_TEXTURED, Texture, StartX, StartY - i * (DICE_SIZE + DICE_SPACING), 0, 0, DICE_SIZE, DICE_SIZE, DICE_SIZE, DICE_SIZE);
        }
    }

    private static void renderBloodOverlay(GuiGraphics graphics) {
        float Alpha = ClientPlayerData.getBloodIntensity();

        if (Alpha <= 0.001F) {
            return;
        }

        int alphaInt = (int) (Alpha * 255);
        int Color = (alphaInt << 24) | 0xFFFFFF;

        graphics.blit(RenderPipelines.GUI_TEXTURED, BLOOD_OVERLAY, 0, 0, 0, 0, graphics.guiWidth(), graphics.guiHeight(), graphics.guiWidth(), graphics.guiHeight(), Color);
    }

    private static void renderNoiseMeter(GuiGraphics Graphics) {
        int PosX = RIGHT_MARGIN;
        int PosY = Graphics.guiHeight()/2 - NOISE_HEIGHT/2;

        Graphics.blit(RenderPipelines.GUI_TEXTURED, NOISE_BACKGROUND, PosX, PosY, 0, 0, NOISE_WIDTH, NOISE_HEIGHT, NOISE_WIDTH, NOISE_HEIGHT);

        int extraDamage;

        if (ClientPlayerData.getNoiseMeter() >= AncientWarden.NOISE_METER_MAX_VALUE * 0.75f) {
            extraDamage = 6;
        } else if (ClientPlayerData.getNoiseMeter() >= AncientWarden.NOISE_METER_MAX_VALUE * 0.5f) {
            extraDamage = 3;
        } else if (ClientPlayerData.getNoiseMeter() >= AncientWarden.NOISE_METER_MAX_VALUE * 0.25f) {
            extraDamage = 1;
        } else {
            extraDamage = 0;
        }

        String Text = "+" + extraDamage;

        Graphics.drawString(Minecraft.getInstance().font, Text, Graphics.guiWidth() / 2 + 10, Graphics.guiHeight() / 2 + 5, 0xFF058769);

        float Progress = ClientPlayerData.getNoiseMeter() / (float)AncientWarden.NOISE_METER_MAX_VALUE;
        int VisibleHeight = Math.round(NOISE_HEIGHT * Progress);

        if (VisibleHeight <= 0) {
            return;
        }


        int ClipTop = PosY + NOISE_HEIGHT - VisibleHeight;
        Graphics.enableScissor(PosX, ClipTop, PosX + NOISE_WIDTH, PosY + NOISE_HEIGHT);

        Graphics.blit(RenderPipelines.GUI_TEXTURED, NOISE_BAR, PosX, PosY, 0, 0, NOISE_WIDTH, NOISE_HEIGHT, NOISE_WIDTH, NOISE_HEIGHT);

        Graphics.disableScissor();
    }
}