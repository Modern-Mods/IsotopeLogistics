package com.nuclearmekanism.nuclearentangloporter.content.integration;

import appeng.api.stacks.AEItemKey;
import appeng.api.storage.cells.StorageCell;
import appeng.blockentity.storage.DriveBlockEntity;
import com.nuclearmekanism.nuclearentangloporter.content.chemical.RadioisotopeCapsules;
import com.nuclearmekanism.nuclearentangloporter.mixin.RefinedStorageDiskInventoryAccessor;
import com.refinedmods.refinedstorage.api.resource.ResourceAmount;
import com.refinedmods.refinedstorage.common.storage.DiskInventory;
import com.refinedmods.refinedstorage.common.storage.diskdrive.AbstractDiskDriveBlockEntity;
import com.refinedmods.refinedstorage.common.support.resource.ItemResource;
import mekanism.common.registries.MekanismParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

/** Server-only particle checks; storage contents are authoritative here and never need client synchronization. */
public final class RadioactiveDriveParticles {

    private static final int PARTICLE_INTERVAL = 10;

    private RadioactiveDriveParticles() {
    }

    public static void tickAe2(Level level, BlockPos pos, DriveBlockEntity drive) {
        if (shouldEmit(level) && hasActiveAe2Capsule(drive)) {
            emit((ServerLevel) level, pos);
        }
    }

    public static void tickRefinedStorage(Level level, BlockPos pos, AbstractDiskDriveBlockEntity drive) {
        if (shouldEmit(level) && hasActiveRefinedStorageCapsule(drive)) {
            emit((ServerLevel) level, pos);
        }
    }

    private static boolean shouldEmit(Level level) {
        return level instanceof ServerLevel && level.getGameTime() % PARTICLE_INTERVAL == 0;
    }

    private static boolean hasActiveAe2Capsule(DriveBlockEntity drive) {
        for (int slot = 0; slot < drive.getCellCount(); slot++) {
            StorageCell cell = drive.getOriginalCellInventory(slot);
            if (cell != null && cell.getAvailableStacks().keySet().stream().anyMatch(key ->
                  key instanceof AEItemKey item && RadioisotopeCapsules.isActive(item.getReadOnlyStack()))) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasActiveRefinedStorageCapsule(AbstractDiskDriveBlockEntity drive) {
        DiskInventory disks = ((RefinedStorageDiskInventoryAccessor) drive).nuclear$getDiskInventory();
        for (int slot = 0; slot < disks.getContainerSize(); slot++) {
            if (disks.resolve(slot).map(storage -> storage.getAll().stream().map(ResourceAmount::resource).anyMatch(resource ->
                  resource instanceof ItemResource item && RadioisotopeCapsules.isActive(item.toItemStack()))).orElse(false)) {
                return true;
            }
        }
        return false;
    }

    private static void emit(ServerLevel level, BlockPos pos) {
        level.sendParticles(MekanismParticleTypes.RADIATION.get(), pos.getX() + 0.5, pos.getY() + 0.8, pos.getZ() + 0.5,
              2, 0.35, 0.35, 0.35, 0);
    }
}
