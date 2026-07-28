package com.nuclearmekanism.nuclearentangloporter.content.tile;

import com.nuclearmekanism.nuclearentangloporter.content.RadioisotopeProcessorType;
import com.nuclearmekanism.nuclearentangloporter.content.NuclearEntangloporterBlocks;
import com.nuclearmekanism.nuclearentangloporter.content.chemical.RadioisotopeCapsules;
import com.nuclearmekanism.nuclearentangloporter.content.NuclearEntangloporterItems;
import com.nuclearmekanism.nuclearentangloporter.content.block.BlockIsotopicPhaseController;
import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.api.IContentsListener;
import mekanism.api.RelativeSide;
import mekanism.api.Upgrade;
import mekanism.api.chemical.BasicChemicalTank;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.chemical.attribute.ChemicalAttributeValidator;
import mekanism.api.functions.ConstantPredicates;
import mekanism.common.capabilities.energy.MachineEnergyContainer;
import mekanism.common.capabilities.holder.chemical.ChemicalTankHelper;
import mekanism.common.capabilities.holder.chemical.IChemicalTankHolder;
import mekanism.common.capabilities.holder.energy.EnergyContainerHelper;
import mekanism.common.capabilities.holder.energy.IEnergyContainerHolder;
import mekanism.common.capabilities.holder.slot.IInventorySlotHolder;
import mekanism.common.capabilities.holder.slot.InventorySlotHelper;
import mekanism.common.inventory.slot.BasicInventorySlot;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.registries.MekanismChemicals;
import mekanism.common.tile.component.TileComponentEjector;
import mekanism.common.tile.component.TileComponentChunkLoader;
import mekanism.common.tile.interfaces.IHasMode;
import mekanism.common.lib.chunkloading.IChunkLoader;
import mekanism.common.tile.prefab.TileEntityConfigurableMachine;
import mekanism.common.util.MekanismUtils;
import mekanism.common.inventory.container.MekanismContainer;
import mekanism.common.inventory.container.sync.SyncableBoolean;
import mekanism.common.inventory.container.sync.SyncableInt;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.ChunkPos;
import java.util.Collections;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

/**
 * Transactional backend for all three capsule machines. Inputs remain untouched until energy and every output have
 * passed a simulation check, preventing automation timing or broken-machine duplication.
 */
public class TileEntityRadioisotopeProcessor extends TileEntityConfigurableMachine implements IHasMode, IChunkLoader {

    private static final long TANK_CAPACITY = 10_000L;
    private static final int STANDARD_TICKS_REQUIRED = 100;
    private static final int STABILIZATION_TICKS_REQUIRED = 200;
    private static final long NEUTRALIZER_OXYGEN = 100L;
    private static final long NEUTRALIZER_ENERGY = 100_000L;
    private static final long EXCITATION_ENERGY = 50_000L;
    private final RadioisotopeProcessorType type;
    private final long operationEnergy;
    private boolean excite;
    private int progress;
    private IChemicalTank chemicalInput;
    private IChemicalTank chemicalOutput;
    private MachineEnergyContainer<TileEntityRadioisotopeProcessor> energyContainer;
    private BasicInventorySlot inputSlot;
    private BasicInventorySlot outputSlot;
    private final TileComponentChunkLoader<TileEntityRadioisotopeProcessor> chunkLoaderComponent;

    public TileEntityRadioisotopeProcessor(Holder<Block> block, BlockPos pos, BlockState state, RadioisotopeProcessorType type, long operationEnergy) {
        super(block, pos, state);
        this.type = type;
        this.operationEnergy = operationEnergy;
        configComponent.setupIOConfig(TransmissionType.ITEM, inputSlot, outputSlot, RelativeSide.FRONT, true);
        configComponent.setupIOConfig(TransmissionType.CHEMICAL, chemicalInput, chemicalOutput, RelativeSide.RIGHT);
        configComponent.setupInputConfig(TransmissionType.ENERGY, energyContainer);
        ejectorComponent = new TileComponentEjector(this);
        ejectorComponent.setOutputData(configComponent, TransmissionType.ITEM, TransmissionType.CHEMICAL);
        chunkLoaderComponent = new TileComponentChunkLoader<>(this);
    }

    @Override
    public @NotNull IChemicalTankHolder getInitialChemicalTanks(IContentsListener listener) {
        ChemicalTankHelper builder = ChemicalTankHelper.forSideWithConfig(this);
        // Radioactive inputs need an explicit attribute validator; default Mekanism tanks reject radiation-bearing chemicals.
        builder.addTank(chemicalInput = BasicChemicalTank.createModern(TANK_CAPACITY, ConstantPredicates.notExternal(), ConstantPredicates.alwaysTrueBi(),
              this::acceptsChemical, ChemicalAttributeValidator.ALWAYS_ALLOW, listener));
        builder.addTank(chemicalOutput = BasicChemicalTank.output(TANK_CAPACITY, listener));
        return builder.build();
    }

