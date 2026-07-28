package com.nuclearmekanism.nuclearentangloporter.client.recipeviewer;

import mekanism.api.chemical.ChemicalStack;
import net.minecraft.world.item.ItemStack;

/** One concrete radioactive-chemical variant displayed by the addon's JEI machine categories. */
public record RadioisotopeJeiRecipe(ChemicalStack chemical, ItemStack input, ItemStack output) {
}
