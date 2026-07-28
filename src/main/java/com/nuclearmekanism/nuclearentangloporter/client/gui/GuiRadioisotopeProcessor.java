package com.nuclearmekanism.nuclearentangloporter.client.gui;

import com.nuclearmekanism.nuclearentangloporter.NuclearEntangloporterLang;
import com.nuclearmekanism.nuclearentangloporter.content.tile.TileEntityRadioisotopeProcessor;
import mekanism.client.gui.GuiConfigurableTile;
import mekanism.client.gui.element.progress.GuiProgress;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

/** Shared recipe-button and foreground behavior for independently laid-out processor screens. */
public abstract class GuiRadioisotopeProcessor extends GuiConfigurableTile<TileEntityRadioisotopeProcessor, MekanismTileContainer<TileEntityRadioisotopeProcessor>> {

    public GuiRadioisotopeProcessor(MekanismTileContainer<TileEntityRadioisotopeProcessor> container, Inventory inventory, Component title) {
        super(container, inventory, title);
        dynamicSlots = true;
    }

    /** Addon category identities make JEI open this machine's recipes instead of its borrowed Mekanism model source. */
    protected final GuiProgress recipeProgress(ProgressType type, int x, int y, IRecipeViewerRecipeType<?> category) {
        GuiProgress progress = new GuiProgress(tile::getScaledProgress, type, this, x, y).recipeViewerCategories(category);
        progress.setTooltip(NuclearEntangloporterLang.RECIPE_VIEWER);
        return progress;
    }

    @Override
    protected void drawForegroundText(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        renderTitleText(guiGraphics);
        renderInventoryText(guiGraphics);
        super.drawForegroundText(guiGraphics, mouseX, mouseY);
    }
}
