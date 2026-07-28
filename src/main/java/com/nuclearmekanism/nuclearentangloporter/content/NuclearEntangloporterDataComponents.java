package com.nuclearmekanism.nuclearentangloporter.content;

import com.nuclearmekanism.nuclearentangloporter.NuclearEntangloporter;
import com.nuclearmekanism.nuclearentangloporter.content.frequency.NuclearFrequencyTypes;
import com.nuclearmekanism.nuclearentangloporter.content.frequency.NuclearInventoryFrequency;
import mekanism.common.attachments.FrequencyAware;
import mekanism.common.registration.MekanismDeferredHolder;
import mekanism.common.registration.impl.DataComponentDeferredRegister;
import net.minecraft.core.component.DataComponentType;
import mekanism.api.chemical.ChemicalStack;

/**
 * Registers data components that the Nuclear Entangloporter uses when persisting to item stacks.
 */
public final class NuclearEntangloporterDataComponents {

    /** Deferred register mirroring Mekanism's component registry helpers. */
    public static final DataComponentDeferredRegister DATA_COMPONENTS =
          new DataComponentDeferredRegister(NuclearEntangloporter.MODID);

    /**
     * Frequency-aware component that serialises {@link NuclearInventoryFrequency} identities onto items like configuration
     * cards and the block item itself so they can restore the selected frequency when placed.
     */
    public static final MekanismDeferredHolder<DataComponentType<?>, DataComponentType<FrequencyAware<NuclearInventoryFrequency>>>
          NUCLEAR_INVENTORY_FREQUENCY = DATA_COMPONENTS.registerFrequencyAware(
                "nuclear_inventory_frequency",
                () -> NuclearFrequencyTypes.NUCLEAR_INVENTORY
          );

    /** Exact chemical data is part of item identity, so unequal capsules cannot merge. */
    public static final MekanismDeferredHolder<DataComponentType<?>, DataComponentType<ChemicalStack>> CAPSULE_CONTENTS = DATA_COMPONENTS.simple(
          "radioisotope_capsule_contents", builder -> builder.persistent(ChemicalStack.CODEC)
                .networkSynchronized(ChemicalStack.STREAM_CODEC).cacheEncoding());

    /** Stored alongside contents so serialized capsules retain their stabilization state. */
    public static final MekanismDeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> CAPSULE_PHASE_LOCKED =
          DATA_COMPONENTS.registerBoolean("radioisotope_capsule_phase_locked");

    private NuclearEntangloporterDataComponents() {
    }
}
