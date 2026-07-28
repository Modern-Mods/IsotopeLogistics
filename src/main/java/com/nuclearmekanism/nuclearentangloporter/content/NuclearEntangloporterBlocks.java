package com.nuclearmekanism.nuclearentangloporter.content;

import com.nuclearmekanism.nuclearentangloporter.NuclearEntangloporter;
import com.nuclearmekanism.nuclearentangloporter.content.item.ItemBlockNuclearEntangloporter;
import com.nuclearmekanism.nuclearentangloporter.content.item.ItemBlockRadioactiveChemicalTank;
import com.nuclearmekanism.nuclearentangloporter.content.tile.TileEntityNuclearEntangloporter;
import com.nuclearmekanism.nuclearentangloporter.content.tile.TileEntityRadioactiveChemicalTank;
import com.nuclearmekanism.nuclearentangloporter.content.tile.TileEntityRadioisotopeProcessor;
import com.nuclearmekanism.nuclearentangloporter.content.block.BlockIsotopicPhaseController;
import com.nuclearmekanism.nuclearentangloporter.content.block.BlockRadioactiveBin;
import java.util.Map;
import mekanism.common.attachments.component.AttachedEjector;
import mekanism.common.attachments.component.AttachedSideConfig;
import mekanism.common.attachments.component.AttachedSideConfig.LightConfigInfo;
import mekanism.common.attachments.containers.ContainerType;
import mekanism.common.attachments.containers.chemical.ChemicalTanksBuilder;
import mekanism.common.attachments.containers.fluid.ComponentBackedFluidTankFluidTank;
import mekanism.common.attachments.containers.fluid.FluidTanksBuilder;
import mekanism.common.attachments.containers.item.ComponentBackedBinInventorySlot;
import mekanism.common.attachments.containers.item.ItemSlotsBuilder;
import mekanism.common.block.basic.BlockBin;
import mekanism.common.block.basic.BlockFluidTank;
import mekanism.common.block.prefab.BlockTile;
import mekanism.common.block.prefab.BlockTile.BlockTileModel;
import mekanism.common.content.blocktype.Machine;
import mekanism.common.content.blocktype.BlockTypeTile;
import mekanism.common.item.block.ItemBlockBin;
import mekanism.common.item.block.machine.ItemBlockFluidTank;
import mekanism.common.item.block.ItemBlockTooltip;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.registration.impl.BlockDeferredRegister;
import mekanism.common.registration.impl.BlockRegistryObject;
import mekanism.common.registries.MekanismDataComponents;
import mekanism.common.resource.BlockResourceInfo;
import mekanism.common.tile.TileEntityBin;
import mekanism.common.tile.TileEntityFluidTank;

/**
 * Registers the Nuclear Entangloporter block and item by mirroring Mekanism's tile registration helpers.
 */
public class NuclearEntangloporterBlocks {

    /** Deferred register that hooks into Mekanism's block+item wrapper utilities. */
    public static final BlockDeferredRegister BLOCKS = new BlockDeferredRegister(NuclearEntangloporter.MODID);
    // All processor ports stay pipe-accessible by default; tank/slot predicates still enforce each machine's role.
    private static final AttachedSideConfig PROCESSOR_SIDE_CONFIG = new AttachedSideConfig(Map.of(
          TransmissionType.ITEM, LightConfigInfo.INPUT_OUT_ALL,
          TransmissionType.CHEMICAL, LightConfigInfo.INPUT_OUT_ALL,
          TransmissionType.ENERGY, LightConfigInfo.INPUT_ONLY
    ));

    /**
     * Block registry entry that reuses the Mekanism Quantum Entangloporter model/item wrapper so the clone stays feature-par.
     */
    public static final BlockRegistryObject<BlockTileModel<TileEntityNuclearEntangloporter, BlockTypeTile<TileEntityNuclearEntangloporter>>, ItemBlockNuclearEntangloporter>
          NUCLEAR_ENTANGLOPORTER = BLOCKS.register("nuclear_entangloporter",
                () -> new BlockTileModel<>(NuclearEntangloporterBlockTypes.NUCLEAR_ENTANGLOPORTER,
                      properties -> properties.mapColor(BlockResourceInfo.STEEL.getMapColor())),
                ItemBlockNuclearEntangloporter::new);

