package com.nuclearmekanism.nuclearentangloporter.content;

import com.nuclearmekanism.nuclearentangloporter.NuclearEntangloporter;
import com.nuclearmekanism.nuclearentangloporter.content.item.IsotopeNeutralizerItem;
import com.nuclearmekanism.nuclearentangloporter.content.item.RadioisotopeCapsuleItem;
import mekanism.common.registration.impl.ItemDeferredRegister;
import mekanism.common.registration.impl.ItemRegistryObject;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;

/** Registers reusable containment parts and the two state-specific capsule items. */
public final class NuclearEntangloporterItems {

    public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(NuclearEntangloporter.MODID);

    public static final ItemRegistryObject<Item> EMPTY_CONTAINMENT_CAPSULE = ITEMS.register("empty_containment_capsule");
    public static final ItemRegistryObject<Item> EMPTY_PILL_CAPSULE = ITEMS.register("empty_pill_capsule");
    public static final ItemRegistryObject<Item> NEUTRALIZING_COMPOUND = ITEMS.register("neutralizing_compound");
    public static final ItemRegistryObject<Item> UNCHARGED_NEUTRALIZER_CAPSULE = ITEMS.register("uncharged_neutralizer_capsule");
    public static final ItemRegistryObject<IsotopeNeutralizerItem> ISOTOPE_NEUTRALIZER = ITEMS.registerItem("isotope_neutralizer", IsotopeNeutralizerItem::new);
    public static final ItemRegistryObject<RadioisotopeCapsuleItem> ACTIVE_RADIOISOTOPE_CAPSULE = ITEMS.registerItem("active_radioisotope_capsule",
          properties -> new RadioisotopeCapsuleItem(properties, false));
    public static final ItemRegistryObject<RadioisotopeCapsuleItem> PHASE_LOCKED_RADIOISOTOPE_CAPSULE = ITEMS.registerItem("phase_locked_radioisotope_capsule",
          properties -> new RadioisotopeCapsuleItem(properties, true));
    public static final ItemRegistryObject<Item> STABILIZATION_MATRIX = ITEMS.register("stabilization_matrix");

    /** Registers optional disks only after their owner mod is known to be present. */
    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
        if (ModList.get().isLoaded("ae2")) {
            Ae2RadioactiveStorageCells.register(modEventBus);
        }
        if (ModList.get().isLoaded("refinedstorage")) {
            RefinedStorageRadioactiveDisks.register(modEventBus);
        }
    }

    private NuclearEntangloporterItems() {
    }
}
