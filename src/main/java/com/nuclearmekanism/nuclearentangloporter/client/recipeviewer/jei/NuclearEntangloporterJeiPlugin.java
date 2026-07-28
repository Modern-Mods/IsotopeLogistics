package com.nuclearmekanism.nuclearentangloporter.client.recipeviewer.jei;

import com.nuclearmekanism.nuclearentangloporter.NuclearEntangloporter;
import com.nuclearmekanism.nuclearentangloporter.client.recipeviewer.RadioisotopeJeiRecipe;
import com.nuclearmekanism.nuclearentangloporter.client.recipeviewer.RadioisotopeRecipeViewerTypes;
import com.nuclearmekanism.nuclearentangloporter.content.NuclearEntangloporterBlocks;
import com.nuclearmekanism.nuclearentangloporter.content.NuclearEntangloporterItems;
import com.nuclearmekanism.nuclearentangloporter.content.chemical.RadioisotopeCapsules;
import java.util.List;
import mekanism.api.MekanismAPI;
import mekanism.api.chemical.ChemicalStack;
import mekanism.client.recipe_viewer.jei.MekanismJEI;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import mekanism.common.registries.MekanismChemicals;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;

/** Registers addon-owned JEI pages from exactly the capsule transformations processor tiles execute. */
@JeiPlugin
public class NuclearEntangloporterJeiPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(NuclearEntangloporter.MODID, "jei");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IGuiHelper helper = registry.getJeiHelpers().getGuiHelper();
        registry.addRecipeCategories(
              new RadioisotopeRecipeCategory(helper, RadioisotopeRecipeCategory.Machine.ENCAPSULATING),
              new RadioisotopeRecipeCategory(helper, RadioisotopeRecipeCategory.Machine.PHASE_CONTROLLING),
              new RadioisotopeRecipeCategory(helper, RadioisotopeRecipeCategory.Machine.RECONSTITUTING)
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registry) {
        registry.addRecipes(type(RadioisotopeRecipeViewerTypes.ENCAPSULATING), recipes(RadioisotopeRecipeCategory.Machine.ENCAPSULATING));
        registry.addRecipes(type(RadioisotopeRecipeViewerTypes.PHASE_CONTROLLING), recipes(RadioisotopeRecipeCategory.Machine.PHASE_CONTROLLING));
        registry.addRecipes(type(RadioisotopeRecipeViewerTypes.RECONSTITUTING), recipes(RadioisotopeRecipeCategory.Machine.RECONSTITUTING));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
        registry.addRecipeCatalyst(NuclearEntangloporterBlocks.RADIOLOGICAL_ENCAPSULATOR, type(RadioisotopeRecipeViewerTypes.ENCAPSULATING));
        registry.addRecipeCatalyst(NuclearEntangloporterBlocks.ISOTOPIC_PHASE_CONTROLLER, type(RadioisotopeRecipeViewerTypes.PHASE_CONTROLLING));
        registry.addRecipeCatalyst(NuclearEntangloporterBlocks.CHEMICAL_RECONSTITUTION_CHAMBER, type(RadioisotopeRecipeViewerTypes.RECONSTITUTING));
    }

    private static RecipeType<RadioisotopeJeiRecipe> type(IRecipeViewerRecipeType<RadioisotopeJeiRecipe> type) {
        return MekanismJEI.recipeType(type);
    }

    private static List<RadioisotopeJeiRecipe> recipes(RadioisotopeRecipeCategory.Machine machine) {
        var radioactiveRecipes = MekanismAPI.CHEMICAL_REGISTRY.holders().map(holder -> new ChemicalStack(holder, RadioisotopeCapsules.CAPACITY))
              .filter(ChemicalStack::isRadioactive).flatMap(chemical -> switch (machine) {
                  case ENCAPSULATING -> java.util.stream.Stream.of(new RadioisotopeJeiRecipe(chemical,
                        NuclearEntangloporterItems.EMPTY_CONTAINMENT_CAPSULE.asStack(), RadioisotopeCapsules.create(chemical, false)));
                  case PHASE_CONTROLLING -> java.util.stream.Stream.of(
                        new RadioisotopeJeiRecipe(chemical, RadioisotopeCapsules.create(chemical, false), RadioisotopeCapsules.create(chemical, true)),
                        new RadioisotopeJeiRecipe(chemical, RadioisotopeCapsules.create(chemical, true), RadioisotopeCapsules.create(chemical, false))
                  );
                  case RECONSTITUTING -> java.util.stream.Stream.of(new RadioisotopeJeiRecipe(chemical,
                        RadioisotopeCapsules.create(chemical, false), NuclearEntangloporterItems.EMPTY_CONTAINMENT_CAPSULE.asStack()));
              });
        if (machine == RadioisotopeRecipeCategory.Machine.ENCAPSULATING) {
            radioactiveRecipes = java.util.stream.Stream.concat(radioactiveRecipes, java.util.stream.Stream.of(new RadioisotopeJeiRecipe(
                  MekanismChemicals.OXYGEN.asStack(100), NuclearEntangloporterItems.UNCHARGED_NEUTRALIZER_CAPSULE.asStack(),
                  NuclearEntangloporterItems.ISOTOPE_NEUTRALIZER.asStack())));
        }
        return radioactiveRecipes.toList();
    }
}
