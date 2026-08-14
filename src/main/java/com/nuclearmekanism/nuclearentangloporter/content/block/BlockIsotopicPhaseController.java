package com.nuclearmekanism.nuclearentangloporter.content.block;

import com.nuclearmekanism.nuclearentangloporter.NuclearEntangloporterLang;
import com.nuclearmekanism.nuclearentangloporter.content.tile.TileEntityRadioisotopeProcessor;
import java.util.function.UnaryOperator;
import mekanism.common.block.prefab.BlockTile.BlockTileModel;
import mekanism.common.content.blocktype.Machine;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

/** Sneak-use switches phase direction until the dedicated controller GUI is added. */
public class BlockIsotopicPhaseController extends BlockTileModel<TileEntityRadioisotopeProcessor, Machine<TileEntityRadioisotopeProcessor>> {

    public static final BooleanProperty EXCITE = BooleanProperty.create("excite");

    public BlockIsotopicPhaseController(Machine<TileEntityRadioisotopeProcessor> type, UnaryOperator<BlockBehaviour.Properties> propertiesModifier) {
        super(type, propertiesModifier);
        registerDefaultState(defaultBlockState().setValue(EXCITE, false));
    }

    public BlockIsotopicPhaseController(Machine<TileEntityRadioisotopeProcessor> type, BlockBehaviour.Properties properties) {
        super(type, properties);
        registerDefaultState(defaultBlockState().setValue(EXCITE, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<net.minecraft.world.level.block.Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(EXCITE);
    }

    /** Tile owns mode; block state only selects supplied Stabilize/Excite art. */
    public static void setExciteState(Level level, BlockPos pos, boolean excite) {
        BlockState state = level.getBlockState(pos);
        if (state.getBlock() instanceof BlockIsotopicPhaseController && state.getValue(EXCITE) != excite) {
            level.setBlock(pos, state.setValue(EXCITE, excite), net.minecraft.world.level.block.Block.UPDATE_CLIENTS);
        }
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player,
          @NotNull BlockHitResult hit) {
        if (!player.isShiftKeyDown()) {
            return super.useWithoutItem(state, level, pos, player, hit);
        }
        if (level.getBlockEntity(pos) instanceof TileEntityRadioisotopeProcessor controller) {
            if (!level.isClientSide) {
                controller.togglePhaseMode();
                Component mode = controller.isExciteMode() ? NuclearEntangloporterLang.EXCITE.translate() : NuclearEntangloporterLang.STABILIZE.translate();
                player.displayClientMessage(NuclearEntangloporterLang.PHASE_MODE.translate(mode), true);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
