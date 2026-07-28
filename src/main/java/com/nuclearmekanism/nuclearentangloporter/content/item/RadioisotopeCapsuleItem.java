package com.nuclearmekanism.nuclearentangloporter.content.item;

import com.nuclearmekanism.nuclearentangloporter.NuclearEntangloporterLang;
import com.nuclearmekanism.nuclearentangloporter.content.chemical.RadioisotopeCapsules;
import java.util.List;
import mekanism.api.chemical.ChemicalStack;
import mekanism.common.util.text.TextUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

/** Shows component-backed contents without treating a malformed capsule as usable material. */
public class RadioisotopeCapsuleItem extends Item {

    private final boolean phaseLocked;

    public RadioisotopeCapsuleItem(Properties properties, boolean phaseLocked) {
        super(properties.stacksTo(64));
        this.phaseLocked = phaseLocked;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        ChemicalStack contents = RadioisotopeCapsules.contents(stack);
        if (contents == null || !RadioisotopeCapsules.isValid(stack, phaseLocked)) {
            tooltip.add(NuclearEntangloporterLang.CAPSULE_INVALID.translate());
            return;
        }
        tooltip.add(NuclearEntangloporterLang.CAPSULE_CHEMICAL.translate(contents.getTextComponent()));
        tooltip.add(NuclearEntangloporterLang.CAPSULE_AMOUNT.translate(TextUtils.format(contents.getAmount())));
        tooltip.add(NuclearEntangloporterLang.CAPSULE_STATE.translate(phaseLocked ? NuclearEntangloporterLang.PHASE_LOCKED.translate() : NuclearEntangloporterLang.ACTIVE.translate()));
        tooltip.add(NuclearEntangloporterLang.CAPSULE_RADIATION.translate(phaseLocked ? NuclearEntangloporterLang.SUPPRESSED.translate() :
              TextUtils.format(contents.getRadioactivity())));
    }
}