    public static final BlockRegistryObject<BlockRadioactiveBin, ItemBlockBin> BASIC_RADIOACTIVE_BIN = bin("basic_radioactive_bin", RadioactiveStorageBlockTypes.BASIC_RADIOACTIVE_BIN);
    public static final BlockRegistryObject<BlockRadioactiveBin, ItemBlockBin> ADVANCED_RADIOACTIVE_BIN = bin("advanced_radioactive_bin", RadioactiveStorageBlockTypes.ADVANCED_RADIOACTIVE_BIN);
    public static final BlockRegistryObject<BlockRadioactiveBin, ItemBlockBin> ELITE_RADIOACTIVE_BIN = bin("elite_radioactive_bin", RadioactiveStorageBlockTypes.ELITE_RADIOACTIVE_BIN);
    public static final BlockRegistryObject<BlockRadioactiveBin, ItemBlockBin> ULTIMATE_RADIOACTIVE_BIN = bin("ultimate_radioactive_bin", RadioactiveStorageBlockTypes.ULTIMATE_RADIOACTIVE_BIN);
    public static final BlockRegistryObject<BlockRadioactiveBin, ItemBlockBin> CREATIVE_RADIOACTIVE_BIN = bin("creative_radioactive_bin", RadioactiveStorageBlockTypes.CREATIVE_RADIOACTIVE_BIN);

    public static final BlockRegistryObject<BlockFluidTank, ItemBlockFluidTank> BASIC_RADIOACTIVE_FLUID_TANK = fluid("basic_radioactive_fluid_tank", RadioactiveStorageBlockTypes.BASIC_RADIOACTIVE_FLUID_TANK);
    public static final BlockRegistryObject<BlockFluidTank, ItemBlockFluidTank> ADVANCED_RADIOACTIVE_FLUID_TANK = fluid("advanced_radioactive_fluid_tank", RadioactiveStorageBlockTypes.ADVANCED_RADIOACTIVE_FLUID_TANK);
    public static final BlockRegistryObject<BlockFluidTank, ItemBlockFluidTank> ELITE_RADIOACTIVE_FLUID_TANK = fluid("elite_radioactive_fluid_tank", RadioactiveStorageBlockTypes.ELITE_RADIOACTIVE_FLUID_TANK);
    public static final BlockRegistryObject<BlockFluidTank, ItemBlockFluidTank> ULTIMATE_RADIOACTIVE_FLUID_TANK = fluid("ultimate_radioactive_fluid_tank", RadioactiveStorageBlockTypes.ULTIMATE_RADIOACTIVE_FLUID_TANK);
    public static final BlockRegistryObject<BlockFluidTank, ItemBlockFluidTank> CREATIVE_RADIOACTIVE_FLUID_TANK = fluid("creative_radioactive_fluid_tank", RadioactiveStorageBlockTypes.CREATIVE_RADIOACTIVE_FLUID_TANK);

    public static final BlockRegistryObject<BlockTileModel<TileEntityRadioactiveChemicalTank, Machine<TileEntityRadioactiveChemicalTank>>, ItemBlockRadioactiveChemicalTank>
          BASIC_RADIOACTIVE_CHEMICAL_TANK = chemical("basic_radioactive_chemical_tank", RadioactiveStorageBlockTypes.BASIC_RADIOACTIVE_CHEMICAL_TANK);
    public static final BlockRegistryObject<BlockTileModel<TileEntityRadioactiveChemicalTank, Machine<TileEntityRadioactiveChemicalTank>>, ItemBlockRadioactiveChemicalTank>
          ADVANCED_RADIOACTIVE_CHEMICAL_TANK = chemical("advanced_radioactive_chemical_tank", RadioactiveStorageBlockTypes.ADVANCED_RADIOACTIVE_CHEMICAL_TANK);
    public static final BlockRegistryObject<BlockTileModel<TileEntityRadioactiveChemicalTank, Machine<TileEntityRadioactiveChemicalTank>>, ItemBlockRadioactiveChemicalTank>
          ELITE_RADIOACTIVE_CHEMICAL_TANK = chemical("elite_radioactive_chemical_tank", RadioactiveStorageBlockTypes.ELITE_RADIOACTIVE_CHEMICAL_TANK);
    public static final BlockRegistryObject<BlockTileModel<TileEntityRadioactiveChemicalTank, Machine<TileEntityRadioactiveChemicalTank>>, ItemBlockRadioactiveChemicalTank>
          ULTIMATE_RADIOACTIVE_CHEMICAL_TANK = chemical("ultimate_radioactive_chemical_tank", RadioactiveStorageBlockTypes.ULTIMATE_RADIOACTIVE_CHEMICAL_TANK);
    public static final BlockRegistryObject<BlockTileModel<TileEntityRadioactiveChemicalTank, Machine<TileEntityRadioactiveChemicalTank>>, ItemBlockRadioactiveChemicalTank>
          CREATIVE_RADIOACTIVE_CHEMICAL_TANK = chemical("creative_radioactive_chemical_tank", RadioactiveStorageBlockTypes.CREATIVE_RADIOACTIVE_CHEMICAL_TANK);

