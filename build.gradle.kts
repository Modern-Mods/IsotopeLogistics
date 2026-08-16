import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

plugins {
    id("net.neoforged.moddev") version "2.0.107"
    id("java")
}

private object ModelTransforms {
    /** Minecraft block models only support element rotations through 45 degrees. Bake supplied right-angle port rotations instead. */
    fun bakeRightAngleRotation(element: Map<String, Any?>): LinkedHashMap<String, Any?> {
    val baked = LinkedHashMap(element)
    @Suppress("UNCHECKED_CAST")
    val rotation = baked["rotation"] as? Map<String, Any?> ?: return baked
    if (rotation.containsKey("axis")) {
        return baked
    }
    val x = (rotation["x"] as? Number)?.toDouble() ?: 0.0
    val y = (rotation["y"] as? Number)?.toDouble() ?: 0.0
    val z = (rotation["z"] as? Number)?.toDouble() ?: 0.0
    require(x == 0.0 && z == 0.0 && kotlin.math.abs(y) == 90.0) {
        "Unsupported legacy element rotation in ${element["name"]}: $rotation"
    }
    @Suppress("UNCHECKED_CAST")
    val origin = rotation["origin"] as List<Number>
    @Suppress("UNCHECKED_CAST")
    val from = baked["from"] as List<Number>
    @Suppress("UNCHECKED_CAST")
    val to = baked["to"] as List<Number>
    val rotated = listOf(from[0], to[0]).flatMap { pointX ->
        listOf(from[2], to[2]).map { pointZ ->
            if (y > 0) {
                (origin[0].toDouble() + pointZ.toDouble() - origin[2].toDouble()) to (origin[2].toDouble() - pointX.toDouble() + origin[0].toDouble())
            } else {
                (origin[0].toDouble() - pointZ.toDouble() + origin[2].toDouble()) to (origin[2].toDouble() + pointX.toDouble() - origin[0].toDouble())
            }
        }
    }
    baked["from"] = listOf(rotated.minOf { it.first }, from[1], rotated.minOf { it.second })
    baked["to"] = listOf(rotated.maxOf { it.first }, to[1], rotated.maxOf { it.second })
    baked.remove("rotation")
    @Suppress("UNCHECKED_CAST")
    val faces = baked["faces"] as? Map<String, Map<String, Any?>>
    if (faces != null) {
        fun rotateFace(face: String) = when (face) {
            "up", "down" -> face
            else -> if (y > 0) mapOf("north" to "west", "east" to "north", "south" to "east", "west" to "south").getValue(face)
                    else mapOf("north" to "east", "east" to "south", "south" to "west", "west" to "north").getValue(face)
        }
        baked["faces"] = linkedMapOf<String, Any?>().apply {
            faces.forEach { (direction, face) ->
                put(rotateFace(direction), LinkedHashMap(face).apply {
                    (this["cullface"] as? String)?.let { this["cullface"] = rotateFace(it) }
                })
            }
        }
    }
        return baked
    }
}

abstract class ComposeRadiologicalEncapsulatorModel : DefaultTask() {
    @get:InputFile
    abstract val sourceModel: RegularFileProperty

    @get:OutputFile
    abstract val outputModel: RegularFileProperty

