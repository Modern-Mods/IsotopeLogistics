package com.nuclearmekanism.nuclearentangloporter.content;

import com.nuclearmekanism.nuclearentangloporter.NuclearEntangloporter;
import com.nuclearmekanism.nuclearentangloporter.content.tile.TileEntityNuclearEntangloporter;
import com.nuclearmekanism.nuclearentangloporter.content.tile.TileEntityRadioactiveChemicalTank;
import com.nuclearmekanism.nuclearentangloporter.content.tile.TileEntityRadioisotopeProcessor;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.registration.impl.TileEntityTypeDeferredRegister;
import mekanism.common.registration.impl.TileEntityTypeRegistryObject;
import mekanism.common.tile.base.TileEntityMekanism;
import mekanism.common.tile.TileEntityBin;
import mekanism.common.tile.TileEntityFluidTank;

/**
 * Registers block entity types required by the add-on while preserving Mekanism's capability expectations.
 */
public class NuclearEntangloporterBlockEntityTypes {

    /** Deferred register scoped to the mod id for tile entity registration. */
    public static final TileEntityTypeDeferredRegister TILE_ENTITY_TYPES = new TileEntityTypeDeferredRegister(NuclearEntangloporter.MODID);

    /**
     * Block entity type that mirrors the Quantum Entangloporter while swapping in the radioactive-aware tile entity.
     */
    public static final TileEntityTypeRegistryObject<TileEntityNuclearEntangloporter> NUCLEAR_ENTANGLOPORTER =
          TILE_ENTITY_TYPES.mekBuilder(NuclearEntangloporterBlocks.NUCLEAR_ENTANGLOPORTER, TileEntityNuclearEntangloporter::new)
                // Mirror Mekanism's ticking behaviour so the frequency network and chunk loader update correctly.
                .clientTicker(TileEntityMekanism::tickClient).serverTicker(TileEntityMekanism::tickServer)
                // Allow configuration cards to copy the block's settings like the parent block.
                .withSimple(Capabilities.CONFIG_CARD)
                .build();

    public static final TileEntityTypeRegistryObject<TileEntityBin> BASIC_RADIOACTIVE_BIN = bin(NuclearEntangloporterBlocks.BASIC_RADIOACTIVE_BIN);
    public static final TileEntityTypeRegistryObject<TileEntityBin> ADVANCED_RADIOACTIVE_BIN = bin(NuclearEntangloporterBlocks.ADVANCED_RADIOACTIVE_BIN);
    public static final TileEntityTypeRegistryObject<TileEntityBin> ELITE_RADIOACTIVE_BIN = bin(NuclearEntangloporterBlocks.ELITE_RADIOACTIVE_BIN);
    public static final TileEntityTypeRegistryObject<TileEntityBin> ULTIMATE_RADIOACTIVE_BIN = bin(NuclearEntangloporterBlocks.ULTIMATE_RADIOACTIVE_BIN);
    public static final TileEntityTypeRegistryObject<TileEntityBin> CREATIVE_RADIOACTIVE_BIN = bin(NuclearEntangloporterBlocks.CREATIVE_RADIOACTIVE_BIN);

    public static final TileEntityTypeRegistryObject<TileEntityFluidTank> BASIC_RADIOACTIVE_FLUID_TANK = fluid(NuclearEntangloporterBlocks.BASIC_RADIOACTIVE_FLUID_TANK);
    public static final TileEntityTypeRegistryObject<TileEntityFluidTank> ADVANCED_RADIOACTIVE_FLUID_TANK = fluid(NuclearEntangloporterBlocks.ADVANCED_RADIOACTIVE_FLUID_TANK);
    public static final TileEntityTypeRegistryObject<TileEntityFluidTank> ELITE_RADIOACTIVE_FLUID_TANK = fluid(NuclearEntangloporterBlocks.ELITE_RADIOACTIVE_FLUID_TANK);
    public static final TileEntityTypeRegistryObject<TileEntityFluidTank> ULTIMATE_RADIOACTIVE_FLUID_TANK = fluid(NuclearEntangloporterBlocks.ULTIMATE_RADIOACTIVE_FLUID_TANK);
    public static final TileEntityTypeRegistryObject<TileEntityFluidTank> CREATIVE_RADIOACTIVE_FLUID_TANK = fluid(NuclearEntangloporterBlocks.CREATIVE_RADIOACTIVE_FLUID_TANK);