    @Override
    protected @NotNull IEnergyContainerHolder getInitialEnergyContainers(IContentsListener listener) {
        EnergyContainerHelper builder = EnergyContainerHelper.forSideWithConfig(this);
        builder.addContainer(energyContainer = MachineEnergyContainer.input(this, listener));
        return builder.build();
    }

    @Override
    protected @NotNull IInventorySlotHolder getInitialInventory(IContentsListener listener) {
        InventorySlotHelper builder = InventorySlotHelper.forSideWithConfig(this);
        // Mekanism calls this before our constructor assigns type, so use its already-initialized block holder.
        var block = getBlockHolder().value();
        int inputX = block == NuclearEntangloporterBlocks.RADIOLOGICAL_ENCAPSULATOR.get() ? 30 : 26;
        int outputX = block == NuclearEntangloporterBlocks.CHEMICAL_RECONSTITUTION_CHAMBER.get() ? 120 : 134;
        builder.addSlot(inputSlot = BasicInventorySlot.at(this::acceptsItem, listener, inputX, 36));
        builder.addSlot(outputSlot = BasicInventorySlot.at(listener, outputX, 36));
        return builder.build();
    }

    @Override
    protected boolean onUpdateServer() {
        boolean update = super.onUpdateServer();
        if (canProcess()) {
            if (++progress >= getTicksRequired()) {
                process();
                progress = 0;
            }
            setActive(true);
            markForSave();
        } else {
            progress = 0;
            setActive(false);
        }
        return update;
    }

    /** Sneak-use/controller GUI calls this to choose the only accepted phase transition. */
    public void togglePhaseMode() {
        if (type == RadioisotopeProcessorType.PHASE_CONTROL) {
            excite = !excite;
            syncPhaseModel();
            markForSave();
            sendUpdatePacket();
        }
    }

    @Override
    public void nextMode() {
        togglePhaseMode();
    }

    @Override
    public void previousMode() {
        togglePhaseMode();
    }

    public boolean isExciteMode() {
        return excite;
    }

    public boolean isPhaseController() {
        return type == RadioisotopeProcessorType.PHASE_CONTROL;
    }

    public boolean isReconstitutionChamber() {
        return type == RadioisotopeProcessorType.RECONSTITUTE;
    }

    public MachineEnergyContainer<TileEntityRadioisotopeProcessor> getEnergyContainer() {
        return energyContainer;
    }

    public int getProgress() {
        return progress;
    }

    /** Client GUI uses this synchronized fraction for Mekanism's native progress widget. */
    public double getScaledProgress() {
        return progress / (double) getTicksRequired();
    }

    public IChemicalTank getChemicalInput() {
        return chemicalInput;
    }

    public IChemicalTank getChemicalOutput() {
        return chemicalOutput;
    }

    @Override
    public float getRadiationScale() {
        return RadioisotopeCapsules.isActive(inputSlot.getStack()) || RadioisotopeCapsules.isActive(outputSlot.getStack()) ? 1 : super.getRadiationScale();
    }

    @Override
    public void addContainerTrackers(MekanismContainer container) {
        super.addContainerTrackers(container);
        container.track(SyncableBoolean.create(() -> excite, value -> excite = value));
        container.track(SyncableInt.create(() -> progress, value -> progress = Math.clamp(value, 0, getTicksRequired() - 1)));
    }

    @Override
    public void writeSustainedData(HolderLookup.Provider provider, CompoundTag data) {
        super.writeSustainedData(provider, data);
        data.putBoolean("Excite", excite);
        data.putInt("Progress", progress);
    }

    @Override
    public void readSustainedData(HolderLookup.Provider provider, @NotNull CompoundTag data) {
        super.readSustainedData(provider, data);
        excite = data.getBoolean("Excite");
        progress = Math.clamp(data.getInt("Progress"), 0, getTicksRequired() - 1);
        syncPhaseModel();
    }

    private boolean acceptsChemical(ChemicalStack stack) {
        return type == RadioisotopeProcessorType.ENCAPSULATE && (stack.isRadioactive() || stack.getChemical() == MekanismChemicals.OXYGEN.get());
    }

    private boolean acceptsItem(ItemStack stack) {
        return switch (type) {
            case ENCAPSULATE -> stack.is(NuclearEntangloporterItems.EMPTY_CONTAINMENT_CAPSULE) || stack.is(NuclearEntangloporterItems.UNCHARGED_NEUTRALIZER_CAPSULE);
            case PHASE_CONTROL -> excite ? RadioisotopeCapsules.isPhaseLocked(stack) : RadioisotopeCapsules.isActive(stack);
            case RECONSTITUTE -> RadioisotopeCapsules.isActive(stack);
        };
    }