    @TaskAction
    fun compose() {
        @Suppress("UNCHECKED_CAST")
        val raw = JsonSlurper().parse(sourceModel.get().asFile) as Map<String, Any?>
        @Suppress("UNCHECKED_CAST")
        val elements = raw["elements"] as List<Map<String, Any?>>
        fun textures(includePorts: Boolean) = linkedMapOf<String, Any?>("0" to "nuclearentangloporter:block/models/radiological_encapsulator").apply {
            if (includePorts) {
                put("1", "nuclearentangloporter:block/models/ports")
                put("2", "nuclearentangloporter:block/models/ports_led")
            }
        }
        val cutout = elements.filterNot { (it["name"] as String).startsWith("translucent_") }.map { element ->
            ModelTransforms.bakeRightAngleRotation(element).apply {
                if ((this["name"] as String).contains("led", ignoreCase = true)) {
                    put("neoforge_data", linkedMapOf("block_light" to 15, "sky_light" to 15))
                }
            }
        }
        val translucent = elements.filter { (it["name"] as String).startsWith("translucent_") }
        val model = linkedMapOf<String, Any?>(
            "loader" to "neoforge:composite",
            "gui_light" to "side",
            "textures" to linkedMapOf("particle" to "nuclearentangloporter:block/models/radiological_encapsulator"),
            "display" to raw["display"],
            "children" to linkedMapOf(
                "cutout" to linkedMapOf("render_type" to "minecraft:cutout", "textures" to textures(true), "elements" to cutout),
                "translucent" to linkedMapOf("render_type" to "minecraft:translucent", "textures" to textures(false), "elements" to translucent)
            )
        )
        outputModel.get().asFile.apply {
            parentFile.mkdirs()
            writeText(JsonOutput.prettyPrint(JsonOutput.toJson(model)))
        }
    }
}

/** Bakes Blockbench's legacy right-angle rotations and remaps supplied Mekanism textures to this addon's copied assets. */
abstract class NormalizeCustomMachineModel : DefaultTask() {
    @get:InputFile
    abstract val sourceModel: RegularFileProperty

    @get:OutputFile
    abstract val outputModel: RegularFileProperty

    @TaskAction
    fun normalize() {
        @Suppress("UNCHECKED_CAST")
        val raw = JsonSlurper().parse(sourceModel.get().asFile) as Map<String, Any?>
        @Suppress("UNCHECKED_CAST")
        val textures = raw["textures"] as Map<String, Any?>
        @Suppress("UNCHECKED_CAST")
        val elements = raw["elements"] as List<Map<String, Any?>>
        val remappedTextures = linkedMapOf<String, Any?>()
        textures.forEach { (key, texture) ->
            remappedTextures[key] = (texture as? String)?.replace("mekanism:", "nuclearentangloporter:") ?: texture
        }
        val cutout = elements.filterNot { (it["name"] as? String)?.startsWith("translucent_") == true }.map { element ->
            ModelTransforms.bakeRightAngleRotation(element).apply {
                if ((this["name"] as? String)?.contains("led", ignoreCase = true) == true) {
                    put("neoforge_data", linkedMapOf("block_light" to 15, "sky_light" to 15))
                }
            }
        }
        val translucent = elements.filter { (it["name"] as? String)?.startsWith("translucent_") == true }
        val children = linkedMapOf<String, Any?>(
            "cutout" to linkedMapOf("render_type" to "minecraft:cutout", "textures" to remappedTextures, "elements" to cutout)
        )
        if (translucent.isNotEmpty()) {
            children["translucent"] = linkedMapOf("render_type" to "minecraft:translucent", "textures" to remappedTextures, "elements" to translucent)
        }
        val model = linkedMapOf<String, Any?>(
            "loader" to "neoforge:composite",
            "parent" to "block/block",
            "textures" to linkedMapOf("particle" to remappedTextures["particle"]),
            "children" to children
        )
        raw["display"]?.let { model["display"] = it }
        outputModel.get().asFile.apply {
            parentFile.mkdirs()
            writeText(JsonOutput.prettyPrint(JsonOutput.toJson(model)))
        }
    }
}

group = "com.nuclearmekanism"
val modVersion = property("mod_version") as String
version = modVersion
println("Building Isotope Logistics v$version")

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

base.archivesName.set("IsotopeLogistics")

repositories {
    mavenCentral()
    maven("https://modmaven.dev/")
}

val integrationModsDir = providers.gradleProperty("integration_mods_dir")
    .orElse("C:/Users/Zach/AppData/Roaming/PrismLauncher/instances/Testing/minecraft/mods")
    .get()

