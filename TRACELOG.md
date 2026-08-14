# Trace Log

- **Prompt:** Create a Mekanism add-on that introduces a Nuclear Entangloporter restricted to transporting Mekanism-defined radioactive gases and liquids.
  - **Steps:**
    1. Bootstrapped a standalone NeoForge/Mekanism development setup (Gradle files, metadata, assets) for the add-on mod.
    2. Implemented registries, block/item wrappers, and language resources mirroring the Quantum Entangloporter.
    3. Added radioactive-only tank holders and a custom tile entity that filters capabilities to gases/fluids flagged as Mekanism radioactives.
    4. Documented features, updated repository logs, and prepared build/test instructions.
  - **Rationale:** Ensures the new block feels identical to Mekanism's entangloporter while enforcing strict handling for nuclear materials, keeping waste logistics isolated from standard networks.
- **Prompt:** Resolve Gradle build failure by exposing Mekanism implementation classes on the compile classpath.
  - **Steps:**
    1. Added Mekanism's development `:all` classifier as a compile-only dependency so the Quantum Entangloporter clone can reference common internals.
    2. Bumped the mod build number and refreshed documentation to highlight the new dependency expectation for contributors.
    3. Logged future work suggestions to guard against upstream packaging changes disrupting compilation.
  - **Rationale:** Pulling in the full Mekanism implementation jar restores access to shared tile logic while keeping runtime dependencies unchanged, unblocking successful builds.
- **Prompt:** Fix missing registries and radioactivity helpers so the Nuclear Entangloporter compiles against Mekanism.
  - **Steps:**
    1. Added Mekanism-aligned block and block-entity registries that hook into the injected mod event bus.
    2. Implemented shared radioactivity filters that translate Mekanism fluids back into chemical stacks before enforcing the radiation check.
    3. Updated documentation, suggestions, and build metadata to capture the new registration flow and testing follow-up.
  - **Rationale:** Restoring the registry infrastructure and unified filters resolves the compiler errors while keeping the add-on behavior identical to Mekanism's Quantum Entangloporter outside of the nuclear restrictions.
- **Prompt:** Ensure the built Nuclear Entangloporter jar is recognised as a valid NeoForge mod file.
  - **Steps:**
    1. Added a `neoforge.mods.toml` descriptor and `pack.mcmeta` so the jar declares its metadata to the NeoForge loader.
    2. Bundled a basic English localisation file covering the block name and description to prevent missing translation keys in-game.
    3. Bumped the build number and refreshed repository docs and suggestions to reflect the metadata packaging change.
  - **Rationale:** Including the required metadata lets NeoForge discover the add-on during mod scans and avoids the invalid mod warning that blocked in-game testing.
- **Prompt:** Prevent the BuildCreativeModeTabContentsEvent from crashing the mod during loader initialisation.
  - **Steps:**
    1. Moved the creative tab listener registration to the injected mod event bus so the IModBusEvent fires on a compatible dispatcher.
    2. Documented the event-bus nuance in the README and surfaced a new suggestion to guard against future mis-registrations.
    3. Incremented the build counter to track the hotfix release.
  - **Rationale:** Keeping IModBusEvent listeners on the mod bus removes the loader exception NeoForge threw when the handler targeted the global event bus, allowing the jar to construct successfully again.
- **Prompt:** Restore full Quantum Entangloporter functionality and visuals while limiting transport to radioactive chemicals and fluids.
  - **Steps:**
    1. Rebased the nuclear tile entity on Mekanism's `TileEntityQuantumEntangloporter`, reusing its inventory, energy, heat, and frequency systems while injecting radioactive tank filters.
    2. Updated block, item, and block-entity registrations to expose the complete capability set and reuse Mekanism's side configuration metadata.
    3. Added resource overrides that point to Mekanism's quantum entangloporter models plus documentation and suggestions covering the shared asset dependency.
  - **Rationale:** Extending Mekanism's implementation preserves every gameplay feature players expect—GUI, upgrades, computer integration, and textures—while still enforcing the add-on's nuclear-only transport constraint.
