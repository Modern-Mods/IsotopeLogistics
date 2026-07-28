package com.nuclearmekanism.nuclearentangloporter.client;

import com.nuclearmekanism.nuclearentangloporter.NuclearEntangloporter;
import com.nuclearmekanism.nuclearentangloporter.client.gui.GuiChemicalReconstitutionChamber;
import com.nuclearmekanism.nuclearentangloporter.client.gui.GuiIsotopicPhaseController;
import com.nuclearmekanism.nuclearentangloporter.client.gui.GuiNuclearEntangloporter;
import com.nuclearmekanism.nuclearentangloporter.client.gui.GuiRadiologicalEncapsulator;
import com.nuclearmekanism.nuclearentangloporter.client.gui.GuiRadioactiveBin;
import com.nuclearmekanism.nuclearentangloporter.client.gui.GuiRadioactiveChemicalTank;
import com.nuclearmekanism.nuclearentangloporter.content.container.NuclearEntangloporterContainerTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import mekanism.client.ClientRegistrationUtil;
import mekanism.client.gui.GuiFluidTank;
import mekanism.client.render.RenderPropertiesProvider.MekRenderProperties;
import mekanism.client.render.item.block.RenderFluidTankItem;
import mekanism.client.render.tileentity.RenderBin;
import com.nuclearmekanism.nuclearentangloporter.content.NuclearEntangloporterBlocks;
import com.nuclearmekanism.nuclearentangloporter.content.NuclearEntangloporterBlockEntityTypes;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

/**
 * Client-only hooks that wire Mekanism's quantum entangloporter screen to the nuclear container type.
 */
@EventBusSubscriber(modid = NuclearEntangloporter.MODID, value = Dist.CLIENT)
public class NuclearEntangloporterClient {

    private NuclearEntangloporterClient() {
    }

    /**
     * Registers Mekanism's GUI class against the add-on's menu type so right-click interactions open the interface.
     */
    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(NuclearEntangloporterContainerTypes.NUCLEAR_ENTANGLOPORTER.get(), GuiNuclearEntangloporter::new);
        event.register(NuclearEntangloporterContainerTypes.RADIOLOGICAL_ENCAPSULATOR.get(), GuiRadiologicalEncapsulator::new);
        event.register(NuclearEntangloporterContainerTypes.ISOTOPIC_PHASE_CONTROLLER.get(), GuiIsotopicPhaseController::new);
        event.register(NuclearEntangloporterContainerTypes.CHEMICAL_RECONSTITUTION_CHAMBER.get(), GuiChemicalReconstitutionChamber::new);
        event.register(NuclearEntangloporterContainerTypes.RADIOACTIVE_BIN.get(), GuiRadioactiveBin::new);
        event.register(NuclearEntangloporterContainerTypes.RADIOACTIVE_FLUID_TANK.get(), GuiFluidTank::new);
        event.register(NuclearEntangloporterContainerTypes.RADIOACTIVE_CHEMICAL_TANK.get(), GuiRadioactiveChemicalTank::new);
    }

    /** Reuse Mekanism's Bin renderer so stored items and counts appear on every radioactive-bin front. */
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        ClientRegistrationUtil.bindTileEntityRenderer(event, RenderBin::new,
              NuclearEntangloporterBlockEntityTypes.BASIC_RADIOACTIVE_BIN,
              NuclearEntangloporterBlockEntityTypes.ADVANCED_RADIOACTIVE_BIN,
              NuclearEntangloporterBlockEntityTypes.ELITE_RADIOACTIVE_BIN,
              NuclearEntangloporterBlockEntityTypes.ULTIMATE_RADIOACTIVE_BIN,
              NuclearEntangloporterBlockEntityTypes.CREATIVE_RADIOACTIVE_BIN);
    }

    /** Registers Mekanism's dynamic item renderer so radioactive fluid tanks render their tiered shell and contents. */
    @SubscribeEvent
    public static void registerItemRenderers(RegisterClientExtensionsEvent event) {
        ClientRegistrationUtil.registerItemExtensions(event, new MekRenderProperties(RenderFluidTankItem.RENDERER),
              NuclearEntangloporterBlocks.BASIC_RADIOACTIVE_FLUID_TANK,
              NuclearEntangloporterBlocks.ADVANCED_RADIOACTIVE_FLUID_TANK,
              NuclearEntangloporterBlocks.ELITE_RADIOACTIVE_FLUID_TANK,
              NuclearEntangloporterBlocks.ULTIMATE_RADIOACTIVE_FLUID_TANK,
              NuclearEntangloporterBlocks.CREATIVE_RADIOACTIVE_FLUID_TANK);
    }
}
