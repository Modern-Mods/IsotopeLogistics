package com.nuclearmekanism.nuclearentangloporter.content.chemical;

import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.attribute.ChemicalAttributeValidator;
import mekanism.api.functions.ConstantPredicates;
import mekanism.common.attachments.containers.ContainerType;
import mekanism.common.attachments.containers.chemical.ComponentBackedChemicalTank;
import mekanism.common.attachments.containers.chemical.AttachedChemicals;
import mekanism.common.tier.ChemicalTankTier;
import com.nuclearmekanism.nuclearentangloporter.content.item.ItemBlockRadioactiveChemicalTank;
import net.minecraft.world.item.ItemStack;

/** Item-form tank storage with Mekanism's creative behavior and no radioactive attribute filter. */
public class RadioactiveComponentBackedChemicalTank extends ComponentBackedChemicalTank {

    public static RadioactiveComponentBackedChemicalTank create(ContainerType<?, ?, ?> ignored, ItemStack stack, int tank) {
        if (!(stack.getItem() instanceof ItemBlockRadioactiveChemicalTank item)) {
            throw new IllegalStateException("Attached to should always be a radioactive chemical tank item");
        }
        return new RadioactiveComponentBackedChemicalTank(stack, tank, item.getTier());
    }

    private final boolean creative;

    private RadioactiveComponentBackedChemicalTank(ItemStack stack, int tank, ChemicalTankTier tier) {
        super(stack, tank, ConstantPredicates.alwaysTrueBi(), ConstantPredicates.alwaysTrueBi(), ConstantPredicates.alwaysTrue(), tier::getOutput, tier::getStorage,
              ChemicalAttributeValidator.ALWAYS_ALLOW);
        creative = tier == ChemicalTankTier.CREATIVE;
    }

    @Override
    public ChemicalStack insert(ChemicalStack stack, Action action, AutomationType automationType) {
        return super.insert(stack, action.combine(!creative), automationType);
    }

    @Override
    public ChemicalStack extract(AttachedChemicals chemicals, ChemicalStack stored, long amount, Action action, AutomationType automationType) {
        return super.extract(chemicals, stored, amount, action.combine(!creative), automationType);
    }

    @Override
    public long setStackSize(AttachedChemicals chemicals, ChemicalStack stored, long amount, Action action) {
        return super.setStackSize(chemicals, stored, amount, action.combine(!creative));
    }
}
