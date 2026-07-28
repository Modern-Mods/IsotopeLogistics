package com.nuclearmekanism.nuclearentangloporter.client.gui;

import com.nuclearmekanism.nuclearentangloporter.NuclearEntangloporterLang;
import com.nuclearmekanism.nuclearentangloporter.client.recipeviewer.RadioisotopeRecipeViewerTypes;
import com.nuclearmekanism.nuclearentangloporter.content.tile.TileEntityRadioisotopeProcessor;
import mekanism.client.gui.element.GuiInnerScreen;
import mekanism.client.gui.element.bar.GuiHorizontalPowerBar;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiChemicalGauge;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.client.gui.element.tab.GuiEnergyTab;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

/** Chemical Reconstitution Chamber layout. Keep its coordinates independent from other processors. */
public class GuiChemicalReconstitutionChamber extends GuiRadioisotopeProcessor {

    public GuiChemicalReconstitutionChamber(MekanismTileContainer<TileEntityRadioisotopeProcessor> container, Inventory inventory, Component title) {
        super(container, inventory, title);
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        addRenderableWidget(new GuiHorizontalPowerBar(this, tile.getEnergyContainer(), 87, 75));
        addRenderableWidget(recipeProgress(ProgressType.LARGE_RIGHT, 60, 40, RadioisotopeRecipeViewerTypes.RECONSTITUTING));
        addRenderableWidget(new GuiEnergyTab(this, tile.getEnergyContainer(), tile::getActive));
        addRenderableWidget(new GuiChemicalGauge(tile::getChemicalOutput, () -> tile.getChemicalTanks(null), GaugeType.STANDARD, this, 150, 17));
        addRenderableWidget(new GuiInnerScreen(this, 8, 18, 140, 16,
              () -> java.util.List.of(NuclearEntangloporterLang.RECONSTITUTION_PHASE_LOCKED.translate())));
    }
}
