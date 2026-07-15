package sqyro.classessmp.client;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import sqyro.classessmp.ClassesSMP;
import sqyro.classessmp.effect.ClassesEffects;

public class FreezeOverlay {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID,"textures/gui/freezing_overlay.png");

    public static void register() {
        HudRenderCallback.EVENT.register((graphics, tickDelta) -> {
            if (!Minecraft.getInstance().player.hasEffect(ClassesEffects.FREEZING)) {
                return;
            }

            graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, 0, 0, 0, 0, graphics.guiWidth(), graphics.guiHeight(), graphics.guiWidth(), graphics.guiHeight());
        });
    }
}