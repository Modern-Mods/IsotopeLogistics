package com.nuclearmekanism.nuclearentangloporter.content;

import com.nuclearmekanism.nuclearentangloporter.NuclearEntangloporterLang;
import com.nuclearmekanism.nuclearentangloporter.content.container.NuclearEntangloporterContainerTypes;
import com.nuclearmekanism.nuclearentangloporter.content.RadioisotopeProcessingBlockTypes;
import com.nuclearmekanism.nuclearentangloporter.content.tile.TileEntityNuclearEntangloporter;
import mekanism.common.block.attribute.AttributeUpgradeSupport;
import mekanism.common.block.attribute.Attributes;
import mekanism.common.block.attribute.AttributeStateFacing;
import mekanism.common.block.attribute.Attributes.AttributeSecurity;
import mekanism.common.block.attribute.Attributes.AttributeInventory;
import mekanism.common.block.attribute.Attributes.AttributeRedstone;
import mekanism.common.content.blocktype.BlockTypeTile;
import mekanism.common.content.blocktype.BlockTypeTile.BlockTileBuilder;
import mekanism.common.content.blocktype.BlockShapes;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.registries.MekanismSounds;
import mekanism.common.tile.component.config.DataType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

/**
 * Block type definition mirroring the Quantum Entangloporter with narrowed transmission support.
 */
public class NuclearEntangloporterBlockTypes {

    public static final BlockTypeTile<TileEntityNuclearEntangloporter> NUCLEAR_ENTANGLOPORTER = BlockTileBuilder
          .createBlock(() -> NuclearEntangloporterBlockEntityTypes.NUCLEAR_ENTANGLOPORTER,
                NuclearEntangloporterLang.DESCRIPTION_NUCLEAR_ENTANGLOPORTER)
          // Reuse Mekanism's container layout via the add-on specific menu registration so GUI interactions open correctly.
          .withGui(() -> NuclearEntangloporterContainerTypes.NUCLEAR_ENTANGLOPORTER)
          .withSound(MekanismSounds.CHEMICAL_DISSOLUTION_CHAMBER)
          .with(AttributeUpgradeSupport.ANCHOR_ONLY)
          .with(new AttributeStateFacing(BlockStateProperties.FACING), Attributes.INVENTORY, Attributes.SECURITY, Attributes.REDSTONE)
          .withSideConfig(TransmissionType.ITEM, TransmissionType.FLUID, TransmissionType.CHEMICAL, TransmissionType.ENERGY, TransmissionType.HEAT)
          .withCustomShape(BlockShapes.QUANTUM_ENTANGLOPORTER)
          .withComputerSupport("nuclearEntangloporter")
          .build();

    static {
        RadioisotopeProcessingBlockTypes.RADIOLOGICAL_ENCAPSULATOR.add(new mekanism.common.block.attribute.AttributeGui(
              () -> NuclearEntangloporterContainerTypes.RADIOLOGICAL_ENCAPSULATOR, null));
        RadioisotopeProcessingBlockTypes.ISOTOPIC_PHASE_CONTROLLER.add(new mekanism.common.block.attribute.AttributeGui(
              () -> NuclearEntangloporterContainerTypes.ISOTOPIC_PHASE_CONTROLLER, null));
        RadioisotopeProcessingBlockTypes.CHEMICAL_RECONSTITUTION_CHAMBER.add(new mekanism.common.block.attribute.AttributeGui(
              () -> NuclearEntangloporterContainerTypes.CHEMICAL_RECONSTITUTION_CHAMBER, null));
    }

    private NuclearEntangloporterBlockTypes() {
    }
}
