# TASK: Reversible Radioactive Chemical Encapsulation and Stabilization

Implement a reversible radioactive-material storage system for this Mekanism companion mod.

The system must allow compatible radioactive chemicals to be:

1. Converted from a chemical into a radioactive item capsule.
2. Stabilized into a non-radioactive, inventory-safe item.
3. Reactivated into a radioactive item.
4. Converted back into the original radioactive chemical.

The complete processing loop is:

```text
Radioactive Chemical
→ Active Radioisotope Capsule
→ Phase-Locked Radioisotope Capsule
→ Active Radioisotope Capsule
→ Radioactive Chemical
```

Example:

```text
Nuclear Waste
→ Nuclear Waste Capsule
→ Phase-Locked Nuclear Waste Capsule
→ Nuclear Waste Capsule
→ Nuclear Waste
```

## Core Design

The mod should treat stabilization as **isotopic phase locking**.

A high-energy quantum confinement field suppresses radioactive decay without changing the underlying chemical identity. The material remains chemically recoverable and may later be restored through controlled isotopic excitation.

Do not describe the process as destroying and recreating radioactivity.

Preferred terminology:

* **Encapsulation**: Chemical to radioactive item
* **Phase locking** or **stabilization**: Radioactive item to inert item
* **Excitation**: Inert item to radioactive item
* **Reconstitution**: Radioactive item to chemical

## Mandatory Processing Stages

### 1. Radiological Encapsulator

Add a machine named:

```text
Radiological Encapsulator
```

Its purpose is to convert a supported radioactive chemical into an item form.

Input:

* A supported radioactive chemical
* Energy
* Any required reusable or consumable containment component

Output:

* An active radioactive capsule containing a fixed quantity of the input chemical

Conceptual recipe:

```text
Radioactive Chemical
+ Energy
+ Empty Containment Capsule
→ Active Radioisotope Capsule
```

The chemical quantity must be preserved exactly unless a configurable recipe explicitly states otherwise.

The initial implementation should use a fixed amount per capsule so identical capsules can stack reliably.

Use an amount appropriate for Mekanism chemical quantities after inspecting the current Mekanism API and existing conventions. Do not assume that fluid-style millibucket units are correct for the installed Mekanism version.

### 2. Isotopic Phase Controller

Add a reversible machine named:

```text
Isotopic Phase Controller
```

This machine handles both stabilization and reactivation.

It must support two operating modes:

```text
Stabilize
Excite
```

#### Stabilize mode

Input:

* Active Radioisotope Capsule
* Energy
* Optional stabilization catalyst or matrix, if enabled by the final recipe design

Output:

* Phase-Locked Radioisotope Capsule

Conceptual recipe:

```text
Active Radioisotope Capsule
+ Energy
+ Stabilization Matrix
→ Phase-Locked Radioisotope Capsule
```

#### Excite mode

Input:

* Phase-Locked Radioisotope Capsule
* Energy

Output:

* Active Radioisotope Capsule

Conceptual recipe:

```text
Phase-Locked Radioisotope Capsule
+ Energy
→ Active Radioisotope Capsule
```

The GUI must clearly show the selected mode.

The machine must not process an incompatible capsule for the selected mode.

A redstone-controlled mode option may be added only if it fits the project’s existing machine conventions and does not unnecessarily complicate the implementation.

### 3. Chemical Reconstitution Chamber

Add a machine named:

```text
Chemical Reconstitution Chamber
```

Its purpose is to convert an active radioactive capsule back into its original chemical.

Input:

* Active Radioisotope Capsule
* Energy, if appropriate for the machine design

Output:

* The original radioactive chemical
* An empty containment capsule, if capsules are reusable

Conceptual recipe:

```text
Active Radioisotope Capsule
→ Radioactive Chemical
+ Empty Containment Capsule
```

A phase-locked capsule must not be accepted directly.

When a phase-locked capsule is inserted, the machine must refuse processing and expose an understandable status message such as:

