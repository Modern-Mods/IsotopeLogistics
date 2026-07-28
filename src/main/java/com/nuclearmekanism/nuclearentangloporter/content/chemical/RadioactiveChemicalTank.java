package com.nuclearmekanism.nuclearentangloporter.content.chemical;

import java.util.Objects;
import java.util.function.LongSupplier;
import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.api.IContentsListener;
import mekanism.api.chemical.BasicChemicalTank;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.attribute.ChemicalAttributeValidator;
import mekanism.api.functions.ConstantPredicates;
import mekanism.common.tier.ChemicalTankTier;
import org.jetbrains.annotations.Nullable;

/** Chemical tank behavior matching Mekanism's tiered tank while accepting every chemical attribute. */
public class RadioactiveChemicalTank extends BasicChemicalTank {

    public static RadioactiveChemicalTank create(ChemicalTankTier tier, @Nullable IContentsListener listener) {
        Objects.requireNonNull(tier, "Chemical tank tier cannot be null");
        return new RadioactiveChemicalTank(tier, listener);
    }

    private final boolean creative;
    private final LongSupplier rate;

    private RadioactiveChemicalTank(ChemicalTankTier tier, @Nullable IContentsListener listener) {
        // Mekanism's stock non-creative tank supplies null here, which rejects radioactive attributes.
        super(tier.getStorage(), ConstantPredicates.alwaysTrueBi(), ConstantPredicates.alwaysTrueBi(), ConstantPredicates.alwaysTrue(),
              ChemicalAttributeValidator.ALWAYS_ALLOW, listener, null);
        creative = tier == ChemicalTankTier.CREATIVE;
        rate = tier::getOutput;
    }

    @Override
    protected long getInsertRate(@Nullable AutomationType automationType) {
        return automationType == AutomationType.INTERNAL ? rate.getAsLong() : super.getInsertRate(automationType);
    }

    @Override
    protected long getExtractRate(@Nullable AutomationType automationType) {
        return automationType == AutomationType.INTERNAL ? rate.getAsLong() : super.getExtractRate(automationType);
    }

    @Override
    public ChemicalStack insert(ChemicalStack stack, Action action, AutomationType automationType) {
        if (creative && isEmpty() && action.execute() && automationType != AutomationType.EXTERNAL) {
            ChemicalStack remainder = super.insert(stack, Action.SIMULATE, automationType);
            if (remainder.isEmpty()) {
                setStackUnchecked(stack.copyWithAmount(getCapacity()));
            }
            return remainder;
        }
        return super.insert(stack, action.combine(!creative), automationType);
    }

    @Override
    public ChemicalStack extract(long amount, Action action, AutomationType automationType) {
        return super.extract(amount, action.combine(!creative), automationType);
    }

    @Override
    public long setStackSize(long amount, Action action) {
        return super.setStackSize(amount, action.combine(!creative));
    }
}
