package com.nuclearmekanism.nuclearentangloporter.event;

import com.nuclearmekanism.nuclearentangloporter.NuclearEntangloporter;
import com.nuclearmekanism.nuclearentangloporter.content.NuclearEntangloporterBlocks;
import com.nuclearmekanism.nuclearentangloporter.content.chemical.RadioisotopeCapsules;
import mekanism.api.radiation.IRadiationManager;
import mekanism.common.block.basic.BlockBin;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/** Applies Mekanism's public radiation API to active capsules carried by players; phase-locked stacks never enter this path. */
@EventBusSubscriber(modid = NuclearEntangloporter.MODID)
public final class ActiveCapsuleRadiation {

    private ActiveCapsuleRadiation() {
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide || player.tickCount % 20 != 0) {
            return;
        }
        double radiation = radiation(player);
        if (radiation > 0) {
            IRadiationManager.INSTANCE.radiate(player, radiation);
        }
    }

    /** Dropped active capsules contaminate their immediate location once per second. */
    @SubscribeEvent
    public static void onItemTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof ItemEntity item) || item.level().isClientSide || item.tickCount % 20 != 0) {
            return;
        }
        double radiation = RadioisotopeCapsules.radiation(item.getItem());
        if (radiation > 0) {
            IRadiationManager.INSTANCE.radiate(item.level(), item.blockPosition(), radiation);
        }
    }

    /** Standard Mekanism bins have no radioactive-item validator, so reject manual active-capsule insertion here. */
    @SubscribeEvent
    public static void onRightClickBin(PlayerInteractEvent.RightClickBlock event) {
        if (RadioisotopeCapsules.isActive(event.getItemStack()) && event.getLevel().getBlockState(event.getPos()).getBlock() instanceof BlockBin &&
            !NuclearEntangloporterBlocks.isRadioactiveBin(event.getLevel().getBlockState(event.getPos()).getBlock())) {
            event.setCancellationResult(InteractionResult.FAIL);
            event.setCanceled(true);
        }
    }

    private static double radiation(Player player) {
        double total = radiation(player.getInventory().items);
        total += radiation(player.getInventory().armor);
        return total + radiation(player.getInventory().offhand);
    }

    private static double radiation(Iterable<ItemStack> stacks) {
        double total = 0;
        for (ItemStack stack : stacks) {
            total += RadioisotopeCapsules.radiation(stack);
        }
        return total;
    }
}
