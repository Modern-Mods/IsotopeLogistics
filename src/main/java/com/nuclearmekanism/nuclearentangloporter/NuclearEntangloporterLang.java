package com.nuclearmekanism.nuclearentangloporter;

import mekanism.api.text.ILangEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

/**
 * Simple language key definitions for this add-on.
 */
public enum NuclearEntangloporterLang implements ILangEntry {
    DESCRIPTION_NUCLEAR_ENTANGLOPORTER("description.nuclearentangloporter.nuclear_entangloporter"),
    DESCRIPTION_RADIOACTIVE_CHEMICAL_TANK("description.nuclearentangloporter.radioactive_chemical_tank"),
    DESCRIPTION_RADIOACTIVE_FLUID_TANK("description.nuclearentangloporter.radioactive_fluid_tank"),
    DESCRIPTION_RADIOACTIVE_BIN("description.nuclearentangloporter.radioactive_bin"),
    DESCRIPTION_RADIOLOGICAL_ENCAPSULATOR("description.nuclearentangloporter.radiological_encapsulator"),
    DESCRIPTION_ISOTOPIC_PHASE_CONTROLLER("description.nuclearentangloporter.isotopic_phase_controller"),
    DESCRIPTION_CHEMICAL_RECONSTITUTION_CHAMBER("description.nuclearentangloporter.chemical_reconstitution_chamber"),
    CREATIVE_TAB("itemGroup.nuclearentangloporter"),
    CAPSULE_CHEMICAL("tooltip.nuclearentangloporter.capsule.chemical"),
    CAPSULE_AMOUNT("tooltip.nuclearentangloporter.capsule.amount"),
    CAPSULE_STATE("tooltip.nuclearentangloporter.capsule.state"),
    CAPSULE_RADIATION("tooltip.nuclearentangloporter.capsule.radiation"),
    CAPSULE_INVALID("tooltip.nuclearentangloporter.capsule.invalid"),
    ACTIVE("state.nuclearentangloporter.active"),
    PHASE_LOCKED("state.nuclearentangloporter.phase_locked"),
    SUPPRESSED("state.nuclearentangloporter.suppressed"),
    STABILIZE("mode.nuclearentangloporter.stabilize"),
    EXCITE("mode.nuclearentangloporter.excite"),
    PHASE_MODE("status.nuclearentangloporter.phase_mode"),
    RECONSTITUTION_PHASE_LOCKED("status.nuclearentangloporter.reconstitution_phase_locked"),
    PHASE_MODE_SWITCH("status.nuclearentangloporter.phase_mode_switch"),
    RECIPE_VIEWER("status.nuclearentangloporter.recipe_viewer"),
    RECIPES("gui.nuclearentangloporter.recipes"),
    RECIPE_ENCAPSULATION_INPUT("recipe.nuclearentangloporter.encapsulation_input"),
    RECIPE_ENCAPSULATION_OUTPUT("recipe.nuclearentangloporter.encapsulation_output"),
    RECIPE_STABILIZATION("recipe.nuclearentangloporter.stabilization"),
    RECIPE_EXCITATION("recipe.nuclearentangloporter.excitation"),
    RECIPE_RECONSTITUTION("recipe.nuclearentangloporter.reconstitution"),
    JEI_ENCAPSULATING("jei.nuclearentangloporter.encapsulating"),
    JEI_PHASE_CONTROLLING("jei.nuclearentangloporter.phase_controlling"),
    JEI_RECONSTITUTING("jei.nuclearentangloporter.reconstituting"),
    BIN_STORED("status.nuclearentangloporter.bin_stored");

    private final String key;

    NuclearEntangloporterLang(String key) {
        this.key = key;
    }

    @NotNull
    @Override
    public String getTranslationKey() {
        return key;
    }

    @NotNull
    @Override
    public MutableComponent translate(Object... args) {
        return Component.translatable(key, args);
    }
}