```text
Chemical is phase-locked and cannot be reconstituted.
```

Do not silently reactivate phase-locked material inside this machine.

## Item Model

Prefer a generic capsule implementation instead of registering a separate item for every radioactive chemical.

The preferred item types are:

```text
Active Radioisotope Capsule
Phase-Locked Radioisotope Capsule
```

Each capsule must store enough data to reconstruct the original contents.

Required stored information:

* Chemical registry identity
* Contained quantity
* Stabilization state
* Any additional compatibility or validation data required by the installed Mekanism API

Use the data-component system appropriate for Minecraft 1.21.1 and NeoForge.

Do not use deprecated NBT patterns if the project and Minecraft version use data components.

Do not duplicate the entire capsule implementation between active and phase-locked states when a shared data model can be used safely.

## Capsule Tooltips

### Active capsule

The tooltip should communicate:

```text
Contained Chemical: Nuclear Waste
Amount: [formatted chemical amount]
State: Active
Radiation: [formatted radiation value or Active]
```

### Phase-locked capsule

The tooltip should communicate:

```text
Contained Chemical: Nuclear Waste
Amount: [formatted chemical amount]
State: Phase-Locked
Radiation: Suppressed
```

Use localization entries for all tooltip text.

Do not hard-code English strings into gameplay classes.

## Stacking Rules

Capsules may stack only when all relevant stored data matches.

At minimum, stacking compatibility must require matching:

* Chemical type
* Chemical amount
* Stabilization state
* Any purity, isotope, decay, or recipe metadata introduced by the implementation

Fixed-capacity capsules are preferred because variable chemical amounts may prevent useful stacking and complicate automation.

Do not allow two capsules with different chemicals or amounts to merge into the same stack.

## Radiation Behavior

### Active Radioisotope Capsule

An active capsule remains radioactive.

It must not be treated as safe merely because the chemical has been converted into an item.

Where supported by the installed Mekanism API, active capsules should contribute radiation when:

* Carried by a player
* Present as a dropped item
* Stored in an ordinary inventory
* Released by a broken machine or storage block

Implement radiation behavior using actual Mekanism radiation APIs or established project integration patterns.

Do not invent API methods.

If Mekanism does not expose a safe and maintainable way to apply radiation from arbitrary inventories, implement the supported subset and document the limitation.

### Phase-Locked Radioisotope Capsule

A phase-locked capsule must:

* Emit no radiation
* Be safe to carry
* Be safe to drop
* Be safe to store in ordinary inventories
* Be compatible with normal item transportation
* Remain unusable in recipes that require active radioactive material
* Preserve the original chemical and quantity exactly

Phase locking must remain stable without continuous power.

Losing power must not automatically reactivate stored capsules.

## Storage Distinction

The implementation must preserve the following conceptual storage tiers:

| Form                 | Radiation state | Intended storage         |
| -------------------- | --------------- | ------------------------ |
| Radioactive chemical | Active          | Chemical storage systems |
| Active capsule       | Active          | Shielded item storage    |
| Phase-locked capsule | Suppressed      | Ordinary item storage    |

Phase-locked capsules may be used with:

* Vanilla chests
* Item pipes
* Mekanism logistical transporters
* Digital item storage systems
* Other standard item handlers

Do not add arbitrary compatibility restrictions unless required for correctness.

## Optional Shielded Storage

Add dedicated shielded item storage only after the core processing loop works.

Possible block names include:

* Lead-Lined Crate
* Radiological Storage Vault
* Shielded Bin
* Radioisotope Cache

A shielded storage block would allow active capsules to be stored without irradiating the environment.

This is secondary scope.

Do not delay the core encapsulation and stabilization system to implement shielded storage.

## Visual Direction

### Active capsules

Active capsule visuals should communicate danger.

Suggested presentation:

* Bright chemical-colored interior
* Radiation symbol
* Emissive or animated appearance where practical
* Stronger saturation than the stabilized version

### Phase-locked capsules

