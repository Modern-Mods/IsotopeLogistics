package com.nuclearmekanism.nuclearentangloporter.content.holder;

import com.nuclearmekanism.nuclearentangloporter.content.tile.TileEntityNuclearEntangloporter;
import java.util.Collections;
import java.util.List;
import mekanism.api.energy.IEnergyContainer;
import mekanism.common.capabilities.energy.BasicEnergyContainer;
import mekanism.common.capabilities.holder.energy.IEnergyContainerHolder;
import mekanism.common.config.MekanismConfig;
import mekanism.common.lib.transmitter.TransmissionType;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.common.util.Lazy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Energy container holder mirroring Mekanism's quantum entangloporter implementation.
 */
public class NuclearEntangloporterEnergyContainerHolder extends NuclearEntangloporterConfigHolder<IEnergyContainer> implements IEnergyContainerHolder {

    private final Lazy<List<IEnergyContainer>> clientContainer = Lazy.of(() -> Collections.singletonList(BasicEnergyContainer.create(MekanismConfig.general.entangloporterEnergyBuffer.getAsLong(), null)));

    public NuclearEntangloporterEnergyContainerHolder(TileEntityNuclearEntangloporter entangloporter) {
        super(entangloporter);
    }

    @Override
    protected TransmissionType getTransmissionType() {
        return TransmissionType.ENERGY;
    }

    @NotNull
    @Override
    public List<IEnergyContainer> getEnergyContainers(@Nullable Direction side) {
        if (entangloporter.hasFrequency()) {
            return entangloporter.getFreq().getEnergyContainers(side);
        } else if (entangloporter.isRemote()) {
            return clientContainer.get();
        }
        return Collections.emptyList();
    }
}
