package com.nuclearmekanism.nuclearentangloporter.content.chemical;

import com.nuclearmekanism.nuclearentangloporter.content.NuclearEntangloporterDataComponents;
import com.nuclearmekanism.nuclearentangloporter.content.NuclearEntangloporterItems;
import mekanism.api.chemical.ChemicalStack;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

/** Shared capsule validation and construction; item components keep chemical identity, amount, and phase state together. */
public final class RadioisotopeCapsules {

    /** Mekanism chemical units, matching one tenth of the Chemical Oxidizer's 10,000-unit output tank. */
    public static final long CAPACITY = 1_000;

    private RadioisotopeCapsules() {
    }

    public static boolean isActive(ItemStack stack) {
        return stack.is(NuclearEntangloporterItems.ACTIVE_RADIOISOTOPE_CAPSULE) && isValid(stack, false);
    }

    public static boolean isPhaseLocked(ItemStack stack) {
        return stack.is(NuclearEntangloporterItems.PHASE_LOCKED_RADIOISOTOPE_CAPSULE) && isValid(stack, true);
    }

    /** Returns one active stack's total dose; phase-locked and malformed capsules are always inert. */
    public static double radiation(ItemStack stack) {
        if (!isActive(stack)) {
            return 0;
        }
        ChemicalStack contents = contents(stack);
        return contents == null ? 0 : contents.getRadioactivity() * stack.getCount();
    }

    public static boolean isValid(ItemStack stack, boolean phaseLocked) {
        ChemicalStack contents = contents(stack);
        return contents != null && !contents.isEmpty() && contents.isRadioactive() && contents.getAmount() == CAPACITY
               && stack.getOrDefault(NuclearEntangloporterDataComponents.CAPSULE_PHASE_LOCKED.value(), false) == phaseLocked;
    }

    public static @Nullable ChemicalStack contents(ItemStack stack) {
        return stack.get(NuclearEntangloporterDataComponents.CAPSULE_CONTENTS.value());
    }

    public static ItemStack create(ChemicalStack contents, boolean phaseLocked) {
        if (contents.isEmpty() || !contents.isRadioactive() || contents.getAmount() != CAPACITY) {
            return ItemStack.EMPTY;
        }
        ItemStack capsule = (phaseLocked ? NuclearEntangloporterItems.PHASE_LOCKED_RADIOISOTOPE_CAPSULE :
                                         NuclearEntangloporterItems.ACTIVE_RADIOISOTOPE_CAPSULE).asStack();
        // Copy prevents a tank mutation from changing component data after transactional output checks complete.
        capsule.set(NuclearEntangloporterDataComponents.CAPSULE_CONTENTS.value(), contents.copy());
        capsule.set(NuclearEntangloporterDataComponents.CAPSULE_PHASE_LOCKED.value(), phaseLocked);
        return capsule;
    }
}
