# AGENTS.md

This repo contains both editable mod code and read-only relevant sources. These rules ensure we don’t break upstream code while building the Nuclear Entangloporter mod.
This file defines the contribution rules and boundaries for development in this repository.  
Follow these guidelines to ensure stable builds, prevent accidental corruption of game/framework code, and keep commits clean and reviewable.


## Golden Rules

* You must confirm the project builds successfully **before committing any changes**.
* Edit only where allowed (see Directory Policy). Treat game/framework sources as read-only.
* Leave clear and concise comments detailing the process alongside any code written.
* Raise Gradle Build Number for each Commit.
* ONLY Create the addon mod we are developing. Do NOT edit any code in restricted directories.


## Directory Policy
```
/ (root)
├─ src/                               # The ONLY mod code to edit!
├─ Mekanism/                          # Mekanism Source Code (READ-ONLY)
├─ ModDevGradle/                      # Gradle Plugin for Developing Minecraft Mods on NeoForge (READ-ONLY)
├─ GradleInfo/                        # Contains Instructions to utilize Mekanism API via Gradle (READ-ONLY)
└─ NeoForge/                          # NeoForge Source Code (READ-ONLY)
```

### Allowed edits
- `NuclearEntangloporter/**`
- Root docs: `AGENTS.md`, `README.md`, `CONTRIBUTING.md`, `.gitignore`, `.editorconfig`, `SUGGESTIONS.md`, `TRACELOG.md`

### Forbidden edits
- Anything under the other top-level folders listed as read-only
- READ-ONLY Directories

## What To Do If Build Fails
* Suggest a text-only fix (e.g., add HintPath using a relative path) but don’t break the read-only policy.

## What To Do When Build Succeeds
* Review the code changes in the commit.
* Suggest refactors, optimizations, or improvements for readability and performance.
* Propose feature expansions or enhancements directly related to the commit.
* Output the suggestions as an entry in `SUGGESTIONS.md`

## What To Do Before Committing 
* Every commit must include an entry in `TRACELOG.md` detailing:
  - The prompt/task given
  - The steps taken
  - Rationale for chosen implementation

## Commit Checklist
- [ ] Project builds with no errors
- [ ] Changes limited to allowed directories
- [ ] Clear comments added for all new/modified code
- [ ] `TRACELOG.md` updated with new entry including prompt + detailing steps
- [ ] `SUGGESTIONS.md` updated with new entry
- [ ] `README.md` updated with relevant information
- [ ] Raise Gradle Build Versioning