- **Prompt:** Fix the invalid Mekanism block entity validation crash and restore chunk loading/computer parity for the Nuclear Entangloporter.
  - **Steps:**
    1. Ported Mekanism's Quantum Entangloporter tile code wholesale so the nuclear block validates against its own block provider while retaining the original frequency, chunk loader, and computer control hooks.
    2. Introduced Nuclear Entangloporter energy, heat, and inventory holders alongside the existing radioactive tank wrappers to mirror Mekanism's capability exposure without accepting non-radioactive contents.
    3. Updated project docs, suggestions, and the build counter after verifying a clean NeoForge build with the restored tile entity.
- **Rationale:** Copying the full Mekanism implementation eliminates the block entity type mismatch that caused the crash and keeps the add-on behaviour indistinguishable from the quantum entangloporter outside the intended radioactive transfer limits.
- **Prompt:** Restore GUI access and ticking behaviour after the nuclear entangloporter failed to open Mekanism's screen in-game.
  - **Steps:**
    1. Registered a dedicated nuclear entangloporter menu type that mirrors Mekanism's container offsets while targeting the add-on tile entity.
    2. Hooked Mekanism's `GuiQuantumEntangloporter` to the new menu during client screen registration and pointed the block type at the updated container supplier.
    3. Matched Mekanism's tile registration extras—server ticker and configuration card capability—and refreshed the documentation, suggestions, and build metadata.
  - **Rationale:** Reusing Mekanism's GUI through an add-on specific menu type keeps right-click interactions functional while preserving parity with the quantum entangloporter's ticking and configuration tooling.
- **Prompt:** Fix the NeoForge automatic subscriber crash complaining about missing `@SubscribeEvent` handlers on the client screen registrar.
  - **Steps:**
    1. Annotated the menu screen registration callback with `@SubscribeEvent` and bound the subscriber to the mod event bus so NeoForge recognises it during injection.
    2. Updated the README with the bus registration detail to guide future contributors.
    3. Logged a follow-up suggestion about static checks for subscriber annotations and bumped the build metadata.
  - **Rationale:** Ensuring the client registrar exposes a proper event handler allows NeoForge to complete its automatic subscriber scan and lets the nuclear entangloporter GUI load again without crashing the loader.
- **Prompt:** Allow the Nuclear Entangloporter to accept Mekanism's radioactive fluids like polonium without loosening the nuclear-only transport rule.
  - **Steps:**
    1. Normalised Mekanism fluid identifiers in the radioactivity filter so flowing variants resolve back to their still chemical names before consulting the chemical registry.
    2. Documented the flowing-fluid handling in the README and noted a logging follow-up in the suggestions list.
    3. Incremented the build counter after verifying a clean NeoForge build with the updated radioactive filter logic.
  - **Rationale:** Converting flowing fluid names to the matching chemical keys ensures Mekanism's radioactive liquids and gases clear the transport filter while non-radioactive content stays blocked.
- **Prompt:** Ensure the Nuclear Entangloporter buffers radioactive chemicals and fluids exactly like Mekanism's quantum counterpart while still filtering out safe materials.
  - **Steps:**
    1. Cached radioactive tank wrappers so Mekanism capability lookups interact with stable delegates that mirror the frequency buffers' identity.
    2. Allowed empty-stack mutations through the wrappers while continuing to block non-radioactive inserts, preserving Mekanism's buffer lifecycle and clearing paths.
    3. Updated documentation, suggestions, and build metadata before running a clean NeoForge build to validate the behaviour.
  - **Rationale:** Keeping wrapper identity stable and respecting empty stack operations restores Mekanism's internal tank behaviour, letting the nuclear entangloporter hold polonium and waste just like the stock quantum entangloporter without compromising the radioactive-only guardrails.
- **Prompt:** Make the radioactive filters accept Mekanism's waste streams by honouring the API's radioactivity metadata for both chemicals and fluids.
  - **Steps:**
    1. Swapped the chemical tank checks to use Mekanism's `isRadioactive` helper so datapack-driven radiation overrides are respected instead of recalculating raw values.
    2. Looked up fluids through the chemical registry using the normalised still identifier and rejected the API's empty chemical sentinel to keep the filter mod-friendly.
    3. Documented the registry-based lookup, noted a cross-mod regression test in suggestions, incremented the build counter, and ran the NeoForge build.
