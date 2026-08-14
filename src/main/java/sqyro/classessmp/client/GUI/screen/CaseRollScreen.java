package sqyro.classessmp.client.GUI.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import sqyro.classessmp.ClassesSMP;
import sqyro.classessmp.blocks.CaseLootTable;
import sqyro.classessmp.blocks.ClassesBlocks;
import sqyro.classessmp.items.ClassesItems;

import java.util.ArrayList;
import java.util.List;

public class CaseRollScreen extends Screen {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/case_roll_gui.png");

    private static final Identifier BLUE_BG = Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/blue_bg_texture.png");
    private static final Identifier PURPLE_BG = Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/purple_bg_texture.png");
    private static final Identifier PINK_BG = Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/pink_bg_texture.png");
    private static final Identifier RED_BG = Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/red_bg_texture.png");
    private static final Identifier GOLD_BG = Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "textures/gui/gold_bg_texture.png");

    private static final int ITEM_SIZE = 80;
    private static final int ITEM_COUNT = 40;

    private static final int REWARD_INDEX = 30;
    private static final int ANIMATION_TICKS = 100;

    private static final int ITEM_DISTANCE = 133;

    private final ItemStack reward;

    private final List<ScrollingItem> items = new ArrayList<>();

    private int animationTick = 0;

    private boolean finished;

    private ScrollingItem lastClosestItem;

    public CaseRollScreen(ItemStack reward) {
        super(Component.literal("GOLD GOLD GOLD"));
        this.reward = reward.copy();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    protected void init() {
        super.init();

        items.clear();

        int middleY = height / 2 - ITEM_SIZE / 2;
        double centerX = width / 2.0;

        for (int i = 0; i < ITEM_COUNT; i++) {
            ItemStack stack;

            if (i == REWARD_INDEX) {
                stack = reward.copy();
            } else {
                stack = createVisualRollItem();
            }

            items.add(new ScrollingItem(stack, centerX + i * ITEM_DISTANCE, middleY));
        }
    }

    private ItemStack createVisualRollItem() {
        if (minecraft == null || minecraft.level == null) {
            return new ItemStack(Items.IRON_INGOT);
        }

        return CaseLootTable.roll(minecraft.level.getRandom());
    }

    @Override
    public void tick() {
        super.tick();

        if (finished) {
            return;
        }

        animationTick++;

        double progress = Math.min(1.0, animationTick / (double) ANIMATION_TICKS);
        double eased = 1.0 - Math.pow(1.0 - progress, 3.0);
        double startingOffset = 350.0;

        double totalTravel = startingOffset + REWARD_INDEX * ITEM_DISTANCE;

        double travel = totalTravel * eased;

        for (int i = 0; i < items.size(); i++) {
            ScrollingItem item = items.get(i);

            item.x = width / 2.0 + startingOffset + i * ITEM_DISTANCE - travel;
        }

        double centerX = width / 2.0;

        ScrollingItem closest = null;

        double closestDistance = Double.MAX_VALUE;

        for (ScrollingItem item : items) {
            double distance = Math.abs(item.x - centerX);

            if (distance < closestDistance) {
                closestDistance = distance;
                closest = item;
            }
        }

        if (closest != null && closest != lastClosestItem) {
            lastClosestItem = closest;

            minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        }

        if (progress >= 1.0) {
            finishAnimation();
        }
    }

    private void finishAnimation() {
        if (finished) {
            return;
        }

        double centerX = width / 2.0;

        for (int i = 0; i < items.size(); i++) {
            ScrollingItem item = items.get(i);
            item.x = centerX + (i - REWARD_INDEX) * ITEM_DISTANCE;
        }

        playRewardSound();
        finished = true;
    }

    private void playRewardSound() {
        Item item = reward.getItem();

        if (item == Items.ENCHANTED_GOLDEN_APPLE) {
            minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.TOTEM_USE, 1.0F));
        } else if (item == ClassesItems.REMOVE_CLASS_ITEM) {
            minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, 1.0F));
        } else {
            minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.VILLAGER_CELEBRATE, 1.0F));
        }
    }

    private Identifier getBackgroundTexture(ItemStack stack) {
        Item item = stack.getItem();

        if (item == Items.IRON_INGOT || item == Items.GOLD_INGOT || item == Items.EMERALD || item == Items.COPPER_INGOT) {
            return BLUE_BG;
        }

        if (item == ClassesBlocks.BOSSMINER2000.asItem() || item == Items.DIAMOND || item == ClassesItems.CASE_KEY) {
            return PURPLE_BG;
        }

        if (item == Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE || item == Items.NETHERITE_INGOT) {
            return PINK_BG;
        }

        if (item == Items.ENCHANTED_GOLDEN_APPLE) {
            return RED_BG;
        }

        if (item == ClassesItems.REMOVE_CLASS_ITEM) {
            return GOLD_BG;
        }

        return BLUE_BG;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        int guiX = (width - 176) / 2;
        int guiY = (height - 166) / 2;

        graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, guiX, guiY, 0, 0, 176, 166, 256, 256);

        for (ScrollingItem item : items) {
            renderScrollingItem(graphics, item);
        }

        int centerX = width / 2;

        int lineHeight = 17 * ITEM_SIZE / 16;
        int lineY = height / 2 - lineHeight / 2 + 2;

        graphics.fill(centerX - 1, lineY, centerX + 1, lineY + lineHeight, 0xFFD1DD0E);

        super.render(graphics, mouseX, mouseY, partialTick);
    }

    private void renderScrollingItem(GuiGraphics graphics, ScrollingItem item) {
        int bgWidth = 120;
        int bgHeight = 85;

        int bgX = (int) item.x - bgWidth / 2;
        int bgY = item.y - 2;

        graphics.blit(RenderPipelines.GUI_TEXTURED, getBackgroundTexture(item.stack), bgX, bgY, 0, 0, bgWidth, bgHeight, bgWidth, bgHeight);

        graphics.pose().pushMatrix();
        graphics.pose().translate((float) item.x - ITEM_SIZE / 2.0F, (float) item.y);

        graphics.pose().scale(5.0F, 5.0F);

        if (item.stack.getItem() != ClassesItems.REMOVE_CLASS_ITEM) {
            graphics.renderItem(item.stack, 0, 0);
        }

        graphics.pose().popMatrix();
    }

    private static class ScrollingItem {
        private final ItemStack stack;

        private double x;
        private final int y;

        private ScrollingItem(ItemStack stack, double x, int y) {
            this.stack = stack;
            this.x = x;
            this.y = y;
        }
    }
}
