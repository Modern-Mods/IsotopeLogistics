package com.nuclearmekanism.nuclearentangloporter.client.gui;

import com.nuclearmekanism.nuclearentangloporter.content.tile.TileEntityRadioactiveChemicalTank;
import java.util.ArrayList;
import java.util.List;
import mekanism.api.chemical.IChemicalTank;
import mekanism.client.gui.GuiConfigurableTile;
import mekanism.client.gui.element.GuiInnerScreen;
import mekanism.client.gui.element.GuiSideHolder;
import mekanism.client.gui.element.bar.GuiChemicalBar;
import mekanism.client.gui.element.button.GuiGasMode;
import mekanism.client.gui.element.tab.GuiWarningTab;
import mekanism.common.MekanismLang;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.inventory.warning.IWarningTracker;
import mekanism.common.tier.ChemicalTankTier;
import mekanism.common.util.text.TextUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

/** Stock chemical-tank layout, bound to radioactive-tank tile type. */
public class GuiRadioactiveChemicalTank extends GuiConfigurableTile<TileEntityRadioactiveChemicalTank, MekanismTileContainer<TileEntityRadioactiveChemicalTank>> {

    public GuiRadioactiveChemicalTank(MekanismTileContainer<TileEntityRadioactiveChemicalTank> container, Inventory inventory, Component title) {
        super(container, inventory, title);
        dynamicSlots = true;
    }

    @Override
    protected void addGuiElements() {
        addRenderableWidget(GuiSideHolder.armorHolder(this));
        super.addGuiElements();
        addRenderableWidget(new GuiChemicalBar(this, GuiChemicalBar.getProvider(tile.getChemicalTank(), tile.getChemicalTanks(null)), 42, 16, 116, 10, true));
        addRenderableWidget(new GuiInnerScreen(this, 42, 37, 118, 28, () -> {
            List<Component> text = new ArrayList<>();
            IChemicalTank tank = tile.getChemicalTank();
            if (tank.isEmpty()) {
                text.add(MekanismLang.CHEMICAL.translate(MekanismLang.NONE));
                text.add(MekanismLang.GENERIC_FRACTION.translate(0, tile.getTier() == ChemicalTankTier.CREATIVE ? MekanismLang.INFINITE : TextUtils.format(tile.getTier().getStorage())));
            } else {
                text.add(MekanismLang.CHEMICAL.translate(tank.getStack()));
                text.add(tile.getTier() == ChemicalTankTier.CREATIVE ? MekanismLang.INFINITE.translate() :
                      MekanismLang.GENERIC_FRACTION.translate(TextUtils.format(tank.getStored()), TextUtils.format(tank.getCapacity())));
            }
            return text;
        }));
        addRenderableWidget(new GuiGasMode(this, 159, 72, true, () -> tile.dumping, tile.getBlockPos(), 0));
    }

    @Override
    protected void drawForegroundText(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        renderTitleText(guiGraphics);
        renderInventoryText(guiGraphics, 85);
        super.drawForegroundText(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void addWarningTab(IWarningTracker warningTracker) {
        addRenderableWidget(new GuiWarningTab(this, warningTracker, false));
    }
}
