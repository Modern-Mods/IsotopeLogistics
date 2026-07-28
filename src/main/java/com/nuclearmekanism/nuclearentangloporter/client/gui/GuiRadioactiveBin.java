package com.nuclearmekanism.nuclearentangloporter.client.gui;

import com.nuclearmekanism.nuclearentangloporter.NuclearEntangloporterLang;
import com.nuclearmekanism.nuclearentangloporter.content.container.RadioactiveBinContainer;
import java.util.List;
import mekanism.client.gui.GuiMekanismTile;
import mekanism.client.gui.element.GuiInnerScreen;
import mekanism.common.MekanismLang;
import mekanism.common.tile.TileEntityBin;
import mekanism.common.util.text.TextUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/** Small bin screen: slot handles normal item insertion/extraction while screen reports total stored count. */
public class GuiRadioactiveBin extends GuiMekanismTile<TileEntityBin, RadioactiveBinContainer> {

    public GuiRadioactiveBin(RadioactiveBinContainer container, Inventory inventory, Component title) {
        super(container, inventory, title);
        dynamicSlots = true;
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        addRenderableWidget(new GuiInnerScreen(this, 27, 18, 122, 16, () -> {
            ItemStack stack = tile.getBinSlot().getStack();
            return List.of(stack.isEmpty() ? MekanismLang.EMPTY.translate() : NuclearEntangloporterLang.BIN_STORED.translate(TextUtils.format(stack.getCount())));
        }));
    }

    @Override
    protected void drawForegroundText(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        renderTitleText(guiGraphics);
        renderInventoryText(guiGraphics);
        super.drawForegroundText(guiGraphics, mouseX, mouseY);
    }
}