Phase-locked capsule visuals should communicate suppressed energy.

Suggested presentation:

* Dimmed or desaturated chemical color
* Blue-white containment accents
* Reduced or absent glow
* A visual containment ring, lattice, or field effect

### Machines

The Isotopic Phase Controller may visually include:

* Electromagnetic coils
* Energy arcs
* A blue-white confinement field
* A chamber surrounding the inserted capsule
* Different active visuals for Stabilize and Excite modes

Follow the project’s existing rendering and animation architecture.

Do not introduce a new rendering framework solely for these machines.

Placeholder textures are acceptable during the initial functional implementation if assets are not part of the current task.

## Balance Requirements

The system must not trivialize Mekanism radiation management.

Default balance should follow these principles:

* Encapsulation costs energy.
* Stabilization costs substantial energy.
* Stabilization takes meaningful processing time.
* Reactivation also costs energy.
* Reconstitution is not completely free unless existing machine conventions justify it.
* Breaking a machine containing active radioactive material may release radiation where the API supports it.
* Phase-locked material cannot be used directly in active radioactive recipes.
* Phase locking is permanent until intentionally reversed.
* The system must not require continuous power for safe storage.

Do not add unavoidable random loss by default.

If conversion loss is implemented, it must be configurable and disabled by default unless the project specification already requires resource loss.

## Stabilization Matrix

A consumable or durable item named:

```text
Stabilization Matrix
```

may be required for phase locking.

The matrix should represent the material or field structure used to maintain the phase-locked nuclear state.

Before implementing it as a consumable, evaluate the existing progression and recipe balance.

Acceptable options include:

* Consumed per capsule
* Damaged gradually
* Used as a reusable machine component
* Required only for machine construction
* Configurable recipe ingredient

Choose the option that best fits the current mod’s established machine and recipe design.

Do not add a mandatory grind without considering progression.

## Compatibility

The system should support more than a hard-coded list of vanilla Mekanism chemicals.

Where feasible, it must support:

* Mekanism radioactive chemicals
* Radioactive chemicals added by Mekanism addons
* Radioactive chemicals added by compatible third-party mods
* Future radioactive chemicals registered through supported APIs or tags

Determine eligibility from actual chemical attributes, radiation data, tags, or supported Mekanism mechanisms.

Do not identify chemicals solely by registry-name string matching.

If fully dynamic detection is not possible, implement a clear registration or configuration API for addons.

## Recipes and Automation

All processing stages must work with automation.

Machines must support the project’s established conventions for:

* Item insertion
* Item extraction
* Chemical insertion
* Chemical extraction
* Side configuration
* Energy input
* Recipe lookup
* Progress tracking
* Redstone control, where already standard

Recipes should be data-driven where practical.

Avoid hard-coding every supported chemical conversion directly into machine logic.

The encapsulation and reconstitution systems should preserve chemical identity without requiring a manually registered recipe for every possible radioactive chemical, if the Mekanism API safely permits dynamic handling.

## Safety and Validation

The implementation must safely handle:

* Missing chemical registrations
* Removed addon chemicals
* Invalid data components
* Empty capsules
* Zero or negative stored quantities
* Quantities above capsule capacity
* Corrupted capsule data
* Unsupported non-radioactive chemicals
* Active capsules inserted into the wrong machine mode
* Phase-locked capsules inserted into the Reconstitution Chamber
* Machine destruction while radioactive chemicals are buffered internally

Invalid items must not crash the game or duplicate chemicals.

When invalid stored data is detected, fail safely and log useful diagnostic information without spamming the log every tick.

## No Duplication

The full loop must conserve material.

For a valid capsule:

```text
Chemical input amount
=
Capsule stored amount
=
Chemical output amount
```

The player must not be able to duplicate chemicals by:

* Changing machine modes
* Removing items during processing
* Breaking machines
* Moving partially processed items
* Manipulating automation timing
* Combining capsule stacks
* Corrupting capsule state
* Reusing an output container incorrectly