    public static final BlockRegistryObject<BlockTileModel<TileEntityRadioisotopeProcessor, Machine<TileEntityRadioisotopeProcessor>>, ItemBlockTooltip<BlockTileModel<TileEntityRadioisotopeProcessor, Machine<TileEntityRadioisotopeProcessor>>>>
          RADIOLOGICAL_ENCAPSULATOR = processor("radiological_encapsulator", RadioisotopeProcessingBlockTypes.RADIOLOGICAL_ENCAPSULATOR);
    public static final BlockRegistryObject<BlockIsotopicPhaseController, ItemBlockTooltip<BlockIsotopicPhaseController>>
          ISOTOPIC_PHASE_CONTROLLER = BLOCKS.register("isotopic_phase_controller", () -> new BlockIsotopicPhaseController(
                RadioisotopeProcessingBlockTypes.ISOTOPIC_PHASE_CONTROLLER, properties -> properties.mapColor(BlockResourceInfo.STEEL.getMapColor())),
                NuclearEntangloporterBlocks::processorItem);
    public static final BlockRegistryObject<BlockTileModel<TileEntityRadioisotopeProcessor, Machine<TileEntityRadioisotopeProcessor>>, ItemBlockTooltip<BlockTileModel<TileEntityRadioisotopeProcessor, Machine<TileEntityRadioisotopeProcessor>>>>
          CHEMICAL_RECONSTITUTION_CHAMBER = processor("chemical_reconstitution_chamber", RadioisotopeProcessingBlockTypes.CHEMICAL_RECONSTITUTION_CHAMBER);

    private static BlockRegistryObject<BlockRadioactiveBin, ItemBlockBin> bin(String name, Machine<TileEntityBin> type) {
        return BLOCKS.register(name, () -> new BlockRadioactiveBin(type, properties -> properties.mapColor(type.get(mekanism.common.block.attribute.AttributeTier.class).tier().getBaseTier().getMapColor())), ItemBlockBin::new)
              .forItemHolder(holder -> holder.addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder().addSlot(ComponentBackedBinInventorySlot::create).build()));
    }

    public static boolean isRadioactiveBin(net.minecraft.world.level.block.Block block) {
        return block == BASIC_RADIOACTIVE_BIN.get() || block == ADVANCED_RADIOACTIVE_BIN.get() || block == ELITE_RADIOACTIVE_BIN.get() ||
               block == ULTIMATE_RADIOACTIVE_BIN.get() || block == CREATIVE_RADIOACTIVE_BIN.get();
    }

    private static BlockRegistryObject<BlockFluidTank, ItemBlockFluidTank> fluid(String name, Machine<TileEntityFluidTank> type) {
        return BLOCKS.register(name, () -> new BlockFluidTank(type), ItemBlockFluidTank::new).forItemHolder(holder -> holder
              .addAttachedContainerCapabilities(ContainerType.FLUID, () -> FluidTanksBuilder.builder().addTank(ComponentBackedFluidTankFluidTank::create).build())
              .addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder().addFluidInputSlot(0).addOutput().build()));
    }

    private static BlockRegistryObject<BlockTileModel<TileEntityRadioactiveChemicalTank, Machine<TileEntityRadioactiveChemicalTank>>, ItemBlockRadioactiveChemicalTank> chemical(String name,
          Machine<TileEntityRadioactiveChemicalTank> type) {
        return BLOCKS.register(name, () -> new BlockTileModel<>(type, properties -> properties.mapColor(type.get(mekanism.common.block.attribute.AttributeTier.class).tier().getBaseTier().getMapColor())), ItemBlockRadioactiveChemicalTank::new)
              .forItemHolder(holder -> holder.addAttachedContainerCapabilities(ContainerType.CHEMICAL, () -> ChemicalTanksBuilder.builder()
                    .addTank(com.nuclearmekanism.nuclearentangloporter.content.chemical.RadioactiveComponentBackedChemicalTank::create).build())
                    .addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder().addChemicalDrainSlot(0).addChemicalFillSlot(0).build()));
    }

    private static BlockRegistryObject<BlockTileModel<TileEntityRadioisotopeProcessor, Machine<TileEntityRadioisotopeProcessor>>, ItemBlockTooltip<BlockTileModel<TileEntityRadioisotopeProcessor, Machine<TileEntityRadioisotopeProcessor>>>> processor(
          String name, Machine<TileEntityRadioisotopeProcessor> type) {
        return BLOCKS.register(name, () -> new BlockTileModel<>(type, properties -> properties.mapColor(BlockResourceInfo.STEEL.getMapColor())),
              NuclearEntangloporterBlocks::processorItem);
    }

    private static <BLOCK extends net.minecraft.world.level.block.Block & mekanism.common.block.interfaces.IHasDescription> ItemBlockTooltip<BLOCK> processorItem(
          BLOCK block, net.minecraft.world.item.Item.Properties properties) {
        return new ItemBlockTooltip<>(block, true, properties
              .component(MekanismDataComponents.EJECTOR, AttachedEjector.DEFAULT)
              .component(MekanismDataComponents.SIDE_CONFIG, PROCESSOR_SIDE_CONFIG));
    }

    private NuclearEntangloporterBlocks() {
    }
}
