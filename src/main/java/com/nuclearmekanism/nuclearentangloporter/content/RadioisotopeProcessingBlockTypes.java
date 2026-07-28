package com.nuclearmekanism.nuclearentangloporter.content;

import com.nuclearmekanism.nuclearentangloporter.NuclearEntangloporterLang;
import com.nuclearmekanism.nuclearentangloporter.content.tile.TileEntityRadioisotopeProcessor;
import mekanism.api.Upgrade;
import mekanism.common.content.blocktype.Machine;
import mekanism.common.content.blocktype.Machine.MachineBuilder;
import mekanism.common.block.attribute.AttributeStateActive;
import mekanism.common.block.attribute.AttributeHasBounding;
import mekanism.common.content.blocktype.BlockShapes;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.registration.impl.SoundEventRegistryObject;
import mekanism.common.registries.MekanismSounds;
import net.minecraft.sounds.SoundEvent;
import mekanism.common.util.VoxelShapeUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;

/** Machine definitions use Mekanism energy and side-configuration attributes instead of a parallel capability system. */
public final class RadioisotopeProcessingBlockTypes {

    private static final VoxelShape[] RADIOLOGICAL_ENCAPSULATOR_SHAPE = createRadiologicalEncapsulatorShape();
    public static final Machine<TileEntityRadioisotopeProcessor> RADIOLOGICAL_ENCAPSULATOR = machine(
          () -> NuclearEntangloporterBlockEntityTypes.RADIOLOGICAL_ENCAPSULATOR, NuclearEntangloporterLang.DESCRIPTION_RADIOLOGICAL_ENCAPSULATOR, 1_000L, 500_000L,
          MekanismSounds.ISOTOPIC_CENTRIFUGE, false, true);
    public static final Machine<TileEntityRadioisotopeProcessor> ISOTOPIC_PHASE_CONTROLLER = machine(
          () -> NuclearEntangloporterBlockEntityTypes.ISOTOPIC_PHASE_CONTROLLER, NuclearEntangloporterLang.DESCRIPTION_ISOTOPIC_PHASE_CONTROLLER, 5_000L, 500_000L,
          MekanismSounds.PURIFICATION_CHAMBER, true, false);
    public static final Machine<TileEntityRadioisotopeProcessor> CHEMICAL_RECONSTITUTION_CHAMBER = machine(
          () -> NuclearEntangloporterBlockEntityTypes.CHEMICAL_RECONSTITUTION_CHAMBER, NuclearEntangloporterLang.DESCRIPTION_CHEMICAL_RECONSTITUTION_CHAMBER, 1_000L, 500_000L,
          MekanismSounds.CHEMICAL_DISSOLUTION_CHAMBER, true, false);

    private static Machine<TileEntityRadioisotopeProcessor> machine(java.util.function.Supplier<mekanism.common.registration.impl.TileEntityTypeRegistryObject<TileEntityRadioisotopeProcessor>> tile,
          NuclearEntangloporterLang description, long usage, long storage, SoundEventRegistryObject<SoundEvent> sound, boolean activeModel, boolean hasUpperBoundingBlock) {
        var builder = MachineBuilder.createMachine(tile, description).withEnergyConfig(() -> usage, () -> storage)
              .withSideConfig(TransmissionType.ITEM, TransmissionType.CHEMICAL, TransmissionType.ENERGY)
              .withSound(sound)
              // Capsule dose is fixed, so Chemical upgrades install safely without changing its 1,000-unit contract.
              .withSupportedUpgrades(Upgrade.SPEED, Upgrade.ENERGY, Upgrade.CHEMICAL, Upgrade.MUFFLING, Upgrade.ANCHOR);
        if (hasUpperBoundingBlock) {
            // Reserve block above, but keep hover/collision outline matched to custom encapsulator model.
            builder.withCustomShape(RADIOLOGICAL_ENCAPSULATOR_SHAPE).with(AttributeHasBounding.ABOVE_ONLY);
        }
        // Only the purification-style controller has a distinct active model; the other borrowed models are static.
        return (activeModel ? builder : builder.without(AttributeStateActive.class)).build();
    }

    private static VoxelShape[] createRadiologicalEncapsulatorShape() {
        VoxelShape[] shapes = new VoxelShape[4];
        VoxelShapeUtils.setShape(VoxelShapeUtils.combine(
              Block.box(2, 0, 2, 14, 3, 14), // base
              Block.box(3, 3, 3, 13, 23, 13), // tower
              Block.box(4, 23, 4, 12, 25, 12), // glass tank
              Block.box(3, 25, 3, 13, 26, 13), // top cap
              Block.box(4, 4, 0, 12, 12, 3), // front port
              Block.box(4, 4, 13, 12, 12, 16), // back port
              Block.box(0, 4, 4, 3, 12, 12), // left port
              Block.box(13, 4, 4, 16, 12, 12) // right port
        ), shapes);
        return shapes;
    }

    private RadioisotopeProcessingBlockTypes() {
    }
}
