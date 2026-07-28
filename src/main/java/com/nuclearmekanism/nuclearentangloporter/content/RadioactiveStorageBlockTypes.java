package com.nuclearmekanism.nuclearentangloporter.content;

import com.nuclearmekanism.nuclearentangloporter.NuclearEntangloporterLang;
import com.nuclearmekanism.nuclearentangloporter.content.tile.TileEntityRadioactiveChemicalTank;
import com.nuclearmekanism.nuclearentangloporter.content.container.NuclearEntangloporterContainerTypes;
import java.util.function.Supplier;
import mekanism.common.block.attribute.AttributeParticleFX;
import mekanism.common.block.attribute.AttributeStateActive;
import mekanism.common.block.attribute.AttributeStateFacing;
import mekanism.common.block.attribute.AttributeTier;
import mekanism.common.block.attribute.AttributeUpgradeSupport;
import mekanism.common.block.attribute.AttributeUpgradeable;
import mekanism.common.block.attribute.Attributes.AttributeRedstone;
import mekanism.common.block.attribute.Attributes.AttributeSecurity;
import mekanism.common.content.blocktype.BlockShapes;
import mekanism.common.content.blocktype.BlockTypeTile;
import mekanism.common.content.blocktype.Machine;
import mekanism.common.content.blocktype.Machine.MachineBuilder;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.registration.impl.BlockRegistryObject;
import mekanism.common.registration.impl.TileEntityTypeRegistryObject;
import mekanism.common.tier.BinTier;
import mekanism.common.tier.ChemicalTankTier;
import mekanism.common.tier.FluidTankTier;
import mekanism.common.tile.TileEntityBin;
import mekanism.common.tile.TileEntityFluidTank;

/** Tier metadata and Mekanism GUI/configuration behavior for the radioactive storage family. */
public final class RadioactiveStorageBlockTypes {

    public static final Machine<TileEntityBin> BASIC_RADIOACTIVE_BIN = bin(BinTier.BASIC, () -> NuclearEntangloporterBlockEntityTypes.BASIC_RADIOACTIVE_BIN, () -> NuclearEntangloporterBlocks.ADVANCED_RADIOACTIVE_BIN);
    public static final Machine<TileEntityBin> ADVANCED_RADIOACTIVE_BIN = bin(BinTier.ADVANCED, () -> NuclearEntangloporterBlockEntityTypes.ADVANCED_RADIOACTIVE_BIN, () -> NuclearEntangloporterBlocks.ELITE_RADIOACTIVE_BIN);
    public static final Machine<TileEntityBin> ELITE_RADIOACTIVE_BIN = bin(BinTier.ELITE, () -> NuclearEntangloporterBlockEntityTypes.ELITE_RADIOACTIVE_BIN, () -> NuclearEntangloporterBlocks.ULTIMATE_RADIOACTIVE_BIN);
    public static final Machine<TileEntityBin> ULTIMATE_RADIOACTIVE_BIN = bin(BinTier.ULTIMATE, () -> NuclearEntangloporterBlockEntityTypes.ULTIMATE_RADIOACTIVE_BIN, null);
    public static final Machine<TileEntityBin> CREATIVE_RADIOACTIVE_BIN = bin(BinTier.CREATIVE, () -> NuclearEntangloporterBlockEntityTypes.CREATIVE_RADIOACTIVE_BIN, null);

    public static final Machine<TileEntityFluidTank> BASIC_RADIOACTIVE_FLUID_TANK = fluid(FluidTankTier.BASIC, () -> NuclearEntangloporterBlockEntityTypes.BASIC_RADIOACTIVE_FLUID_TANK, () -> NuclearEntangloporterBlocks.ADVANCED_RADIOACTIVE_FLUID_TANK);
    public static final Machine<TileEntityFluidTank> ADVANCED_RADIOACTIVE_FLUID_TANK = fluid(FluidTankTier.ADVANCED, () -> NuclearEntangloporterBlockEntityTypes.ADVANCED_RADIOACTIVE_FLUID_TANK, () -> NuclearEntangloporterBlocks.ELITE_RADIOACTIVE_FLUID_TANK);
    public static final Machine<TileEntityFluidTank> ELITE_RADIOACTIVE_FLUID_TANK = fluid(FluidTankTier.ELITE, () -> NuclearEntangloporterBlockEntityTypes.ELITE_RADIOACTIVE_FLUID_TANK, () -> NuclearEntangloporterBlocks.ULTIMATE_RADIOACTIVE_FLUID_TANK);
    public static final Machine<TileEntityFluidTank> ULTIMATE_RADIOACTIVE_FLUID_TANK = fluid(FluidTankTier.ULTIMATE, () -> NuclearEntangloporterBlockEntityTypes.ULTIMATE_RADIOACTIVE_FLUID_TANK, null);
    public static final Machine<TileEntityFluidTank> CREATIVE_RADIOACTIVE_FLUID_TANK = fluid(FluidTankTier.CREATIVE, () -> NuclearEntangloporterBlockEntityTypes.CREATIVE_RADIOACTIVE_FLUID_TANK, null);

