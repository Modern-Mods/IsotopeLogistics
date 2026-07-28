# Nuclear Entangloporter

Nuclear Entangloporter is a Minecraft 1.21.1 NeoForge addon for Mekanism. It gives radioactive chemicals and radioisotopes a complete transport, containment, processing, and optional digital-storage path without mixing them into normal Quantum Entangloporter frequencies.

The mod is built around one rule: **active radioisotopes are dangerous material, not ordinary inventory items.** Encapsulate them for transport, phase-lock them for safe storage, or keep them on dedicated radioactive disks.

## Requirements and installation

- Minecraft `1.21.1`
- NeoForge `21.1.0` or newer
- Mekanism `10.7.19` or newer

Place the mod jar in the `mods` folder on both client and server. Mekanism is required. Applied Energistics 2 and Refined Storage are optional; each only enables its own radioactive storage family when installed.

## What this mod adds

| Feature | Purpose |
| --- | --- |
| Nuclear Entangloporter | Dedicated wireless item, energy, fluid, chemical, and heat transport frequency for nuclear work. |
| Radioactive storage blocks | Mekanism-style bins, fluid tanks, and chemical tanks with safe radioactive automation behavior. |
| Containment capsules | Portable 1,000 units chemical packages, in either active or phase-locked state. |
| Radioisotope machines | Encapsulate chemicals, change capsule phase, recover the original chemical, and prepare emergency neutralizers. |
| Isotope Neutralizer | Consumable emergency pill, made through a compound-and-oxygen activation chain, that instantly clears the player's accumulated radiation dose. |
| Radioactive disks/cells | Optional AE2 and Refined Storage storage media that accept active capsules only. |

Core blocks, containment parts, neutralizer ingredients, the Isotope Neutralizer, and the Stabilization Matrix appear in the **Nuclear Entangloporter** creative tab. Active and phase-locked capsules are made by processing. Optional disks/cells appear only when their owner mod is present.

## Design goals and progression

This addon does not replace Mekanism's radiation loop. It gives that loop deliberate logistics choices:

1. Move radioactive chemicals directly through a dedicated Nuclear Entangloporter frequency when the destination is another nuclear machine.
2. Package exactly 1,000 units in an Active Radioisotope Capsule when a discrete item package is useful.
3. Phase-lock that capsule before placing it in ordinary inventories or transporting it without radiation exposure.
4. Use dedicated radioactive bins or optional radioactive digital media only when the capsule must stay active.

The intended progression is **HDPE and lead containment → fission chemicals → encapsulation → phase control → high-capacity nuclear logistics**. The mod deliberately keeps ordinary Quantum Entangloporters and ordinary item storage separate from active radioactive handling.

## First nuclear setup

For a reliable first setup, make an Empty Containment Capsule, place a Radiological Encapsulator near a protected fission installation, and supply it with FE plus a radioactive chemical. Keep an empty capsule in the item input, configure a chemical-input side for the chemical pipe, and send the finished active capsule either to a Radioactive Bin or to an Isotopic Phase Controller.

Use a Phase Controller immediately when the capsule is headed to a normal chest, backpack, or non-radioactive digital network. Use an Active Capsule only where its active state is required; phase-locking is the normal storage and transport form.

## Nuclear Entangloporter

The Nuclear Entangloporter behaves like Mekanism's Quantum Entangloporter, but uses its own nuclear frequency type. Normal Quantum Entangloporters cannot see or join nuclear frequencies, preventing accidental cross-connection between ordinary logistics and radioactive processing.

It supports Mekanism's familiar frequency selection, security, upgrades, side configuration, auto-ejection, chunk loading, and computer hooks. It can move items, FE, fluids, chemicals, and heat across matching nuclear frequencies. Unlike the standard block, its chemical buffer accepts Mekanism radioactive chemicals such as nuclear waste and polonium.

### Basic use

1. Place two or more Nuclear Entangloporters.
2. Open each GUI and select the same nuclear frequency.
3. Use **Side Config** to set the required item, energy, fluid, chemical, or heat sides.
4. Enable auto-eject or connect compatible pipes/cables on the configured sides.

### Choosing a transport method

| Need | Recommended path |
| --- | --- |
| Move bulk nuclear waste, polonium, or another radioactive chemical between machines | Dedicated Nuclear Entangloporter frequency or a Radioactive Chemical Tank. |
| Move a fixed 1,000-unit package into a processing line | Active Radioisotope Capsule. |
| Put a capsule into ordinary storage or carry it safely | Phase-Locked Radioisotope Capsule. |
| Keep active capsules in an AE2/RS network | Matching radioactive cell or disk only. |

