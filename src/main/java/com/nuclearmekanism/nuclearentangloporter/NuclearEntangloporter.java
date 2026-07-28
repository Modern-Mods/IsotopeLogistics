package com.nuclearmekanism.nuclearentangloporter;

import com.nuclearmekanism.nuclearentangloporter.content.NuclearEntangloporterBlockEntityTypes;
import com.nuclearmekanism.nuclearentangloporter.content.NuclearEntangloporterBlocks;
import com.nuclearmekanism.nuclearentangloporter.content.NuclearEntangloporterDataComponents;
import com.nuclearmekanism.nuclearentangloporter.content.NuclearEntangloporterCreativeTabs;
import com.nuclearmekanism.nuclearentangloporter.content.NuclearEntangloporterItems;
import com.nuclearmekanism.nuclearentangloporter.content.frequency.NuclearFrequencyTypes;
import com.nuclearmekanism.nuclearentangloporter.content.container.NuclearEntangloporterContainerTypes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

/**
 * Entry point for the Nuclear Entangloporter add-on.
 */
@Mod(NuclearEntangloporter.MODID)
public class NuclearEntangloporter {

    public static final String MODID = "nuclearentangloporter";

    public NuclearEntangloporter(ModContainer modContainer, IEventBus modEventBus) {
        // Use the injected mod event bus to mirror Mekanism's registration lifecycle.
        NuclearEntangloporterBlocks.BLOCKS.register(modEventBus);
        NuclearEntangloporterBlockEntityTypes.TILE_ENTITY_TYPES.register(modEventBus);
        NuclearEntangloporterContainerTypes.CONTAINER_TYPES.register(modEventBus);
        NuclearEntangloporterDataComponents.DATA_COMPONENTS.register(modEventBus);
        NuclearEntangloporterItems.register(modEventBus);
        NuclearEntangloporterCreativeTabs.TABS.register(modEventBus);

        // Force the nuclear frequency type to register before any frequency menus query the registry.
        NuclearFrequencyTypes.NUCLEAR_INVENTORY.getName();

    }
}
