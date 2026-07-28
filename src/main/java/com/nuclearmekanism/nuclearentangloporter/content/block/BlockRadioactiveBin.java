package com.nuclearmekanism.nuclearentangloporter.content.block;

import com.nuclearmekanism.nuclearentangloporter.content.chemical.RadioisotopeCapsules;
import mekanism.api.radiation.IRadiationManager;
import mekanism.common.block.basic.BlockBin;
import mekanism.common.content.blocktype.BlockTypeTile;
import mekanism.common.registries.MekanismParticleTypes;
import mekanism.common.tile.TileEntityBin;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

/** Radioactive-bin destruction releases any still-active capsule contents before normal bin drops are calculated. */
public class BlockRadioactiveBin extends BlockBin {

    public BlockRadioactiveBin(BlockTypeTile<TileEntityBin> type, java.util.function.UnaryOperator<BlockBehaviour.Properties> propertiesModifier) {
        super(type, propertiesModifier);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moving) {
        if (!level.isClientSide && state.getBlock() != newState.getBlock() && level.getBlockEntity(pos) instanceof TileEntityBin bin) {
            double radiation = RadioisotopeCapsules.radiation(bin.getBinSlot().getStack());
            if (radiation > 0) {
                IRadiationManager.INSTANCE.radiate(level, pos, radiation);
            }
        }
        super.onRemove(state, level, pos, newState, moving);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        super.animateTick(state, level, pos, random);
        if (random.nextBoolean() && level.getBlockEntity(pos) instanceof TileEntityBin bin && RadioisotopeCapsules.isActive(bin.getBinSlot().getStack())) {
            level.addParticle(MekanismParticleTypes.RADIATION.get(), pos.getX() + random.nextDouble(), pos.getY() + random.nextDouble(),
                  pos.getZ() + random.nextDouble(), 0, 0, 0);
        }
    }
}