Creating a nuclear frequency does not make a standard Quantum Entangloporter radioactive-safe, and it does not bridge the two frequency types.

## Radioisotope capsule workflow

Each capsule contains exactly **1,000 units** of one radioactive Mekanism chemical. Capsules stack to 64 only when their contents and phase state match.

```text
Radioactive chemical + Empty Containment Capsule
                    ↓ Radiological Encapsulator
           Active Radioisotope Capsule
                    ↓ Isotopic Phase Controller (Stabilize)
       Phase-Locked Radioisotope Capsule
                    ↓ Isotopic Phase Controller (Excite)
           Active Radioisotope Capsule
                    ↓ Chemical Reconstitution Chamber
Radioactive chemical + Empty Containment Capsule
```

### Capsule states

| State | Use | Safety |
| --- | --- | --- |
| Active Radioisotope Capsule | Processing, dedicated radioactive bins, AE2/RS radioactive media. | Radioactive. |
| Phase-Locked Radioisotope Capsule | Safe transport and ordinary item storage. | Inert. |

Hover a capsule to see its chemical, amount, phase, and radiation status. Invalid or incomplete capsule data is never accepted as processing material.

An Empty Containment Capsule is returned by reconstitution, so the chemical-to-capsule-to-chemical loop does not consume a fresh containment shell each cycle. Phase locking changes only the capsule state; it does not change the stored chemical or its 1,000-unit amount.

## Machines

All three machines have a 500,000 FE internal buffer, Mekanism side configuration for their applicable item, chemical, and energy ports, a progress display, and recipe help from the progress control. With JEI installed, that control opens the machine's own category and every radioactive chemical it accepts. Configure the sides before connecting pipes: a pipe can only insert or extract through a side enabled for that transmission type.

| Machine | Inputs | Outputs | Cost and time |
| --- | --- | --- | --- |
| Radiological Encapsulator | 1,000 units radioactive chemical + 1 Empty Containment Capsule | 1 Active Radioisotope Capsule | 40,000 FE, 100 ticks (5 seconds) |
| Isotopic Phase Controller | 1 Active or Phase-Locked Capsule | Opposite capsule state | Stabilize: 250,000 FE, 200 ticks; Excite: 50,000 FE, 100 ticks |
| Chemical Reconstitution Chamber | 1 Active Radioisotope Capsule | 1,000 units original radioactive chemical + 1 Empty Containment Capsule | 40,000 FE, 100 ticks (5 seconds) |

### Empty Pill Capsule

Use a Metallurgic Infuser: **1 HDPE Pellet + 10 units Carbon → 4 Empty Pill Capsules**.

### Isotope Neutralizer

Craft **1 Enriched Carbon + 1 Fluorite Dust → 1 Neutralizing Compound**, then craft **1 Empty Pill Capsule + 1 Neutralizing Compound → 1 Uncharged Neutralizer Capsule**. Activate that capsule in a Radiological Encapsulator with **100 units Oxygen + 100,000 FE → 1 Isotope Neutralizer**.

### Radiological Encapsulator

Use this machine to package a radioactive chemical. Insert empty capsules into its item input and send a radioactive chemical to its configured chemical-input side. Every output consumes **one** empty capsule; keeping a stack in the input does not make capsules infinite. The custom model occupies two blocks vertically, has a translucent glass chamber, and uses lit port indicators.

It also oxygen-activates Uncharged Neutralizer Capsules into **Isotope Neutralizers**. Each pill uses 100 units of oxygen and 100,000 FE. Eat one to instantly clear your accumulated Mekanism radiation dose; it does not remove radiation sources or stop fresh exposure.

### Isotopic Phase Controller

Use the GUI toggle to choose a mode:

- **Stabilize**: Active Capsule → Phase-Locked Capsule
- **Excite**: Phase-Locked Capsule → Active Capsule

The machine only accepts the capsule state appropriate to the selected mode. A Stabilization Matrix is required to craft the controller, but is **not** consumed during processing.

### Chemical Reconstitution Chamber

Use this machine to unpack an **active** capsule. It returns the contained radioactive chemical to the chemical output and returns an empty containment capsule to the item output. Phase-Locked Capsules must be excited first.