- **Rationale:** Deferring to Mekanism's own radioactivity metadata ensures polonium, nuclear waste, and any future or third-party radioactive chemicals can enter the nuclear entangloporter while still blocking safe fluids.
- **Prompt:** Prevent automation from bypassing the radioactive filters so the nuclear entangloporter actually accepts polonium and waste while still refusing safe contents.
  - **Steps:**
    1. Exposed the radioactivity helper methods and added tile-level `insertChemical`/`insertFluid` overrides that re-run the radioactive checks before delegating to Mekanism's handlers.
    2. Documented the additional guard in the README so future contributors know the server-side filter intentionally mirrors the wrapper logic.
    3. Incremented the Gradle build number and reran the NeoForge build to confirm the stricter inserts compile and pass the project checks.
  - **Rationale:** Repeating the radioactive validation at the tile entry points ensures pressurised tubes, pipes, or other automation that skip tank lookups still respect the nuclear-only contract, restoring polonium and nuclear waste transfers without opening the buffers to safe fluids.
- **Prompt:** Design a dedicated nuclear frequency so the Nuclear Entangloporter can expose radioactive buffers without Mekanism unregistering it.
  - **Steps:**
    1. Implemented a `NuclearInventoryFrequency` clone of Mekanism's inventory frequency that filters stored buffers to radioactive contents and tracks `TileEntityNuclearEntangloporter` participants.
    2. Reflected into Mekanism's private frequency registry to register a `NuclearInventory` frequency type and updated the tile, GUI, and item plumbing to target the new type.
    3. Documented the new frequency flow, bumped the build number, and refreshed project suggestions after validating a clean NeoForge build.
  - **Rationale:** A dedicated frequency type keeps the nuclear entangloporter visible to Mekanism's frequency managers, restoring tank exposure and automation while preserving the radioactive-only contract.
- **Prompt:** Finish the nuclear frequency clone by persisting selections onto items and configuration cards.
  - **Steps:**
    1. Registered a `nuclear_inventory_frequency` data component that serialises `NuclearInventoryFrequency` identities so drops and tools carry the selected channel.
    2. Hooked the nuclear entangloporter tile into the new component for implicit save/load, added remap coverage, and rewrote the block item's tooltip to read the stored identity.
    3. Updated documentation, logged a follow-up suggestion about upstream data-component hooks, bumped the build counter, and ran the NeoForge build to confirm a clean compile.
  - **Rationale:** Persisting the selected frequency keeps nuclear entangloporters aligned with Mekanism's behaviour when moved or copied, ensuring the new frequency namespace is practical in survival worlds.
- **Prompt:** Finish implementing the dedicated nuclear frequency so linked entangloporters resume auto-ejecting radioactive payloads.
  - **Steps:**
    1. Mirrored Mekanism's frequency transfer loop inside `NuclearInventoryFrequency` so it tracks active nuclear entangloporters and redistributes energy, fluids, and chemicals each tick.
    2. Ensured the tile entity calls the new eject handler every server tick and exposed cached capability lookups so the frequency can access radioactive tank wrappers safely.
    3. Documented the restored transfer behaviour, suggested an automation test for multi-block frequencies, bumped the Gradle build number, and validated the changes with a fresh NeoForge build.
  - **Rationale:** Bringing back the shared transfer pipeline lets nuclear entangloporters behave like Mekanism's quantum network while still restricting transported contents to radioactive resources.
- **Prompt:** Ensure the radioactive filters honour Mekanism's chemical datamaps so Nuclear Waste is accepted even when stack radiation metadata lags behind.
  - **Steps:**
    1. Added a fallback that consults Mekanism's chemical radioactivity datamap when `ChemicalStack#isRadioactive` reports zero, covering datapack-defined waste streams.
    2. Mirrored the same datamap fallback for fluid lookups so liquids mapping back to radioactive chemicals remain accepted.
    3. Documented the fallback behaviour in the README, recorded a debug-toggle suggestion, bumped the build counter, and verified a clean NeoForge build.
  - **Rationale:** Checking Mekanism's authoritative radioactivity datamap keeps the nuclear entangloporter tolerant of datapack or early-load edge cases where runtime stacks haven't yet promoted their radiation values while still blocking safe fluids.
