package com.nuclearmekanism.nuclearentangloporter.client.gui;

import com.nuclearmekanism.nuclearentangloporter.content.frequency.NuclearFrequencyTypes;
import com.nuclearmekanism.nuclearentangloporter.content.frequency.NuclearInventoryFrequency;
import com.nuclearmekanism.nuclearentangloporter.content.tile.TileEntityNuclearEntangloporter;
import java.util.List;
import mekanism.client.gui.GuiConfigurableTile;
import mekanism.client.gui.element.custom.GuiFrequencySelector;
import mekanism.client.gui.element.custom.GuiFrequencySelector.ITileGuiFrequencySelector;
import mekanism.client.gui.element.tab.GuiEnergyTab;
import mekanism.client.gui.element.tab.GuiHeatTab;
import mekanism.common.MekanismLang;
import mekanism.common.lib.frequency.FrequencyType;

import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.inventory.warning.IWarningTracker;

import mekanism.common.util.MekanismUtils;
import mekanism.common.util.UnitDisplayUtils.TemperatureUnit;
import mekanism.common.util.text.EnergyDisplay;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

/**
 * Nuclear entangloporter GUI mirroring Mekanism's quantum interface while targeting the add-on tile entity.
 */
public class GuiNuclearEntangloporter extends GuiConfigurableTile<TileEntityNuclearEntangloporter, MekanismTileContainer<TileEntityNuclearEntangloporter>>
      implements ITileGuiFrequencySelector<NuclearInventoryFrequency, TileEntityNuclearEntangloporter> {

    public GuiNuclearEntangloporter(MekanismTileContainer<TileEntityNuclearEntangloporter> container, Inventory inv, Component title) {
        super(container, inv, title);
        imageHeight += 74;
        titleLabelY = 4;
        inventoryLabelY = imageHeight - 93;
        dynamicSlots = true;
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        addRenderableWidget(new GuiFrequencySelector<>(this, 14));
        addRenderableWidget(new GuiEnergyTab(this, () -> {
            NuclearInventoryFrequency frequency = getFrequency();
            EnergyDisplay storing = frequency == null ? EnergyDisplay.ZERO : EnergyDisplay.of(frequency.storedEnergy);
            EnergyDisplay rate = EnergyDisplay.of(tile.getInputRate());
            return List.of(MekanismLang.STORING.translate(storing), MekanismLang.MATRIX_INPUT_RATE.translate(rate));
        }));
        addRenderableWidget(new GuiHeatTab(this, () -> {
            Component transfer = MekanismUtils.getTemperatureDisplay(tile.getLastTransferLoss(), TemperatureUnit.KELVIN, false);
            Component environment = MekanismUtils.getTemperatureDisplay(tile.getLastEnvironmentLoss(), TemperatureUnit.KELVIN, false);
            return List.of(MekanismLang.TRANSFERRED_RATE.translate(transfer), MekanismLang.DISSIPATED_RATE.translate(environment));
        }));
    }

    @Override
    protected void drawForegroundText(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        renderTitleText(guiGraphics);
        renderInventoryText(guiGraphics);
        super.drawForegroundText(guiGraphics, mouseX, mouseY);
    }

    @Override
    public FrequencyType<NuclearInventoryFrequency> getFrequencyType() {
        return NuclearFrequencyTypes.NUCLEAR_INVENTORY;
    }

    @Override
    protected void addWarningTab(IWarningTracker warningTracker) {
        // Shift the warning tab to the right side to match Mekanism's layout and avoid overlapping the heat tab.
        addRenderableWidget(new mekanism.client.gui.element.tab.GuiWarningTab(this, warningTracker, false));
    }
}