Machine processing must use transactional checks where appropriate.

Do not consume an input until the complete output can be accepted.

## Configuration

Add configuration values only where they provide meaningful pack-level control.

Useful configuration candidates:

* Chemical amount per capsule
* Encapsulation energy cost
* Stabilization energy cost
* Excitation energy cost
* Reconstitution energy cost
* Processing duration for each stage
* Whether a Stabilization Matrix is required
* Whether active capsules irradiate holders
* Whether active capsules irradiate dropped-item areas
* Whether active capsules irradiate ordinary inventories
* Radiation multiplier for active capsules
* Optional conversion efficiency

Defaults must preserve the intended radioactive-storage challenge.

Validate configuration values and prevent invalid negative amounts or costs.

## Localization

Add localization entries for:

* All machine names
* All item names
* Machine modes
* Tooltips
* GUI labels
* Status and error messages
* Recipe-category names
* Configuration descriptions, if applicable

Preferred names:

```text
Radiological Encapsulator
Isotopic Phase Controller
Chemical Reconstitution Chamber
Active Radioisotope Capsule
Phase-Locked Radioisotope Capsule
Stabilization Matrix
Stabilize
Excite
```

Example machine description:

```text
Uses a high-energy quantum confinement field to force unstable nuclei into a phase-locked configuration, suppressing radioactive decay without altering the contained chemical.
```

Example excitation description:

```text
Disrupts the containment phase and restores a capsule to its naturally radioactive state.
```

## Architecture Requirements

Before implementation:

1. Inspect the project structure.
2. Inspect the installed Minecraft, NeoForge, and Mekanism versions.
3. Find existing machine, recipe, capability, chemical, radiation, data-component, menu, and GUI patterns.
4. Reuse existing abstractions instead of creating parallel systems.
5. Confirm every Mekanism API call against the actual dependency source or generated documentation.

Do not imagine class names, methods, capabilities, chemical units, or radiation APIs.

Do not create a monolithic implementation.

Separate responsibilities into appropriate modules, such as:

* Registration
* Capsule data component
* Capsule item behavior
* Chemical validation
* Radiation integration
* Machine block entities
* Machine menus
* Machine screens
* Recipe handling
* Configuration
* Localization
* Tests or game tests

Keep machine-specific logic out of the primary mod entrypoint.

## Implementation Order

Implement in this order:

### Phase 1: Investigation

* Locate existing project conventions.
* Confirm relevant Mekanism APIs.
* Document any API limitations.
* Determine how radioactive chemicals are identified.
* Determine the canonical chemical quantity units.

### Phase 2: Capsule Data Model

* Add the capsule data component.
* Add active and phase-locked capsule states.
* Add validation.
* Add tooltips.
* Confirm correct stack behavior.

### Phase 3: Encapsulation and Reconstitution

* Add the Radiological Encapsulator.
* Add the Chemical Reconstitution Chamber.
* Implement lossless chemical-to-item-to-chemical conversion.
* Verify automation.
* Verify no duplication.

### Phase 4: Phase Control

* Add the Isotopic Phase Controller.
* Add Stabilize and Excite modes.
* Add energy and processing-time requirements.
* Add phase-locked restrictions.
* Add mode-specific GUI feedback.

### Phase 5: Radiation Integration

* Add radiation behavior to active capsules using supported Mekanism APIs.
* Confirm phase-locked capsules emit no radiation.
* Handle dropped items, players, inventories, and machine destruction where supported.
* Clearly document unsupported cases.

### Phase 6: Polish

* Add recipes.
* Add localization.
* Add configuration.
* Add recipe-viewer integration if the project already supports one.
* Add placeholder or final assets.
* Update documentation.

## Acceptance Criteria

The task is complete only when all applicable criteria pass.

### Functional