- **Prompt:** Guarantee Mekanism's stock radioactive chemicals always pass the nuclear filters even when registry data is still initialising.
  - **Steps:**
    1. Cached the Mekanism ids for nuclear waste, spent nuclear waste, polonium, and plutonium and used them as a final fallback in the shared radioactivity helper.
    2. Updated README documentation and the suggestion log to capture the hard-coded safety net and a follow-up for auto-deriving the list from Mekanism's registries.
    3. Raised the Gradle build counter and confirmed the project still compiles via the NeoForge build task.
- **Rationale:** Keeping a small hard-coded allowlist ensures the core Mekanism waste loop keeps flowing through the nuclear entangloporter even if datapack metadata or registry lookups momentarily fail during world bootstrap.
- **Prompt:** Prevent early-world inserts from bouncing when Mekanism's chemical registry has not populated yet.
  - **Steps:**
    1. Added a registry-miss fallback in the radioactive fluid filter that reuses the hard-coded Mekanism radioactive id set when the chemical lookup returns empty.
    2. Documented the bootstrap safeguard in the README and logged a suggestion to add a runtime health check for future regressions.
    3. Incremented the Gradle build counter and verified a clean NeoForge build to confirm the tightened guard compiles.
- **Rationale:** Falling back to the known radioactive ids when the chemical registry is unavailable keeps nuclear waste and polonium flowing through the entangloporter during early load, finally matching Mekanism's quantum behaviour for radioactive payloads.
- **Prompt:** The Nuclear Entangloporter is still not accepting/storing any radioactive materials. Analyze Mekanism's quantum entangloporter and API usage to determine why the nuclear variant refuses Nuclear Waste or Polonium.
  - **Steps:**
    1. Updated the chemical radioactivity helper to mirror the fluid bootstrap fallback by reading holder metadata and trusting Mekanism's built-in radioactive ids when the chemical registry lookup returns null during early ticks.
    2. Reviewed the tile and tank holder insert paths to confirm they continue delegating to the shared helper so the new fallback propagates across every entry point.
    3. Documented the holder-based fallback in the README, refreshed the suggestion log with a regression-test idea, bumped the Gradle build counter, and revalidated the project with the NeoForge build.
- **Rationale:** Matching the fluid bootstrap fallback on chemical stacks prevents legitimate radioactive gases from being rejected before the registry binds, aligning the nuclear entangloporter's behaviour with Mekanism's quantum counterpart while keeping the radioactive-only contract intact.
- **Prompt:** Continue developing out desired functionality, currently our nuclear entangloporter is not accepting radioactive materials.
  - **Steps:**
    1. Flagged nuclear inventory frequencies as valid the moment a nuclear entangloporter registers so the new channel immediately exposes its radioactive tank wrappers.
    2. Documented the eager validity toggle in the README and filed a follow-up suggestion for an automated smoke test that asserts tanks appear as soon as a frequency is selected.
    3. Incremented the Gradle build counter and revalidated the project with the NeoForge build to confirm the updated frequency handshake compiles cleanly.
  - **Rationale:** Ensuring a selected nuclear frequency marks itself valid as soon as a tile joins prevents the guarded tanks from vanishing during early world ticks, restoring the expected plug-and-play behaviour players get with Mekanism's stock quantum entangloporter.
- **Prompt:** Something is still preventing the Nuclear Entangloporter from accepting radioactive materials; Nuclear Waste and Polonium never reach the internal buffers.
  - **Steps:**
    1. Added an identity-based short-circuit in the shared radioactivity helper that compares stacks against Mekanism's `MekanismChemicals` entries so stock wastes always count as radioactive even if registry lookups or datamaps fail.
    2. Updated the README and suggestion backlog to highlight the new MekanismChemicals fallback and future-proof the allowlist against upstream renames.
    3. Bumped the Gradle build counter and ran the NeoForge build to confirm the stronger validation still compiles cleanly.
  - **Rationale:** Comparing directly against Mekanism's deferred chemical instances guarantees nuclear waste, spent waste, polonium, and plutonium insert successfully even during early-load hiccups, finally letting the nuclear entangloporter mirror the quantum entangloporter's acceptance of radioactive payloads.
