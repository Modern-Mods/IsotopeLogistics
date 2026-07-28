package com.nuclearmekanism.nuclearentangloporter.content;

import com.nuclearmekanism.nuclearentangloporter.NuclearEntangloporter;
import com.nuclearmekanism.nuclearentangloporter.NuclearEntangloporterLang;
import mekanism.common.registration.impl.CreativeTabDeferredRegister;
import net.neoforged.fml.ModList;

/** The add-on's dedicated creative tab, keeping its machines out of vanilla Functional Blocks. */
public final class NuclearEntangloporterCreativeTabs {

    public static final CreativeTabDeferredRegister TABS = new CreativeTabDeferredRegister(NuclearEntangloporter.MODID);

    public static final mekanism.common.registration.MekanismDeferredHolder<net.minecraft.world.item.CreativeModeTab, net.minecraft.world.item.CreativeModeTab> NUCLEAR_ENTANGLOPORTER =
          TABS.registerMain(NuclearEntangloporterLang.CREATIVE_TAB, NuclearEntangloporterBlocks.NUCLEAR_ENTANGLOPORTER.getItemHolder(), builder -> builder
                .displayItems((parameters, output) -> {
                      CreativeTabDeferredRegister.addToDisplay(output,
                      NuclearEntangloporterBlocks.NUCLEAR_ENTANGLOPORTER,
                      NuclearEntangloporterBlocks.BASIC_RADIOACTIVE_CHEMICAL_TANK,
                      NuclearEntangloporterBlocks.ADVANCED_RADIOACTIVE_CHEMICAL_TANK,
                      NuclearEntangloporterBlocks.ELITE_RADIOACTIVE_CHEMICAL_TANK,
                      NuclearEntangloporterBlocks.ULTIMATE_RADIOACTIVE_CHEMICAL_TANK,
                      NuclearEntangloporterBlocks.CREATIVE_RADIOACTIVE_CHEMICAL_TANK,
                      NuclearEntangloporterBlocks.BASIC_RADIOACTIVE_FLUID_TANK,
                      NuclearEntangloporterBlocks.ADVANCED_RADIOACTIVE_FLUID_TANK,
                      NuclearEntangloporterBlocks.ELITE_RADIOACTIVE_FLUID_TANK,
                      NuclearEntangloporterBlocks.ULTIMATE_RADIOACTIVE_FLUID_TANK,
                      NuclearEntangloporterBlocks.CREATIVE_RADIOACTIVE_FLUID_TANK,
                      NuclearEntangloporterBlocks.BASIC_RADIOACTIVE_BIN,
                      NuclearEntangloporterBlocks.ADVANCED_RADIOACTIVE_BIN,
                      NuclearEntangloporterBlocks.ELITE_RADIOACTIVE_BIN,
                      NuclearEntangloporterBlocks.ULTIMATE_RADIOACTIVE_BIN,
                      NuclearEntangloporterBlocks.CREATIVE_RADIOACTIVE_BIN);
                      CreativeTabDeferredRegister.addToDisplay(output, NuclearEntangloporterBlocks.RADIOLOGICAL_ENCAPSULATOR,
                            NuclearEntangloporterBlocks.ISOTOPIC_PHASE_CONTROLLER, NuclearEntangloporterBlocks.CHEMICAL_RECONSTITUTION_CHAMBER);
                      CreativeTabDeferredRegister.addToDisplay(output, NuclearEntangloporterItems.EMPTY_CONTAINMENT_CAPSULE,
                            NuclearEntangloporterItems.EMPTY_PILL_CAPSULE, NuclearEntangloporterItems.NEUTRALIZING_COMPOUND,
                            NuclearEntangloporterItems.UNCHARGED_NEUTRALIZER_CAPSULE, NuclearEntangloporterItems.ISOTOPE_NEUTRALIZER,
                            NuclearEntangloporterItems.STABILIZATION_MATRIX);
                      if (ModList.get().isLoaded("ae2")) {
                          Ae2RadioactiveStorageCells.addToDisplay(output);
                      }
                      if (ModList.get().isLoaded("refinedstorage")) {
                          RefinedStorageRadioactiveDisks.addToDisplay(output);
                      }
                }));

    private NuclearEntangloporterCreativeTabs() {
    }
}