### If a machine will not run

Check these in order:

1. Machine has FE and its energy-input side is configured for the connected cable.
2. Item and chemical pipes use sides enabled for their transmission type in **Side Config**.
3. Correct input is present: the Encapsulator needs a radioactive chemical and an Empty Containment Capsule, or oxygen and an Uncharged Neutralizer Capsule; the Controller needs the capsule matching its mode; the Reconstitution Chamber needs an active capsule.
4. Output item slot and chemical output tank have room.
5. The Encapsulator has at least 1,000 units of a radioactive chemical. Its chemical input tank holds up to 10,000 units.

### Automation example

```text
Fission output → Nuclear Entangloporter → Radioactive Chemical Tank
                                      ↘ Radiological Encapsulator → Active Capsule
                                                                      ↘ Phase Controller → Phase-Locked Capsule → normal storage
```

For continuous chemical logistics, keep the material as a chemical. Use capsules only where item transport, controlled batches, or safe phase-locked storage is useful. This prevents a bulk chemical line from being needlessly constrained by 1,000-unit capsule batches.

## Radioactive storage blocks

Radioactive Bins, Fluid Tanks, and Chemical Tanks mirror their Mekanism counterparts at Basic, Advanced, Elite, Ultimate, and Creative tiers. They retain tier capacity, item display/lock behavior where applicable, upgrades, and automation controls. Bins use Mekanism's direct insert/extract interactions and do not open a GUI; tanks retain their normal Mekanism controls. Every non-Creative tier has a survival recipe regardless of optional integrations; Creative tiers remain creative-only.

### Radioactive Bins

Radioactive Bins are the dedicated physical storage for Active Radioisotope Capsules. They display their stored item/count on the front and can be used like a standard Mekanism bin.

- Manual insertion of an Active Capsule into a standard Mekanism Bin is blocked.
- Breaking a Radioactive Bin while it still contains Active Capsules releases radiation at its location.
- Empty the bin or phase-lock the capsules before moving or breaking it.

### Radioactive Chemical Tanks and Fluid Tanks

These blocks provide the normal Mekanism tank GUI and automation surface for nuclear installations. The Radioactive Chemical Tank explicitly accepts Mekanism radioactive chemicals. Configure chemical/item/energy sides through the normal Mekanism controls before attaching pipes.

Choose the same capacity tier you would choose for the corresponding Mekanism block; the radioactive variant retains that tier's storage behavior and is crafted by hardening the matching Mekanism tier with lead and HDPE. Fluid tanks are for radioactive fluids supplied by compatible content, while Chemical Tanks are the normal destination for Mekanism radioactive chemicals.

## Radiation and safe handling

Active Radioisotope Capsules are radioactive. Treat them like any other Mekanism radioactive material:

- Wear appropriate Mekanism radiation protection, including a complete Hazmat Suit, before carrying active capsules.
- Do not leave Active Capsules in a player inventory, armor, or offhand without protection. Their carried radiation is applied once per second.
- Do not throw Active Capsules on the ground. Dropped active capsules radiate their location once per second.
- Phase-Locked Capsules are inert and are the safe form for normal item handling.
- Breaking a Radioactive Bin holding Active Capsules releases its stored dose as radiation.

Radiation is provided through Mekanism's radiation system, so its effects and protection obey the Mekanism server configuration. This release does not add continuous radiation scanning for arbitrary chests, pipes, or digital networks; use phase-locked capsules for those general-purpose inventories.

### Emergency response

An Isotope Neutralizer immediately clears the player's accumulated Mekanism radiation dose. It is a cure, not protection: it does not remove a contaminated area, consume nearby radioactive material, or prevent the next exposure. Leave the source, wear suitable protection, and then use the pill when the exposure has stopped.

## Optional AE2 and Refined Storage media

Radioactive storage media exists only when the corresponding storage mod is installed. It accepts **valid Active Radioisotope Capsules only**. Empty capsules, Phase-Locked Capsules, ordinary items, and malformed capsules are rejected. Stock AE2 and Refined Storage item media reject active capsules, so an active capsule can enter only radioactive media. Store phase-locked capsules on ordinary item media instead.

| Integration | Available radioactive media |
| --- | --- |
| Applied Energistics 2 | 1k, 4k, 16k, 64k, 256k, 1M, 4M, 16M, 64M, 256M Radioactive ME Storage Cells |
| Refined Storage | 1k, 4k, 16k, 64k, 256k, 1M, 4M, 16M, 64M, 256M, 1024M, 1048M Radioactive Storage Disks |

