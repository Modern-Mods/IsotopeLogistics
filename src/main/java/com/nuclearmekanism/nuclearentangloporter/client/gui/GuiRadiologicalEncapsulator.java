package com.nuclearmekanism.nuclearentangloporter.client.gui;

import com.nuclearmekanism.nuclearentangloporter.client.recipeviewer.RadioisotopeRecipeViewerTypes;
import com.nuclearmekanism.nuclearentangloporter.content.tile.TileEntityRadioisotopeProcessor;
import mekanism.client.gui.element.bar.GuiVerticalPowerBar;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiChemicalGauge;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.client.gui.element.tab.GuiEnergyTab;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

/** Radiological Encapsulator layout. Keep its coordinates independent from other processors. */
public class GuiRadiologicalEncapsulator extends GuiRadioisotopeProcessor {

    public GuiRadiologicalEncapsulator(MekanismTileContainer<TileEntityRadioisotopeProcessor> container, Inventory inventory, Component title) {
        super(container, inventory, title);
        inventoryLabelX += 24; // bigger = right
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        addRenderableWidget(new GuiVerticalPowerBar(this, tile.getEnergyContainer(), 157, 23));
        addRenderableWidget(recipeProgress(ProgressType.LARGE_RIGHT, 65, 41, RadioisotopeRecipeViewerTypes.ENCAPSULATING));
        addRenderableWidget(new GuiEnergyTab(this, tile.getEnergyContainer(), tile::getActive));
        addRenderableWidget(new GuiChemicalGauge(tile::getChemicalInput, () -> tile.getChemicalTanks(null), GaugeType.STANDARD, this, 7, 17));
    }
}
