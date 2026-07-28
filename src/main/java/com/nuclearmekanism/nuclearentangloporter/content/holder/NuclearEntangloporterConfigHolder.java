package com.nuclearmekanism.nuclearentangloporter.content.holder;

import com.nuclearmekanism.nuclearentangloporter.content.tile.TileEntityNuclearEntangloporter;
import mekanism.common.capabilities.holder.ConfigHolder;

/**
 * Base holder that mirrors Mekanism's quantum entangloporter config holder but targets the nuclear variant tile.
 */
public abstract class NuclearEntangloporterConfigHolder<TYPE> extends ConfigHolder<TYPE> {

    protected final TileEntityNuclearEntangloporter entangloporter;

    protected NuclearEntangloporterConfigHolder(TileEntityNuclearEntangloporter entangloporter) {
        super(entangloporter);
        this.entangloporter = entangloporter;
    }
}
