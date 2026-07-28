package com.nuclearmekanism.nuclearentangloporter.content.item;

import mekanism.api.radiation.IRadiationManager;
import mekanism.api.radiation.capability.IRadiationEntity;
import mekanism.common.capabilities.Capabilities;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/** An always-usable emergency dose that clears only the consumer's accumulated radiation. */
public class IsotopeNeutralizerItem extends Item {

    public IsotopeNeutralizerItem(Properties properties) {
        super(properties.stacksTo(16));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.EAT;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack, @NotNull LivingEntity entity) {
        return 16;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity entity) {
        if (!level.isClientSide && entity instanceof ServerPlayer player) {
            IRadiationEntity radiation = player.getCapability(Capabilities.RADIATION_ENTITY);
            if (radiation != null) {
                radiation.set(IRadiationManager.INSTANCE.baselineRadiation());
            }
            CriteriaTriggers.CONSUME_ITEM.trigger(player, stack);
            player.awardStat(Stats.ITEM_USED.get(this));
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }
        return stack;
    }
}