    private boolean canProcess() {
        long operationEnergy = getOperationEnergy();
        if (energyContainer.extract(operationEnergy, Action.SIMULATE, AutomationType.INTERNAL) != operationEnergy) {
            return false;
        }
        // Every operation consumes exactly one accepted item; never create a capsule from chemical alone.
        if (!acceptsItem(inputSlot.getStack())) {
            return false;
        }
        ItemStack result = result();
        if (result.isEmpty() || !canInsert(outputSlot, result)) {
            return false;
        }
        if (type == RadioisotopeProcessorType.RECONSTITUTE) {
            ChemicalStack contents = RadioisotopeCapsules.contents(inputSlot.getStack());
            return contents != null && chemicalOutput.insert(contents, Action.SIMULATE, AutomationType.INTERNAL).isEmpty();
        }
        return type != RadioisotopeProcessorType.ENCAPSULATE || chemicalInput.getStored() >= chemicalRequired();
    }

    private ItemStack result() {
        return switch (type) {
            case ENCAPSULATE -> {
                ChemicalStack contents = chemicalInput.getStack();
                if (isNeutralizerRecipe()) {
                    yield NuclearEntangloporterItems.ISOTOPE_NEUTRALIZER.asStack();
                }
                yield isRadioisotopeEncapsulationRecipe() && contents.getAmount() >= RadioisotopeCapsules.CAPACITY ?
                      RadioisotopeCapsules.create(contents.copyWithAmount(RadioisotopeCapsules.CAPACITY), false) : ItemStack.EMPTY;
            }
            case PHASE_CONTROL -> {
                ChemicalStack contents = RadioisotopeCapsules.contents(inputSlot.getStack());
                yield contents == null ? ItemStack.EMPTY : RadioisotopeCapsules.create(contents, !excite);
            }
            case RECONSTITUTE -> NuclearEntangloporterItems.EMPTY_CONTAINMENT_CAPSULE.asStack();
        };
    }

    private static boolean canInsert(BasicInventorySlot slot, ItemStack stack) {
        return slot.insertItem(stack, Action.SIMULATE, AutomationType.INTERNAL).isEmpty();
    }

    private void process() {
        ItemStack result = result();
        ChemicalStack reconstituted = type == RadioisotopeProcessorType.RECONSTITUTE ? RadioisotopeCapsules.contents(inputSlot.getStack()) : null;
        // All result checks happened first; execute operations only after that atomic precondition.
        energyContainer.extract(getOperationEnergy(), Action.EXECUTE, AutomationType.INTERNAL);
        inputSlot.shrinkStack(1, Action.EXECUTE);
        if (type == RadioisotopeProcessorType.ENCAPSULATE) {
            chemicalInput.shrinkStack(chemicalRequired(), Action.EXECUTE);
        } else if (reconstituted != null) {
            chemicalOutput.insert(reconstituted, Action.EXECUTE, AutomationType.INTERNAL);
        }
        outputSlot.insertItem(result, Action.EXECUTE, AutomationType.INTERNAL);
    }

    @Override
    public void recalculateUpgrades(Upgrade upgrade) {
        super.recalculateUpgrades(upgrade);
        if (upgrade == Upgrade.SPEED) {
            progress = Math.min(progress, getTicksRequired() - 1);
        }
    }

    @Override
    public Set<ChunkPos> getChunkSet() {
        return Collections.singleton(new ChunkPos(getBlockPos()));
    }

    @Override
    public TileComponentChunkLoader<TileEntityRadioisotopeProcessor> getChunkLoader() {
        return chunkLoaderComponent;
    }

    private int getTicksRequired() {
        int ticksRequired = type == RadioisotopeProcessorType.PHASE_CONTROL && !excite ? STABILIZATION_TICKS_REQUIRED : STANDARD_TICKS_REQUIRED;
        return MekanismUtils.getTicks(this, ticksRequired);
    }

    /** Preserve each processor's established full-operation cost while applying Mekanism's Energy/Speed scaling. */
    private long getOperationEnergy() {
        if (isNeutralizerRecipe()) {
            return NEUTRALIZER_ENERGY;
        }
        if (type == RadioisotopeProcessorType.PHASE_CONTROL && excite) {
            return EXCITATION_ENERGY;
        }
        return Math.max(1, Math.round(operationEnergy * (energyContainer.getEnergyPerTick() / (double) energyContainer.getBaseEnergyPerTick())));
    }

    private long chemicalRequired() {
        return isNeutralizerRecipe() ? NEUTRALIZER_OXYGEN : RadioisotopeCapsules.CAPACITY;
    }

    /** The Encapsulator's medical recipe deliberately accepts oxygen, while containment remains radioactive-only. */
    private boolean isNeutralizerRecipe() {
        return inputSlot.getStack().is(NuclearEntangloporterItems.UNCHARGED_NEUTRALIZER_CAPSULE) && chemicalInput.getStack().getChemical() == MekanismChemicals.OXYGEN.get();
    }

    private boolean isRadioisotopeEncapsulationRecipe() {
        return inputSlot.getStack().is(NuclearEntangloporterItems.EMPTY_CONTAINMENT_CAPSULE) && chemicalInput.getStack().isRadioactive();
    }

    private void syncPhaseModel() {
        if (type == RadioisotopeProcessorType.PHASE_CONTROL && level != null && !level.isClientSide) {
            BlockIsotopicPhaseController.setExciteState(level, worldPosition, excite);
        }
    }
}