dependencies {
    compileOnly("mekanism:Mekanism:${property("mekanism_version")}:api")
    // Pull in Mekanism's development "all" jar so the common implementations we mirror are on the compile classpath.
    compileOnly("mekanism:Mekanism:${property("mekanism_version")}:all")

    runtimeOnly("mekanism:Mekanism:${property("mekanism_version")}")
    runtimeOnly("mekanism:Mekanism:${property("mekanism_version")}:additions")
    runtimeOnly("mekanism:Mekanism:${property("mekanism_version")}:generators")
    runtimeOnly("mekanism:Mekanism:${property("mekanism_version")}:tools")

    // Optional integrations compile against exact local jars; neither jar is bundled.
    compileOnly(files("$integrationModsDir/appliedenergistics2-19.2.17.jar"))
    compileOnly(files("$integrationModsDir/refinedstorage-neoforge-2.0.9.jar"))
    compileOnly(files("$integrationModsDir/jei-1.21.1-neoforge-19.39.0.370.jar"))
}

neoForge {
    version = property("neo_version") as String

    runs {
        configureEach {
            logLevel = org.slf4j.event.Level.INFO
        }
        create("client") {
            client()
        }
        create("server") {
            server()
        }
        create("data") {
            data()
        }
    }

    mods {
        create("nuclearentangloporter") {
            sourceSet(sourceSets.main.get())
        }
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    // User assets stay outside source tree; package exact PNGs and rewrite only their model namespace in build output.
    from("assets/radiologicalencapsulator") {
        include("*.png")
        into("assets/nuclearentangloporter/textures/block/models")
    }
}

val composeRadiologicalEncapsulatorModel = tasks.register<ComposeRadiologicalEncapsulatorModel>("composeRadiologicalEncapsulatorModel") {
    sourceModel.set(layout.projectDirectory.file("assets/radiologicalencapsulator/radioactive_encapsulator.json"))
    outputModel.set(layout.buildDirectory.file("resources/main/assets/nuclearentangloporter/models/block/radiological_encapsulator.json"))
    dependsOn(tasks.processResources)
}

val normalizePhaseControllerModel = tasks.register<NormalizeCustomMachineModel>("normalizePhaseControllerModel") {
    sourceModel.set(layout.projectDirectory.file("assets/phase_controller.json"))
    outputModel.set(layout.buildDirectory.file("resources/main/assets/nuclearentangloporter/models/block/isotopic_phase_controller.json"))
    dependsOn(tasks.processResources)
}

val normalizePhaseControllerExciteModel = tasks.register<NormalizeCustomMachineModel>("normalizePhaseControllerExciteModel") {
    sourceModel.set(layout.projectDirectory.file("assets/phase_controller_excite.json"))
    outputModel.set(layout.buildDirectory.file("resources/main/assets/nuclearentangloporter/models/block/isotopic_phase_controller_excite.json"))
    dependsOn(tasks.processResources)
}

val normalizePhaseControllerStabilizeModel = tasks.register<NormalizeCustomMachineModel>("normalizePhaseControllerStabilizeModel") {
    sourceModel.set(layout.projectDirectory.file("assets/phase_controller_Stabilize.json"))
    outputModel.set(layout.buildDirectory.file("resources/main/assets/nuclearentangloporter/models/block/isotopic_phase_controller_stabilize.json"))
    dependsOn(tasks.processResources)
}

val normalizeChemicalReconstitutionChamberModel = tasks.register<NormalizeCustomMachineModel>("normalizeChemicalReconstitutionChamberModel") {
    sourceModel.set(layout.projectDirectory.file("assets/chemical_reconstitution_chamber.json"))
    outputModel.set(layout.buildDirectory.file("resources/main/assets/nuclearentangloporter/models/block/chemical_reconstitution_chamber.json"))
    dependsOn(tasks.processResources)
}

tasks.named<Jar>("jar") {
    dependsOn(composeRadiologicalEncapsulatorModel, normalizePhaseControllerModel, normalizePhaseControllerExciteModel,
        normalizePhaseControllerStabilizeModel, normalizeChemicalReconstitutionChamberModel)
}

