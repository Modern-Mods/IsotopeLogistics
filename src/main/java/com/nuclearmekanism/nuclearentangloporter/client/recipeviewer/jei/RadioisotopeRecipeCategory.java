package com.nuclearmekanism.nuclearentangloporter.client.recipeviewer.jei;

import com.nuclearmekanism.nuclearentangloporter.client.recipeviewer.RadioisotopeJeiRecipe;
import com.nuclearmekanism.nuclearentangloporter.client.recipeviewer.RadioisotopeRecipeViewerTypes;
import mekanism.client.recipe_viewer.jei.MekanismJEI;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

/** Compact JEI layout for processor contracts; chemical stacks enumerate each allowed radioactive variant. */
public class RadioisotopeRecipeCategory implements IRecipeCategory<RadioisotopeJeiRecipe> {

    public enum Machine {ENCAPSULATING, PHASE_CONTROLLING, RECONSTITUTING}

    private final Machine machine;
    private final IRecipeViewerRecipeType<RadioisotopeJeiRecipe> viewerType;
    private final RecipeType<RadioisotopeJeiRecipe> recipeType;
    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable arrow;

    public RadioisotopeRecipeCategory(IGuiHelper helper, Machine machine) {
        this.machine = machine;
        viewerType = switch (machine) {
            case ENCAPSULATING -> RadioisotopeRecipeViewerTypes.ENCAPSULATING;
            case PHASE_CONTROLLING -> RadioisotopeRecipeViewerTypes.PHASE_CONTROLLING;
            case RECONSTITUTING -> RadioisotopeRecipeViewerTypes.RECONSTITUTING;
        };
        recipeType = MekanismJEI.recipeType(viewerType);
        background = helper.createBlankDrawable(128, 58);
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, viewerType.iconStack());
        arrow = helper.getRecipeArrow();
    }

    @Override
    public @NotNull RecipeType<RadioisotopeJeiRecipe> getRecipeType() {
        return recipeType;
    }

    @Override
    public @NotNull Component getTitle() {
        return viewerType.getTextComponent();
    }

    @Override
    public @NotNull IDrawable getBackground() {
        return background;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RadioisotopeJeiRecipe recipe, IFocusGroup focuses) {
        switch (machine) {
            case ENCAPSULATING -> {
                builder.addSlot(RecipeIngredientRole.INPUT, 8, 20).addIngredients(MekanismJEI.TYPE_CHEMICAL, java.util.List.of(recipe.chemical()));
                builder.addSlot(RecipeIngredientRole.INPUT, 34, 20).addItemStack(recipe.input()).setStandardSlotBackground();
                builder.addSlot(RecipeIngredientRole.OUTPUT, 102, 20).addItemStack(recipe.output()).setOutputSlotBackground();
            }
            case PHASE_CONTROLLING -> {
                builder.addSlot(RecipeIngredientRole.INPUT, 24, 20).addItemStack(recipe.input()).setStandardSlotBackground();
                builder.addSlot(RecipeIngredientRole.OUTPUT, 88, 20).addItemStack(recipe.output()).setOutputSlotBackground();
            }
            case RECONSTITUTING -> {
                builder.addSlot(RecipeIngredientRole.INPUT, 8, 20).addItemStack(recipe.input()).setStandardSlotBackground();
                builder.addSlot(RecipeIngredientRole.OUTPUT, 72, 20).addIngredients(MekanismJEI.TYPE_CHEMICAL, java.util.List.of(recipe.chemical()));
                builder.addSlot(RecipeIngredientRole.OUTPUT, 102, 20).addItemStack(recipe.output()).setOutputSlotBackground();
            }
        }
    }

    @Override
    public void draw(RadioisotopeJeiRecipe recipe, IRecipeSlotsView slots, GuiGraphics graphics, double mouseX, double mouseY) {
        arrow.draw(graphics, machine == Machine.ENCAPSULATING ? 70 : 54, 20);
    }
}
