package com.nuclearmekanism.nuclearentangloporter.client.gui;

import com.nuclearmekanism.nuclearentangloporter.NuclearEntangloporterLang;
import com.nuclearmekanism.nuclearentangloporter.client.recipeviewer.RadioisotopeRecipeViewerTypes;
import com.nuclearmekanism.nuclearentangloporter.content.tile.TileEntityRadioisotopeProcessor;
import mekanism.client.gui.element.GuiInnerScreen;
import mekanism.client.gui.element.button.ToggleButton;
import mekanism.client.gui.element.bar.GuiVerticalPowerBar;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.client.gui.element.tab.GuiEnergyTab;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.network.PacketUtils;
import mekanism.common.network.to_server.PacketGuiInteract;
import mekanism.common.network.to_server.PacketGuiInteract.GuiInteraction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

/** Isotopic Phase Controller layout and mode-toggle control. */
public class GuiIsotopicPhaseController extends GuiRadioisotopeProcessor {

    public GuiIsotopicPhaseController(MekanismTileContainer<TileEntityRadioisotopeProcessor> container, Inventory inventory, Component title) {
        super(container, inventory, title);
        titleLabelY = 4;
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        addRenderableWidget(new GuiVerticalPowerBar(this, tile.getEnergyContainer(), 164, 16));
        addRenderableWidget(recipeProgress(ProgressType.BAR, 76, 38, RadioisotopeRecipeViewerTypes.PHASE_CONTROLLING));
        addRenderableWidget(new GuiEnergyTab(this, tile.getEnergyContainer(), tile::getActive));
        addRenderableWidget(new GuiInnerScreen(this, 30, 18, 116, 16, () -> java.util.List.of(
              NuclearEntangloporterLang.PHASE_MODE.translate(tile.isExciteMode() ? NuclearEntangloporterLang.EXCITE.translate() : NuclearEntangloporterLang.STABILIZE.translate()))));
        addRenderableWidget(new ToggleButton(this, 4, 4, tile::isExciteMode,
              (element, mouseX, mouseY) -> PacketUtils.sendToServer(new PacketGuiInteract(GuiInteraction.NEXT_MODE, tile)))
              .setTooltip(NuclearEntangloporterLang.PHASE_MODE_SWITCH));
    }
}
