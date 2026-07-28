package com.nuclearmekanism.nuclearentangloporter.client.recipeviewer;

import com.nuclearmekanism.nuclearentangloporter.NuclearEntangloporter;
import com.nuclearmekanism.nuclearentangloporter.NuclearEntangloporterLang;
import com.nuclearmekanism.nuclearentangloporter.content.NuclearEntangloporterBlocks;
import mekanism.client.recipe_viewer.type.FakeRVRecipeType;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;

/** Shared identities keep GUI recipe buttons and JEI categories on addon-owned types. */
public final class RadioisotopeRecipeViewerTypes {

    public static final IRecipeViewerRecipeType<RadioisotopeJeiRecipe> ENCAPSULATING = type("encapsulating",
          NuclearEntangloporterBlocks.RADIOLOGICAL_ENCAPSULATOR, NuclearEntangloporterLang.JEI_ENCAPSULATING);
    public static final IRecipeViewerRecipeType<RadioisotopeJeiRecipe> PHASE_CONTROLLING = type("phase_controlling",
          NuclearEntangloporterBlocks.ISOTOPIC_PHASE_CONTROLLER, NuclearEntangloporterLang.JEI_PHASE_CONTROLLING);
    public static final IRecipeViewerRecipeType<RadioisotopeJeiRecipe> RECONSTITUTING = type("reconstituting",
          NuclearEntangloporterBlocks.CHEMICAL_RECONSTITUTION_CHAMBER, NuclearEntangloporterLang.JEI_RECONSTITUTING);

    private RadioisotopeRecipeViewerTypes() {
    }

    private static FakeRVRecipeType<RadioisotopeJeiRecipe> type(String path, ItemLike workstation, NuclearEntangloporterLang name) {
        return new FakeRVRecipeType<>(ResourceLocation.fromNamespaceAndPath(NuclearEntangloporter.MODID, path), workstation, name,
              RadioisotopeJeiRecipe.class, 0, 0, 128, 58);
    }
}
