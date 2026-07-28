package com.nuclearmekanism.nuclearentangloporter.content.item;

import com.nuclearmekanism.nuclearentangloporter.content.NuclearEntangloporterDataComponents;
import com.nuclearmekanism.nuclearentangloporter.content.frequency.NuclearFrequencyTypes;
import com.nuclearmekanism.nuclearentangloporter.content.frequency.NuclearInventoryFrequency;
import com.nuclearmekanism.nuclearentangloporter.content.tile.TileEntityNuclearEntangloporter;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import mekanism.api.text.EnumColor;
import mekanism.common.MekanismLang;
import mekanism.common.attachments.FrequencyAware;
import mekanism.common.attachments.component.AttachedEjector;
import mekanism.common.attachments.component.AttachedSideConfig;
import mekanism.common.attachments.component.AttachedSideConfig.LightConfigInfo;
import mekanism.common.block.prefab.BlockTile.BlockTileModel;
import mekanism.common.content.blocktype.BlockTypeTile;
import mekanism.common.item.block.ItemBlockTooltip;
import mekanism.common.lib.frequency.Frequency.FrequencyIdentity;
import mekanism.common.lib.frequency.FrequencyType;
import mekanism.common.lib.frequency.IFrequencyItem;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.registries.MekanismDataComponents;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.text.OwnerDisplay;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

/**
 * Item representation for the Nuclear Entangloporter with tailored side configuration metadata.
 */
public class ItemBlockNuclearEntangloporter extends ItemBlockTooltip<BlockTileModel<TileEntityNuclearEntangloporter, BlockTypeTile<TileEntityNuclearEntangloporter>>>
      implements IFrequencyItem {

    private static final AttachedSideConfig SIDE_CONFIG = Util.make(() -> {
        Map<TransmissionType, LightConfigInfo> configInfo = new EnumMap<>(TransmissionType.class);
        configInfo.put(TransmissionType.ITEM, LightConfigInfo.FRONT_OUT_NO_EJECT);
        configInfo.put(TransmissionType.FLUID, LightConfigInfo.FRONT_OUT_NO_EJECT);
        configInfo.put(TransmissionType.CHEMICAL, LightConfigInfo.FRONT_OUT_NO_EJECT);
        configInfo.put(TransmissionType.ENERGY, LightConfigInfo.FRONT_OUT_NO_EJECT);
        configInfo.put(TransmissionType.HEAT, LightConfigInfo.INPUT_OUT_ALL);
        return new AttachedSideConfig(configInfo);
    });

    public ItemBlockNuclearEntangloporter(BlockTileModel<TileEntityNuclearEntangloporter, BlockTypeTile<TileEntityNuclearEntangloporter>> block, Properties properties) {
        super(block, true, properties
              .component(MekanismDataComponents.EJECTOR, AttachedEjector.DEFAULT)
              .component(MekanismDataComponents.SIDE_CONFIG, SIDE_CONFIG)
        );
    }

    @Override
    protected void addStats(@NotNull ItemStack stack, @NotNull Item.TooltipContext context, @NotNull List<Component> tooltip,
          @NotNull TooltipFlag flag) {
        FrequencyAware<NuclearInventoryFrequency> frequencyAware =
              stack.get(NuclearEntangloporterDataComponents.NUCLEAR_INVENTORY_FREQUENCY.value());
        if (frequencyAware != null) {
            FrequencyIdentity identity = frequencyAware.identity().orElse(null);
            if (identity != null) {
                tooltip.add(MekanismLang.FREQUENCY.translateColored(EnumColor.INDIGO, EnumColor.GRAY, identity.key()));
                UUID ownerUUID = frequencyAware.getOwner();
                if (ownerUUID != null) {
                    String owner = OwnerDisplay.getOwnerName(MekanismUtils.tryGetClientPlayer(), ownerUUID, null);
                    if (owner != null) {
                        tooltip.add(MekanismLang.OWNER.translateColored(EnumColor.INDIGO, EnumColor.GRAY, owner));
                    }
                }
                tooltip.add(MekanismLang.MODE.translateColored(EnumColor.INDIGO, EnumColor.GRAY, identity.securityMode()));
            }
        }
    }

    @NotNull
    @Override
    public FrequencyType<?> getFrequencyType() {
        return NuclearFrequencyTypes.NUCLEAR_INVENTORY;
    }
}