    public static final Machine<TileEntityRadioactiveChemicalTank> BASIC_RADIOACTIVE_CHEMICAL_TANK = chemical(ChemicalTankTier.BASIC, () -> NuclearEntangloporterBlockEntityTypes.BASIC_RADIOACTIVE_CHEMICAL_TANK, () -> NuclearEntangloporterBlocks.ADVANCED_RADIOACTIVE_CHEMICAL_TANK);
    public static final Machine<TileEntityRadioactiveChemicalTank> ADVANCED_RADIOACTIVE_CHEMICAL_TANK = chemical(ChemicalTankTier.ADVANCED, () -> NuclearEntangloporterBlockEntityTypes.ADVANCED_RADIOACTIVE_CHEMICAL_TANK, () -> NuclearEntangloporterBlocks.ELITE_RADIOACTIVE_CHEMICAL_TANK);
    public static final Machine<TileEntityRadioactiveChemicalTank> ELITE_RADIOACTIVE_CHEMICAL_TANK = chemical(ChemicalTankTier.ELITE, () -> NuclearEntangloporterBlockEntityTypes.ELITE_RADIOACTIVE_CHEMICAL_TANK, () -> NuclearEntangloporterBlocks.ULTIMATE_RADIOACTIVE_CHEMICAL_TANK);
    public static final Machine<TileEntityRadioactiveChemicalTank> ULTIMATE_RADIOACTIVE_CHEMICAL_TANK = chemical(ChemicalTankTier.ULTIMATE, () -> NuclearEntangloporterBlockEntityTypes.ULTIMATE_RADIOACTIVE_CHEMICAL_TANK, null);
    public static final Machine<TileEntityRadioactiveChemicalTank> CREATIVE_RADIOACTIVE_CHEMICAL_TANK = chemical(ChemicalTankTier.CREATIVE, () -> NuclearEntangloporterBlockEntityTypes.CREATIVE_RADIOACTIVE_CHEMICAL_TANK, null);

    private static <TILE extends TileEntityBin> Machine<TILE> bin(BinTier tier, Supplier<TileEntityTypeRegistryObject<TILE>> tile, Supplier<BlockRegistryObject<?, ?>> upgrade) {
        return MachineBuilder.createMachine(tile, NuclearEntangloporterLang.DESCRIPTION_RADIOACTIVE_BIN)
              .with(new AttributeTier<>(tier), new AttributeUpgradeable(upgrade))
              .without(AttributeParticleFX.class, AttributeSecurity.class, AttributeUpgradeSupport.class, AttributeRedstone.class)
              .withComputerSupport(tier, "RadioactiveBin").build();
    }

    private static <TILE extends TileEntityFluidTank> Machine<TILE> fluid(FluidTankTier tier, Supplier<TileEntityTypeRegistryObject<TILE>> tile, Supplier<BlockRegistryObject<?, ?>> upgrade) {
        return MachineBuilder.createMachine(tile, NuclearEntangloporterLang.DESCRIPTION_RADIOACTIVE_FLUID_TANK)
              .withGui(() -> NuclearEntangloporterContainerTypes.RADIOACTIVE_FLUID_TANK).withCustomShape(BlockShapes.FLUID_TANK)
              .with(new AttributeTier<>(tier), new AttributeUpgradeable(upgrade))
              .without(AttributeParticleFX.class, AttributeStateFacing.class, AttributeRedstone.class, AttributeUpgradeSupport.class)
              .withComputerSupport(tier, "RadioactiveFluidTank").build();
    }

    private static <TILE extends TileEntityRadioactiveChemicalTank> Machine<TILE> chemical(ChemicalTankTier tier, Supplier<TileEntityTypeRegistryObject<TILE>> tile, Supplier<BlockRegistryObject<?, ?>> upgrade) {
        return MachineBuilder.createMachine(tile, NuclearEntangloporterLang.DESCRIPTION_RADIOACTIVE_CHEMICAL_TANK)
              .withGui(() -> NuclearEntangloporterContainerTypes.RADIOACTIVE_CHEMICAL_TANK).withCustomShape(BlockShapes.CHEMICAL_TANK)
              .with(new AttributeTier<>(tier), new AttributeUpgradeable(upgrade)).withSideConfig(TransmissionType.CHEMICAL, TransmissionType.ITEM)
              .without(AttributeParticleFX.class, AttributeStateActive.class, AttributeUpgradeSupport.class)
              .withComputerSupport(tier, "RadioactiveChemicalTank").build();
    }

    private RadioactiveStorageBlockTypes() {
    }
}
