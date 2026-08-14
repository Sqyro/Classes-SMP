package sqyro.classessmp.client.GUI.screen;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import sqyro.classessmp.ClassesSMP;
import sqyro.classessmp.blocks.ClassesBlocks;
import sqyro.classessmp.client.GUI.CaseMenu;
import sqyro.classessmp.items.ClassesItems;
import sqyro.classessmp.network.cases.OpenCasePayload;

public class CaseScreen extends AbstractContainerScreen<CaseMenu> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/case_gui.png");

    public CaseScreen(CaseMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {
        graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, 256, 256);

        int ItemRenderDistX = 27;
        int ItemRenderDistY = 22;

        //BLUE
        ItemStack ironStack = new ItemStack(Items.IRON_INGOT);
        graphics.renderItem(ironStack, this.leftPos + 14, this.topPos + 7);
        ItemStack goldStack = new ItemStack(Items.GOLD_INGOT);
        graphics.renderItem(goldStack, this.leftPos + 13 + ItemRenderDistX, this.topPos + 7);
        ItemStack emeraldStack = new ItemStack(Items.EMERALD);
        graphics.renderItem(emeraldStack, this.leftPos + 14 + ItemRenderDistX * 2, this.topPos + 7);
        ItemStack copperStack = new ItemStack(Items.COPPER_INGOT);
        graphics.renderItem(copperStack, this.leftPos + 15 + ItemRenderDistX * 3, this.topPos + 7);

        //PURPLE
        ItemStack bossminerStack = new ItemStack(ClassesBlocks.BOSSMINER2000);
        graphics.renderItem(bossminerStack, this.leftPos + 14 + ItemRenderDistX * 4, this.topPos + 7);
        ItemStack diamondStack = new ItemStack(Items.DIAMOND);
        graphics.renderItem(diamondStack, this.leftPos + 15 + ItemRenderDistX * 5, this.topPos + 6);
        ItemStack caseKeyStack = new ItemStack(ClassesItems.CASE_KEY);
        graphics.renderItem(caseKeyStack, this.leftPos + 14, this.topPos + 7 + ItemRenderDistY);

        //PINK
        ItemStack upgradeTemplateStack = new ItemStack(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE);
        graphics.renderItem(upgradeTemplateStack, this.leftPos + 14 + ItemRenderDistX, this.topPos + 7 + ItemRenderDistY);
        ItemStack netheriteStack = new ItemStack(Items.NETHERITE_INGOT);
        graphics.renderItem(netheriteStack, this.leftPos + 14 + ItemRenderDistX * 2, this.topPos + 7 + ItemRenderDistY);

        //RED
        ItemStack enchantedGoldenAppleStack = new ItemStack(Items.ENCHANTED_GOLDEN_APPLE);
        graphics.renderItem(enchantedGoldenAppleStack, this.leftPos + 14 + ItemRenderDistX * 3, this.topPos + 7 + ItemRenderDistY);

    }

    @Override
    protected void init() {
        super.init();

        this.titleLabelY = 10000;

        this.addRenderableWidget(Button.builder(Component.literal("Unlock Container"), button -> {
            ClientPlayNetworking.send(new OpenCasePayload());
        }).bounds(this.leftPos + 77, this.topPos + 50, 92, 20).build());
    }
}