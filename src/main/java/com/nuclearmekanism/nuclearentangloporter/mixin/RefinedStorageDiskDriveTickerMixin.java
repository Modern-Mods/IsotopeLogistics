package com.nuclearmekanism.nuclearentangloporter.mixin;

import com.refinedmods.refinedstorage.common.storage.AbstractDiskContainerBlockEntity;
import com.refinedmods.refinedstorage.common.storage.diskdrive.AbstractDiskDriveBlockEntity;
import com.nuclearmekanism.nuclearentangloporter.content.integration.RadioactiveDriveParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Reuses RS's existing disk-container ticker; only disk drives perform the radioactive content check. */
@Pseudo
@Mixin(targets = "com.refinedmods.refinedstorage.common.storage.DiskContainerBlockEntityTicker", remap = false)
public abstract class RefinedStorageDiskDriveTickerMixin {

    @Inject(method = "tick(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lcom/refinedmods/refinedstorage/common/storage/AbstractDiskContainerBlockEntity;)V",
          at = @At("TAIL"))
    private void nuclear$emitDriveRadiation(Level level, BlockPos pos, BlockState state, AbstractDiskContainerBlockEntity<?> tile,
          CallbackInfo callback) {
        if (tile instanceof AbstractDiskDriveBlockEntity drive) {
            RadioactiveDriveParticles.tickRefinedStorage(level, pos, drive);
        }
    }
}