    public static final TileEntityTypeRegistryObject<TileEntityRadioactiveChemicalTank> BASIC_RADIOACTIVE_CHEMICAL_TANK = chemical(NuclearEntangloporterBlocks.BASIC_RADIOACTIVE_CHEMICAL_TANK);
    public static final TileEntityTypeRegistryObject<TileEntityRadioactiveChemicalTank> ADVANCED_RADIOACTIVE_CHEMICAL_TANK = chemical(NuclearEntangloporterBlocks.ADVANCED_RADIOACTIVE_CHEMICAL_TANK);
    public static final TileEntityTypeRegistryObject<TileEntityRadioactiveChemicalTank> ELITE_RADIOACTIVE_CHEMICAL_TANK = chemical(NuclearEntangloporterBlocks.ELITE_RADIOACTIVE_CHEMICAL_TANK);
    public static final TileEntityTypeRegistryObject<TileEntityRadioactiveChemicalTank> ULTIMATE_RADIOACTIVE_CHEMICAL_TANK = chemical(NuclearEntangloporterBlocks.ULTIMATE_RADIOACTIVE_CHEMICAL_TANK);
    public static final TileEntityTypeRegistryObject<TileEntityRadioactiveChemicalTank> CREATIVE_RADIOACTIVE_CHEMICAL_TANK = chemical(NuclearEntangloporterBlocks.CREATIVE_RADIOACTIVE_CHEMICAL_TANK);

    public static final TileEntityTypeRegistryObject<TileEntityRadioisotopeProcessor> RADIOLOGICAL_ENCAPSULATOR = processor(
          NuclearEntangloporterBlocks.RADIOLOGICAL_ENCAPSULATOR, RadioisotopeProcessorType.ENCAPSULATE, 40_000L);
    public static final TileEntityTypeRegistryObject<TileEntityRadioisotopeProcessor> ISOTOPIC_PHASE_CONTROLLER = processor(
          NuclearEntangloporterBlocks.ISOTOPIC_PHASE_CONTROLLER, RadioisotopeProcessorType.PHASE_CONTROL, 250_000L);
    public static final TileEntityTypeRegistryObject<TileEntityRadioisotopeProcessor> CHEMICAL_RECONSTITUTION_CHAMBER = processor(
          NuclearEntangloporterBlocks.CHEMICAL_RECONSTITUTION_CHAMBER, RadioisotopeProcessorType.RECONSTITUTE, 40_000L);

    private static TileEntityTypeRegistryObject<TileEntityBin> bin(mekanism.common.registration.impl.BlockRegistryObject<?, ?> block) {
        return TILE_ENTITY_TYPES.mekBuilder(block, (pos, state) -> new TileEntityBin(block, pos, state))
              .serverTicker(TileEntityMekanism::tickServer).withSimple(Capabilities.CONFIGURABLE).build();
    }

    private static TileEntityTypeRegistryObject<TileEntityFluidTank> fluid(mekanism.common.registration.impl.BlockRegistryObject<?, ?> block) {
        return TILE_ENTITY_TYPES.mekBuilder(block, (pos, state) -> new TileEntityFluidTank(block, pos, state))
              .clientTicker(TileEntityMekanism::tickClient).serverTicker(TileEntityMekanism::tickServer)
              .withSimple(Capabilities.CONFIG_CARD).withSimple(Capabilities.CONFIGURABLE).build();
    }

    private static TileEntityTypeRegistryObject<TileEntityRadioactiveChemicalTank> chemical(mekanism.common.registration.impl.BlockRegistryObject<?, ?> block) {
        return TILE_ENTITY_TYPES.mekBuilder(block, (pos, state) -> new TileEntityRadioactiveChemicalTank(block, pos, state))
              .serverTicker(TileEntityMekanism::tickServer).withSimple(Capabilities.CONFIG_CARD).build();
    }

    private static TileEntityTypeRegistryObject<TileEntityRadioisotopeProcessor> processor(mekanism.common.registration.impl.BlockRegistryObject<?, ?> block,
          RadioisotopeProcessorType type, long operationEnergy) {
        return TILE_ENTITY_TYPES.mekBuilder(block, (pos, state) -> new TileEntityRadioisotopeProcessor(block, pos, state, type, operationEnergy))
              .clientTicker(TileEntityMekanism::tickClient).serverTicker(TileEntityMekanism::tickServer)
              .withSimple(Capabilities.CONFIG_CARD).build();
    }

    private NuclearEntangloporterBlockEntityTypes() {
    }
}
