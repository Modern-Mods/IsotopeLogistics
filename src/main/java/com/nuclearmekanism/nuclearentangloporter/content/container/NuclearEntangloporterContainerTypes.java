package com.nuclearmekanism.nuclearentangloporter.content.container;

import com.nuclearmekanism.nuclearentangloporter.NuclearEntangloporter;
import com.nuclearmekanism.nuclearentangloporter.content.tile.TileEntityNuclearEntangloporter;
import com.nuclearmekanism.nuclearentangloporter.content.tile.TileEntityRadioactiveChemicalTank;
import com.nuclearmekanism.nuclearentangloporter.content.tile.TileEntityRadioisotopeProcessor;
import mekanism.common.tile.TileEntityBin;
import mekanism.common.tile.TileEntityFluidTank;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.registration.impl.ContainerTypeDeferredRegister;
import mekanism.common.registration.impl.ContainerTypeRegistryObject;

/**
 * Registers the menu type bound to the Nuclear Entangloporter tile so Mekanism's GUI layout can be reused without
 * requiring the original quantum tile class.
 */
public class NuclearEntangloporterContainerTypes {

    /** Deferred register scoped to the add-on for creating menu types. */
    public static final ContainerTypeDeferredRegister CONTAINER_TYPES =
          new ContainerTypeDeferredRegister(NuclearEntangloporter.MODID);

    /**
     * Menu type mirroring Mekanism's quantum entangloporter container offsets while targeting the nuclear tile entity.
     */
    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityNuclearEntangloporter>>
          NUCLEAR_ENTANGLOPORTER = CONTAINER_TYPES
                .custom("nuclear_entangloporter", TileEntityNuclearEntangloporter.class)
                .offset(0, 74)
                .build();

    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityRadioisotopeProcessor>> RADIOLOGICAL_ENCAPSULATOR =
          CONTAINER_TYPES.custom("radiological_encapsulator", TileEntityRadioisotopeProcessor.class).build();
    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityRadioisotopeProcessor>> ISOTOPIC_PHASE_CONTROLLER =
          CONTAINER_TYPES.custom("isotopic_phase_controller", TileEntityRadioisotopeProcessor.class).build();
    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityRadioisotopeProcessor>> CHEMICAL_RECONSTITUTION_CHAMBER =
          CONTAINER_TYPES.custom("chemical_reconstitution_chamber", TileEntityRadioisotopeProcessor.class).build();

    public static final ContainerTypeRegistryObject<RadioactiveBinContainer> RADIOACTIVE_BIN = CONTAINER_TYPES.register("radioactive_bin", TileEntityBin.class,
          (id, inv, tile) -> new RadioactiveBinContainer(id, inv, tile));
    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityFluidTank>> RADIOACTIVE_FLUID_TANK =
          CONTAINER_TYPES.custom("radioactive_fluid_tank", TileEntityFluidTank.class).armorSideBar().build();
    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityRadioactiveChemicalTank>> RADIOACTIVE_CHEMICAL_TANK =
          CONTAINER_TYPES.custom("radioactive_chemical_tank", TileEntityRadioactiveChemicalTank.class).armorSideBar().build();

    private NuclearEntangloporterContainerTypes() {
    }
}
