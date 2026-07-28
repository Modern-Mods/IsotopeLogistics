package com.nuclearmekanism.nuclearentangloporter.mixin;

import appeng.blockentity.storage.DriveBlockEntity;
import com.nuclearmekanism.nuclearentangloporter.content.integration.RadioactiveDriveParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Gives only AE2's drive a server ticker; stock AE2 drive storage otherwise has no periodic block-entity tick. */
@Pseudo
@Mixin(targets = "appeng.block.AEBaseEntityBlock", remap = false)
public abstract class Ae2DriveBlockTickerMixin {

    @Shadow @Final private Class<?> blockEntityClass;

    @Inject(method = "getTicker", at = @At("RETURN"), cancellable = true)
    private <T extends BlockEntity> void nuclear$addDriveRadiationTicker(Level level, BlockState state, BlockEntityType<T> type,
          CallbackInfoReturnable<BlockEntityTicker<T>> callback) {
        if (!level.isClientSide && DriveBlockEntity.class.isAssignableFrom(blockEntityClass)) {
            BlockEntityTicker<T> parent = callback.getReturnValue();
            callback.setReturnValue((tickLevel, pos, tickState, tile) -> {
                if (parent != null) {
                    parent.tick(tickLevel, pos, tickState, tile);
                }
                RadioactiveDriveParticles.tickAe2(tickLevel, pos, (DriveBlockEntity) tile);
            });
        }
    }
}