If neither AE2 nor Refined Storage is installed, no radioactive disk/cell content is registered or shown.

Their recipes load only with their parent mod. Native AE2 cells through 256k and native Refined Storage disks through 64k are converted into their radioactive equivalent; larger addon tiers combine four previous radioactive media with the parent mod's highest native storage component. This preserves both mods' normal storage progression without referencing items that are absent from the installed version.

### Using radioactive digital media

1. Craft the radioactive cell or disk for the installed integration.
2. Insert it into the normal ME Drive or Disk Drive for that parent mod.
3. Move only valid Active Radioisotope Capsules into the network.
4. Extract a capsule before phase-locking it for ordinary storage, or phase-lock it before moving it to a normal cell/disk.

The dedicated media is an explicit storage boundary, not a blanket change to AE2 or RS. Normal cells, disks, chests, pipes, and third-party inventories do not become radioactive-capable merely because this addon is installed.

## Crafting recipes

`A` = Atomic Alloy, `C` = Crying Obsidian, `E` = Elite Control Circuit, `G` = Glass Pane, `H` = HDPE Pellet, `L` = Lead Ingot, `M` = Stabilization Matrix, `O` = Refined Obsidian Ingot, `P` = Polonium Pellet, `Q` = Quantum Entangloporter, `R` = Reinforced Alloy, `S` = Steel Casing, `T` = Elite Pressurized Tube, `U` = Ultimate Control Circuit, `W` = Radioactive Waste Barrel. Storage diagrams define their own letters.

### Empty Containment Capsule ×4

```text
L H L
H G H
L H L
```

### Radiological Encapsulator

```text
R E R
W S W
R E R
```

### Stabilization Matrix

```text
R O R
L C L
R O R
```

### Isotopic Phase Controller

```text
A U A
L S L
A M A
```

### Chemical Reconstitution Chamber

```text
R E R
T S T
R E R
```

### Nuclear Entangloporter

```text
A P A
U Q U
A M A
```

### Radioactive Mekanism Storage

For each Basic through Ultimate Bin, Fluid Tank, and Chemical Tank, put the matching Mekanism block in the center:

```text
L H L
H P H
L H L
```

### Radioactive AE2 Cells

For each native 1k through 256k AE2 cell, put the matching Item Storage Cell in the center; `Q` is Quartz Glass. Higher tiers use four of the preceding radioactive cell, four 256k Cell Components, and a Stabilization Matrix:

```text
L Q L      R C R
Q P Q      C M C
L Q L      R C R
```

### Radioactive Refined Storage Disks

For each native 1k through 64k RS disk, put the matching Storage Disk in the center; `Q` is Quartz Enriched Iron. Higher tiers use four preceding radioactive disks, four 64k Storage Parts, and an Advanced Processor:

```text
L Q L      R P R
Q P Q      P A P
L Q L      R P R
```

The 1048M Radioactive Disk combines one 1024M Radioactive Disk, six 4M Radioactive Disks, and two Advanced Processors, preserving its exact 1,048M capacity. The AE2 and RS recipes load only when their parent mod is installed.

## Limits and compatibility

- Mekanism is required on both client and server. AE2 and Refined Storage are optional, but an integration must be present wherever its radioactive media is used.
- The Nuclear Entangloporter supports the standard Entangloporter resource types—items, energy, fluids, chemicals, heat, security, side configuration, auto-eject, chunk loading, and computer hooks—on its separate nuclear frequency.
- Radiation is applied for active capsules carried by players, dropped in the world, or released from a broken Radioactive Bin. The addon does not try to scan every arbitrary third-party inventory for radioactive contents.
- Creative-tier radioactive storage is intentionally not craftable. It is available for building and testing only.
- JEI is optional but recommended. It shows every installed recipe and the processor recipe categories; it is the authoritative in-game reference when another mod or datapack changes recipes.

## Support

Report bugs and compatibility issues at [GitHub Issues](https://github.com/nuclearmekanism/NuclearEntangloporter/issues). Include Minecraft, NeoForge, Mekanism, AE2/Refined Storage versions when relevant, plus a latest log for crashes or failed model/pipe behavior.

## Building from source

Development requires Java 21. From the repository root, run:

```text
./NeoForge/gradlew build
```

The built jar is written to `build/libs/`.