* A supported radioactive chemical can be converted into an active capsule.
* The capsule preserves the exact chemical identity.
* The capsule preserves the exact configured amount.
* An active capsule remains radioactive.
* An active capsule can be stabilized.
* A phase-locked capsule emits no radiation.
* A phase-locked capsule can be stored in ordinary item storage.
* A phase-locked capsule cannot be reconstituted directly.
* A phase-locked capsule can be excited back into an active capsule.
* An active capsule can be converted back into its original chemical.
* The complete loop does not duplicate or unintentionally delete material.
* Machines work through manual interaction and automation.
* Machine state persists correctly across save and reload.
* Capsule data persists correctly across save and reload.

### Compatibility

* Non-radioactive chemicals are rejected.
* Unsupported chemicals fail safely.
* Addon chemicals work dynamically where the API allows.
* Removed or missing chemical registrations do not crash the game.
* Invalid capsule data does not crash the game.
* Existing Mekanism processing remains unaffected.

### User Experience

* Every machine clearly communicates its current operation.
* The Phase Controller clearly displays Stabilize or Excite mode.
* Tooltips clearly distinguish active and phase-locked capsules.
* Errors explain why processing cannot begin.
* Text is localized.
* Active and phase-locked items are visually distinguishable.

### Code Quality

* No speculative Mekanism API usage remains.
* No large unrelated logic dump is added to the mod entrypoint.
* Shared behavior is not unnecessarily duplicated.
* Machine logic is separated into maintainable classes.
* Validation is centralized where practical.
* Logs are useful and not emitted every tick.
* New configuration values are validated.
* Code follows existing project style.

## Required Testing

Test at least the following scenarios:

1. Encapsulate a valid radioactive chemical.
2. Attempt to encapsulate a non-radioactive chemical.
3. Stabilize an active capsule.
4. Attempt to stabilize an already phase-locked capsule.
5. Excite a phase-locked capsule.
6. Attempt to excite an already active capsule.
7. Reconstitute an active capsule.
8. Attempt to reconstitute a phase-locked capsule.
9. Break each machine during active processing.
10. Save and reload with capsules in inventories and machines.
11. Automate the full processing loop.
12. Fill the output slot or output chemical tank during processing.
13. Remove an input during processing.
14. Insert capsule items with mismatched stored data.
15. Verify two different chemicals cannot stack together.
16. Verify different stored amounts cannot stack together.
17. Verify active and phase-locked capsules cannot stack together.
18. Verify the full conversion loop preserves material.
19. Verify active capsules produce radiation where supported.
20. Verify phase-locked capsules never produce radiation.
21. Test a radioactive chemical from a compatible addon, if one is available in the development environment.
22. Test behavior when a previously stored chemical is no longer registered.

Use automated tests or game tests where the project already supports them. Otherwise, document reproducible manual test procedures.

## Documentation

Update the project documentation with:

* The complete processing loop
* Machine purposes
* Capsule states
* Radiation behavior
* Automation behavior
* Configuration options
* Known API limitations
* Compatibility guidance for addon developers

Include this conceptual explanation:

```text
The Isotopic Phase Controller uses a high-energy quantum confinement field to force unstable nuclei into a phase-locked configuration. This suppresses radioactive decay without changing the contained chemical. Controlled isotopic excitation reverses the process and restores the capsule to its active radioactive state.
```

## Deliverables

Provide:

* Working source code
* Registrations
* Data components
* Machines and block entities
* Menus and screens
* Recipes
* Configuration
* Localization
* Required assets or documented placeholders
* Tests or a manual verification checklist
* Updated project documentation
* A concise implementation summary
* A list of confirmed Mekanism APIs used
* A list of known limitations

## Final Reporting

When finished, report:

1. Files added.
2. Files modified.
3. Architecture used.
4. How radioactive chemicals are detected.
5. How capsule data is stored.
6. How radiation is applied.
7. How material duplication is prevented.
8. Tests completed.
9. Any unsupported radiation scenarios.
10. Any remaining work or asset placeholders.

Do not claim that a feature works unless it was compiled and tested.

Do not silently omit a requested feature because an API is difficult to use. Document the limitation and implement the safest supported behavior.
