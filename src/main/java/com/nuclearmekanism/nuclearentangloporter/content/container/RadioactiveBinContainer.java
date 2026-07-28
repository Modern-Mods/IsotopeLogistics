package com.nuclearmekanism.nuclearentangloporter.content.container;

import mekanism.common.inventory.container.slot.ContainerSlotType;
import mekanism.common.inventory.container.slot.InventoryContainerSlot;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.tile.TileEntityBin;
import net.minecraft.world.entity.player.Inventory;

/** Gives bins one normal menu slot; Mekanism deliberately omits this slot from its no-GUI bin container. */
public class RadioactiveBinContainer extends MekanismTileContainer<TileEntityBin> {

    public RadioactiveBinContainer(int id, Inventory inventory, TileEntityBin tile) {
        super(NuclearEntangloporterContainerTypes.RADIOACTIVE_BIN, id, inventory, tile);
    }

    @Override
    protected void addSlots() {
        super.addSlots();
        addSlot(new InventoryContainerSlot(tile.getBinSlot(), 80, 36, ContainerSlotType.NORMAL, null, null, tile.getBinSlot()::setStackUnchecked));
    }
}
